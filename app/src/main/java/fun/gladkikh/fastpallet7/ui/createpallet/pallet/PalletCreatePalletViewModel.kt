package `fun`.gladkikh.fastpallet7.ui.createpallet.pallet


import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.BoxCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.CreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.PalletCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.ProductCreatePallet
import `fun`.gladkikh.fastpallet7.model.usecase.check.CheckDocumentUseCase
import `fun`.gladkikh.fastpallet7.repository.CreatePalletRepositoryUpdate
import `fun`.gladkikh.fastpallet7.ui.base.BaseViewModel
import androidx.lifecycle.LiveData

class PalletCreatePalletViewModel(
    private val repository: CreatePalletRepositoryUpdate,
    private val checkDocumentUseCase: CheckDocumentUseCase
) : BaseViewModel() {

    private val loadHandler = PalletCreatePalletLoadDataHandler(
        compositeDisposable,
        repository
    )

    fun getDocLiveData(): LiveData<CreatePallet> = loadHandler.getDocLiveData()
    fun getProductLiveData(): LiveData<ProductCreatePallet> = loadHandler.getProductLiveData()
    fun getPalletLiveData(): LiveData<PalletCreatePallet> = loadHandler.getPalletLiveData()
    fun getListBoxLiveData(): LiveData<List<BoxCreatePallet>> = loadHandler.getListBoxLiveData()

    fun setGuid(guidBox: String?) {
        guidBox?.let { loadHandler.setGuid(it) }
    }

    fun getGuid(): String? {
        return loadHandler.getCurrentPallet()?.guid
    }

}

