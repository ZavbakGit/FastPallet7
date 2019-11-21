package `fun`.gladkikh.fastpallet7.ui.createpallet.pallet


import `fun`.gladkikh.fastpallet7.common.getWeightByBarcode
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.BoxCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.CreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.PalletCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.ProductCreatePallet
import `fun`.gladkikh.fastpallet7.model.usecase.check.CheckDocumentUseCase
import `fun`.gladkikh.fastpallet7.model.usecase.creatpallet.BoxActionUseCase
import `fun`.gladkikh.fastpallet7.repository.CreatePalletRepositoryUpdate
import `fun`.gladkikh.fastpallet7.ui.base.BaseViewModel
import `fun`.gladkikh.fastpallet7.ui.common.Command.*
import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import java.util.*

class PalletCreatePalletViewModel(
    private val repository: CreatePalletRepositoryUpdate,
    private val checkDocumentUseCase: CheckDocumentUseCase
) : BaseViewModel() {

    private val CONFIRM_DELETE_DIALOG = 1
    private val ADD_COUNT_DIALOG = 4

    private val loadHandler = PalletCreatePalletLoadDataHandler(
        compositeDisposable,
        repository
    )

    private val boxUseCase = BoxActionUseCase(
        compositeDisposable = compositeDisposable,
        repository = repository,
        //Берем данные из нового бокса
        beforeAddFun = { box, buffer ->

        },
        //После сохранения всего списка
        afterSaveFun = { box, buffer ->
            commandChannel.postValue(OpenForm(data = box.guid))
        },
        //После удаления закрываем
        afterDellFun = {
            setGuid(getGuid())
        })

    fun getDocLiveData(): LiveData<CreatePallet> = loadHandler.getDocLiveData()
    fun getProductLiveData(): LiveData<ProductCreatePallet> = loadHandler.getProductLiveData()
    fun getPalletLiveData(): LiveData<PalletCreatePallet> = loadHandler.getPalletLiveData()
    fun getListBoxLiveData(): LiveData<List<BoxCreatePallet>> = loadHandler.getListBoxLiveData()

    fun getCurrentProduct() = loadHandler.getCurrentProduct()

    fun setGuid(guidBox: String?) {
        guidBox?.let { loadHandler.setGuid(it) }
    }

    fun getGuid(): String? {
        return loadHandler.getCurrentPallet()?.guid
    }

    override fun callBackConfirmDialog(confirmDialog: ConfirmDialog) {
        super.callBackConfirmDialog(confirmDialog)
        when (confirmDialog.requestCode) {
            CONFIRM_DELETE_DIALOG -> {
                val position = confirmDialog.data as Int
                boxUseCase.delete(loadHandler.getCurrentListBox()!![position])
            }
        }
    }

    override fun callBackEditNumberDialog(editNumberDialog: EditNumberDialog) {
        super.callBackEditNumberDialog(editNumberDialog)
        when (editNumberDialog.requestCode) {
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

    fun startDell(position: Int) {
        commandChannel.postValue(ConfirmDialog("Удаляем!", CONFIRM_DELETE_DIALOG, position))
    }

    fun checkDocEdit(): Boolean {
        val status = loadHandler.getCurrentDoc()?.status
        return checkDocumentUseCase.checkEditDocByStatus(status)
    }

    fun startAdd() {
        commandChannel.postValue(
            EditNumberDialog(
                "Количество",
                ADD_COUNT_DIALOG,
                true,
                "0"
            )
        )
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

        boxUseCase.saveBox(box)
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

        boxUseCase.saveBox(box)
    }

}

