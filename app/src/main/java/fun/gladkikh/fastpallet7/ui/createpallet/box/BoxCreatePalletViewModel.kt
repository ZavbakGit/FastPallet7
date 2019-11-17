package `fun`.gladkikh.fastpallet7.ui.createpallet.box

import `fun`.gladkikh.fastpallet7.Constants.KEY_1
import `fun`.gladkikh.fastpallet7.Constants.KEY_2
import `fun`.gladkikh.fastpallet7.Constants.KEY_3
import `fun`.gladkikh.fastpallet7.Constants.KEY_9
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.BoxCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.CreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.PalletCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.ProductCreatePallet
import `fun`.gladkikh.fastpallet7.model.usecase.creatpallet.BoxCreatePalletUseCase
import `fun`.gladkikh.fastpallet7.repository.createpallet.BoxCreatePalletScreenRepository
import `fun`.gladkikh.fastpallet7.ui.base.BaseViewModel
import `fun`.gladkikh.fastpallet7.ui.common.Command.*
import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

class BoxCreatePalletViewModel(
    private val repository: BoxCreatePalletScreenRepository,
    private val useCaseBox: BoxCreatePalletUseCase
) :
    BaseViewModel() {

    var box: BoxCreatePallet? = null
        private set
    var pallet: PalletCreatePallet? = null
        private set
    var product: ProductCreatePallet? = null
        private set
    var doc: CreatePallet? = null
        private set

    private val guidBoxLiveData = MutableLiveData<String>()

    private val docResult = Transformations.map(guidBoxLiveData) {
        repository.getDoc(it)
    }

    private val productResult = Transformations.map(guidBoxLiveData) {
        repository.getProduct(it)
    }

    private val palletResult = Transformations.map(guidBoxLiveData) {
        repository.getPallet(it)
    }
    private val boxResult = Transformations.map(guidBoxLiveData) {
        repository.getBox(it)
    }

    fun getDocLiveData(): LiveData<CreatePallet> = Transformations.switchMap(docResult) { it }
    fun getProductLiveData(): LiveData<ProductCreatePallet> =
        Transformations.switchMap(productResult) { it }

    fun getPalletLiveData(): LiveData<PalletCreatePallet> =
        Transformations.switchMap(palletResult) { it }

    fun getBoxLiveData(): LiveData<BoxCreatePallet> = Transformations.switchMap(boxResult) { it }

    private val CONFIRM_DELETE_DIALOG = 1
    private val EDIT_PLACE_DIALOG = 2
    private val EDIT_COUNT_DIALOG = 3
    private val ADD_COUNT_DIALOG = 4

    init {
        getBoxLiveData().observeForever {
            if (it == null) {
                commandChannel.postValue(Close)
            }
            box = it
        }
        getPalletLiveData().observeForever {
            pallet = it
        }
        getProductLiveData().observeForever {
            product = it
        }
        getDocLiveData().observeForever {
            doc = it
        }
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
                    box!!.countBox = place
                    saveBox()
                }
            }
            EDIT_COUNT_DIALOG -> {
                val count = editNumberDialog.data?.toFloatOrNull()
                if (count == null) {
                    messageErrorChannel.postValue("Не верное число!")
                } else {
                    box!!.count = count
                    saveBox()
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

    fun keyDown(keyCode: Int) {
        when (keyCode) {
            KEY_1 -> {
                startEditCount()
            }
            KEY_2 -> {
                startEditPlace()
            }
            KEY_3 -> {
                startAdd()
            }
            KEY_9 -> {
                startDelete()
            }
        }
    }

    fun setGuid(guidBox: String) {
        guidBoxLiveData.postValue(guidBox)
    }

    fun startEditPlace() {
        commandChannel.postValue(
            EditNumberDialog(
                "Мест",
                EDIT_PLACE_DIALOG,
                false,
                (box?.countBox ?: 0).toString()
            )
        )
    }

    fun startEditCount() {
        commandChannel.postValue(
            EditNumberDialog(
                "Количество",
                EDIT_COUNT_DIALOG,
                true,
                (box?.count ?: 0).toString()
            )
        )
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

    fun startDelete() {
        commandChannel.postValue(ConfirmDialog("Удаляем!", CONFIRM_DELETE_DIALOG))
    }

    @SuppressLint("CheckResult")
    private fun deleteBox() {
        useCaseBox.deleteBox(box)
            .subscribe({
                commandChannel.postValue(Close)
            }, {
                messageErrorChannel.postValue(it.message)
            })
    }


    @SuppressLint("CheckResult")
    private fun saveBoxByCount(count: Float) {
        useCaseBox.saveBoxByCountFlowable(count, pallet!!.guid)
            .subscribe({
                setGuid(it.guid)
            }, {
                messageErrorChannel.postValue(it.message)
            })
    }

    @SuppressLint("CheckResult")
    fun readBarcode(barcode: String) {
        useCaseBox.saveBoxByBarcodeFlowable(barcode, pallet!!.guid)
            .subscribe({
                setGuid(it.guid)
            }, {
                messageErrorChannel.postValue(it.message)
            })
    }

    @SuppressLint("CheckResult")
    private fun saveBox() {
        useCaseBox.saveBoxFlowable(box!!)
            .subscribe({
                setGuid(it.guid)
            }, {
                messageErrorChannel.postValue(it.message)
            })
    }
}

