package `fun`.gladkikh.fastpallet7.ui.createpallet.pallet


import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.BoxCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.CreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.PalletCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.ProductCreatePallet
import `fun`.gladkikh.fastpallet7.model.usecase.creatpallet.BoxCreatePalletUseCase
import `fun`.gladkikh.fastpallet7.model.usecase.creatpallet.PalletCreatePalletUseCase
import `fun`.gladkikh.fastpallet7.repository.createpallet.PalletCreatePalletScreenRepository
import `fun`.gladkikh.fastpallet7.ui.base.BaseViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

class PalletCreatePalletViewModel(
    private val repository: PalletCreatePalletScreenRepository,
    private val useCaseBox: BoxCreatePalletUseCase,
    private val useCasePallet: PalletCreatePalletUseCase

) : BaseViewModel() {

    var pallet: PalletCreatePallet? = null
        private set
    var product: ProductCreatePallet? = null
        private set
    var doc: CreatePallet? = null
        private set
    var listBox: List<BoxCreatePallet> = listOf()

    private val guidPalletLiveData = MutableLiveData<String>()

    private val docResult = Transformations.map(guidPalletLiveData) {
        repository.getDoc(it)
    }

    private val productResult = Transformations.map(guidPalletLiveData) {
        repository.getProduct(it)
    }

    private val palletResult = Transformations.map(guidPalletLiveData) {
        repository.getPallet(it)
    }
    private val boxListResult = Transformations.map(guidPalletLiveData) {
        repository.getListBox(it)
    }

    fun getDocLiveData(): LiveData<CreatePallet> = Transformations.switchMap(docResult) { it }
    fun getProductLiveData(): LiveData<ProductCreatePallet> =
        Transformations.switchMap(productResult) { it }

    fun getPalletLiveData(): LiveData<PalletCreatePallet> =
        Transformations.switchMap(palletResult) { it }

    fun getListBoxLiveData(): LiveData<List<BoxCreatePallet>> =
        Transformations.switchMap(boxListResult) { it }

    init {
        getListBoxLiveData().observeForever {
            listBox = it
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

    fun setGuid(guidPallet: String) {
        guidPalletLiveData.postValue(guidPallet)
    }


}

