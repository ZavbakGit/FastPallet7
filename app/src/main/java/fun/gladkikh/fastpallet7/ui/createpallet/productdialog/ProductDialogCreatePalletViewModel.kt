package `fun`.gladkikh.fastpallet7.ui.createpallet.productdialog

import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.CreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.ProductCreatePallet
import `fun`.gladkikh.fastpallet7.model.usecase.check.CheckDocumentUseCase
import `fun`.gladkikh.fastpallet7.repository.CreatePalletRepositoryUpdate
import `fun`.gladkikh.fastpallet7.ui.base.BaseViewModel
import android.annotation.SuppressLint
import androidx.lifecycle.LiveData


class ProductDialogCreatePalletViewModel(
    private val repository: CreatePalletRepositoryUpdate,
    private val checkDocumentUseCase: CheckDocumentUseCase
) : BaseViewModel() {

    private val loadHandler = ProductDialodCreatePalletLoadDataHandler(
        compositeDisposable,
        repository
    )

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


    @SuppressLint("CheckResult")
    fun readBarcode(barcode: String) {
        if (!checkDocEdit()) {
            messageErrorChannel.value = "Нельзя изменять документ!"
            return
        }
    }

}

