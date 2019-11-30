package `fun`.gladkikh.fastpallet7.ui.createpallet.productdialog

import `fun`.gladkikh.fastpallet7.Constants
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.CreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.ProductCreatePallet
import `fun`.gladkikh.fastpallet7.model.usecase.check.CheckDocumentUseCase
import `fun`.gladkikh.fastpallet7.model.usecase.creatpallet.ProductActionUseCase
import `fun`.gladkikh.fastpallet7.repository.CreatePalletRepositoryUpdate
import `fun`.gladkikh.fastpallet7.ui.base.BaseViewModel
import `fun`.gladkikh.fastpallet7.ui.common.Command
import android.annotation.SuppressLint
import androidx.lifecycle.LiveData


class ProductDialogCreatePalletViewModel(
    private val repository: CreatePalletRepositoryUpdate,
    private val checkDocumentUseCase: CheckDocumentUseCase
) : BaseViewModel() {

    val EDIT_START_DIALOG = 1
    val EDIT_END_DIALOG = 2
    val EDIT_COFF_DIALOG = 3

    private val loadHandler = ProductDialodCreatePalletLoadDataHandler(
        compositeDisposable,
        repository
    )

    private val useCase = ProductActionUseCase(
        compositeDisposable = compositeDisposable,
        repository = repository,
        //После сохранения всего списка
        afterSaveFun = { product ->
            setGuid(product.guid)
        })

    fun getDocLiveData(): LiveData<CreatePallet> = loadHandler.getDocLiveData()
    fun getProductLiveData(): LiveData<ProductCreatePallet> = loadHandler.getProductLiveData()


    fun setGuid(guidBox: String?) {
        guidBox?.let { loadHandler.setGuid(it) }
    }

    fun getGuid(): String? {
        return loadHandler.getCurrentProduct()?.guid
    }


    fun checkDocEdit(): Boolean {
        val status = loadHandler.getCurrentDoc()?.status
        return checkDocumentUseCase.checkEditDocByStatus(status)
    }

    fun keyDown(keyCode: Int) {
        when (keyCode) {
            Constants.KEY_2 -> {
                startEditStart()
            }
            Constants.KEY_3 -> {
                startEditEnd()
            }
            Constants.KEY_4 -> {
                startEditCoff()
            }
        }
    }

    override fun callBackEditNumberDialog(editNumberDialog: Command.EditNumberDialog) {
        super.callBackEditNumberDialog(editNumberDialog)
        when (editNumberDialog.requestCode) {
            EDIT_START_DIALOG -> {
                val start = editNumberDialog.data?.toIntOrNull()
                val pr = loadHandler.getCurrentProduct()!!.copy(weightStartProduct = start)
                useCase.save(pr)

            }
            EDIT_END_DIALOG -> {
                val end = editNumberDialog.data?.toIntOrNull()
                val pr = loadHandler.getCurrentProduct()!!.copy(weightEndProduct = end)
                useCase.save(pr)

            }
            EDIT_COFF_DIALOG -> {
                val coff = editNumberDialog.data?.toFloatOrNull()
                val pr = loadHandler.getCurrentProduct()!!
                    .copy(weightCoffProduct = coff)
                useCase.save(pr)
            }
        }
    }

    fun startEditStart() {
        if (!checkDocEdit()) {
            messageErrorChannel.value = "Нельзя изменять документ!"
            return
        }
        commandChannel.postValue(
            Command.EditNumberDialog(
                "Начало",
                EDIT_START_DIALOG,
                false,
                (loadHandler.getCurrentProduct()?.weightStartProduct ?: 0).toString()
            )
        )
    }

    fun startEditEnd() {
        if (!checkDocEdit()) {
            messageErrorChannel.value = "Нельзя изменять документ!"
            return
        }
        commandChannel.postValue(
            Command.EditNumberDialog(
                "Конец",
                EDIT_END_DIALOG,
                false,
                (loadHandler.getCurrentProduct()?.weightEndProduct ?: 0).toString()
            )
        )
    }

    fun startEditCoff() {
        if (!checkDocEdit()) {
            messageErrorChannel.value = "Нельзя изменять документ!"
            return
        }
        commandChannel.postValue(
            Command.EditNumberDialog(
                "Коэффицент",
                EDIT_COFF_DIALOG,
                true,
                (loadHandler.getCurrentProduct()?.weightCoffProduct ?: 0).toString()
            )
        )
    }

    @SuppressLint("CheckResult")
    fun readBarcode(barcode: String) {
        if (!checkDocEdit()) {
            messageErrorChannel.value = "Нельзя изменять документ!"
            return
        }
        val pr = loadHandler.getCurrentProduct()!!
            .copy(barcode = barcode)
        useCase.save(pr)
    }

}

