package `fun`.gladkikh.fastpallet7.ui.createpallet.box

import `fun`.gladkikh.fastpallet7.Constants
import `fun`.gladkikh.fastpallet7.common.getWeightByBarcode
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.BoxCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.CreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.PalletCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.ProductCreatePallet
import `fun`.gladkikh.fastpallet7.model.usecase.check.CheckDocumentUseCase
import `fun`.gladkikh.fastpallet7.model.usecase.creatpallet.BoxActionUseCase
import `fun`.gladkikh.fastpallet7.repository.CreatePalletRepositoryUpdate
import `fun`.gladkikh.fastpallet7.ui.base.BaseViewModel
import `fun`.gladkikh.fastpallet7.ui.common.Command
import `fun`.gladkikh.fastpallet7.ui.common.Command.*
import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*

class BoxCreatePalletViewModel(
    private val repository: CreatePalletRepositoryUpdate,
    private val checkDocumentUseCase: CheckDocumentUseCase
) : BaseViewModel() {

    private val loadHandler = BoxCreatePalletLoadDataHandler(
        compositeDisposable,
        repository
    )

    private val bufferMutableLiveData = MutableLiveData<Int>()

    private val boxUseCase = BoxActionUseCase(
        compositeDisposable = compositeDisposable,
        repository = repository,
        //Берем данные из нового бокса
        beforeAddFun = { box, buffer ->
            loadHandler.setOnlyBox(box)
            bufferMutableLiveData.postValue(buffer)
        },
        //После сохранения всего списка
        afterSaveFun = { box, buffer ->
            bufferMutableLiveData.postValue(buffer)
            setGuid(box.guid)
        },
        //После удаления закрываем
        afterDellFun = {
            commandChannel.postValue(Close)
        })

    fun getDocLiveData(): LiveData<CreatePallet> = loadHandler.getDocLiveData()
    fun getProductLiveData(): LiveData<ProductCreatePallet> = loadHandler.getProductLiveData()
    fun getPalletLiveData(): LiveData<PalletCreatePallet> = loadHandler.getPalletLiveData()
    fun getBoxLiveData(): LiveData<BoxCreatePallet> = loadHandler.getBoxLiveData()
    fun getBufferLiveData(): LiveData<Int> = bufferMutableLiveData

    private val CONFIRM_DELETE_DIALOG = 1
    private val EDIT_PLACE_DIALOG = 2
    private val EDIT_COUNT_DIALOG = 3
    private val ADD_COUNT_DIALOG = 4

    fun setGuid(guidBox: String?) {
        guidBox?.let { loadHandler.setGuid(it) }
    }

    fun getGuid(): String? {
        return loadHandler.getCurrentPallet()?.guid
    }



    fun keyDown(keyCode: Int) {
        when (keyCode) {
            Constants.KEY_1 -> {
                startEditCount()
            }
            Constants.KEY_2 -> {
                startEditPlace()
            }
            Constants.KEY_3 -> {
                startAdd()
            }
            Constants.KEY_9 -> {
                startDelete()
            }
        }
    }

    fun startEditPlace() {
        commandChannel.postValue(
            EditNumberDialog(
                "Мест",
                EDIT_PLACE_DIALOG,
                false,
                (loadHandler.getCurrentBox()?.countBox ?: 0).toString()
            )
        )
    }

    fun startEditCount() {
        commandChannel.postValue(
            Command.EditNumberDialog(
                "Количество",
                EDIT_COUNT_DIALOG,
                true,
                (loadHandler.getCurrentBox()?.count ?: 0).toString()
            )
        )
    }

    fun startAdd() {
        commandChannel.postValue(
            Command.EditNumberDialog(
                "Количество",
                ADD_COUNT_DIALOG,
                true,
                "0"
            )
        )
    }

    fun startDelete() {
        commandChannel.postValue(ConfirmDialog("Удаляем!", CONFIRM_DELETE_DIALOG))
    }

    override fun callBackConfirmDialog(confirmDialog: ConfirmDialog) {
        super.callBackConfirmDialog(confirmDialog)
        when (confirmDialog.requestCode) {
            CONFIRM_DELETE_DIALOG -> {
                deleteBox()
            }
        }
    }

    override fun callBackEditNumberDialog(editNumberDialog: EditNumberDialog) {
        super.callBackEditNumberDialog(editNumberDialog)
        when (editNumberDialog.requestCode) {
            EDIT_PLACE_DIALOG -> {
                val place = editNumberDialog.data?.toIntOrNull()
                if (place == null) {
                    messageErrorChannel.postValue("Не верное число!")
                } else {
                    val box = loadHandler.getCurrentBox()
                    box!!.countBox = place
                    saveBox(box)
                }
            }
            EDIT_COUNT_DIALOG -> {
                val count = editNumberDialog.data?.toFloatOrNull()
                if (count == null) {
                    messageErrorChannel.postValue("Не верное число!")
                } else {
                    val box = loadHandler.getCurrentBox()
                    box!!.count = count
                    saveBox(box)
                }
            }
            ADD_COUNT_DIALOG -> {
                val count = editNumberDialog.data?.toFloatOrNull() ?: 0f
                if (count == 0f) {
                    messageErrorChannel.postValue("Не верное число!")
                } else {
                    saveBoxByCount(count)
                }
            }


        }
    }

    @SuppressLint("CheckResult")
    private fun deleteBox() {
        if (!checkDocEdit()) {
            messageErrorChannel.value = "Нельзя изменять документ!"
            return
        }
        boxUseCase.delete(loadHandler.getCurrentBox()!!)
    }

    @SuppressLint("CheckResult")
    private fun saveBoxByCount(count: Float) {
        if (!checkDocEdit()) {
            messageErrorChannel.value = "Нельзя изменять документ!"
            return
        }

        val product = loadHandler.getCurrentProduct()

        val pallet = loadHandler.getCurrentPallet()

        val box = BoxCreatePallet(
            guid = UUID.randomUUID().toString(),
            guidPallet = pallet!!.guid,
            barcode = "",
            countBox = 1,
            count = count,
            dateChanged = Date()
        )

        boxUseCase.saveBoxByByffer(box)
    }

    @SuppressLint("CheckResult")
    fun readBarcode(barcode: String) {
        if (!checkDocEdit()) {
            messageErrorChannel.value = "Нельзя изменять документ!"
            return
        }

        val product = loadHandler.getCurrentProduct()


        val weight = getWeightByBarcode(
            barcode = barcode,
            start = product?.weightStartProduct ?: 0,
            finish = product?.weightEndProduct ?: 0,
            coff = product?.weightCoffProduct ?: 0f
        )

        if (weight == 0f) {
            throw Throwable("Ошибка считывания веса!")
        }

        val pallet = loadHandler.getCurrentPallet()

        val box = BoxCreatePallet(
            guid = UUID.randomUUID().toString(),
            guidPallet = pallet!!.guid,
            barcode = barcode,
            countBox = 1,
            count = weight,
            dateChanged = Date()
        )

        saveBox(box)
    }

    fun checkDocEdit():Boolean{
        val status = loadHandler.getCurrentDoc()?.status
        return checkDocumentUseCase.checkEditDocByStatus(status)
    }
    @SuppressLint("CheckResult")
    private fun saveBox(box:BoxCreatePallet) {
        boxUseCase.saveBoxByByffer(box)
    }
}
