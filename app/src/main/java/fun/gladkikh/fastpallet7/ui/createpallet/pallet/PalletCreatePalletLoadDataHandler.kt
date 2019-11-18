package `fun`.gladkikh.fastpallet7.ui.createpallet.pallet

import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.BoxCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.CreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.PalletCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.ProductCreatePallet
import `fun`.gladkikh.fastpallet7.repository.CreatePalletRepositoryUpdate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class PalletCreatePalletLoadDataHandler(
    compositeDisposable: CompositeDisposable,
    private val repository: CreatePalletRepositoryUpdate
) {
    private val publishSubject = PublishSubject.create<String>()

    private val listBox = MutableLiveData<List<BoxCreatePallet>>()
    private val pallet = MutableLiveData<PalletCreatePallet>()
    private val product = MutableLiveData<ProductCreatePallet>()
    private val doc = MutableLiveData<CreatePallet>()

    fun getListBoxLiveData():LiveData<List<BoxCreatePallet>> = listBox
    fun getPalletLiveData():LiveData<PalletCreatePallet> = pallet
    fun getProductLiveData():LiveData<ProductCreatePallet> = product
    fun getDocLiveData():LiveData<CreatePallet> = doc

    fun getCurrentListBox() = listBox.value
    fun getCurrentPallet() = pallet.value
    fun getCurrentProduct() = product.value
    fun getCurrentDoc() = doc.value

    init {
        compositeDisposable.add(
            getLoadDataFlowable()
                .subscribe()
        )
    }

    private fun getLoadDataFlowable(): Flowable<*> {
        return publishSubject.toFlowable(BackpressureStrategy.BUFFER)
            .observeOn(Schedulers.io())
            .doOnNext {
                listBox.postValue(repository.getgetListBoxByGuidPallet(it))
                pallet.postValue(repository.getObjectCreatePalletByGuid<PalletCreatePallet>(it) as PalletCreatePallet)
                product.postValue(repository.getObjectCreatePalletByGuid<ProductCreatePallet>("0_0") as ProductCreatePallet)
                doc.postValue(repository.getObjectCreatePalletByGuid<CreatePallet>("0") as CreatePallet)
            }
    }

    fun setGuid(guid: String) {
        publishSubject.onNext(guid)
    }
}