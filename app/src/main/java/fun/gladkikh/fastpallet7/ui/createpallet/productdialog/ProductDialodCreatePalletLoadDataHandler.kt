package `fun`.gladkikh.fastpallet7.ui.createpallet.productdialog

import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.CreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.ProductCreatePallet
import `fun`.gladkikh.fastpallet7.repository.CreatePalletRepositoryUpdate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class ProductDialodCreatePalletLoadDataHandler(
    compositeDisposable: CompositeDisposable,
    private val repository: CreatePalletRepositoryUpdate
) {
    private val publishSubject = PublishSubject.create<String>()


    private val product = MutableLiveData<ProductCreatePallet>()
    private val doc = MutableLiveData<CreatePallet>()

    fun getProductLiveData(): LiveData<ProductCreatePallet> = product
    fun getDocLiveData(): LiveData<CreatePallet> = doc

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
                val size = repository.getgetListBoxByGuidPallet(it).size
                val pr = repository.getObjectCreatePalletByGuid<ProductCreatePallet>(it) as ProductCreatePallet
                product.postValue(pr)
                doc.postValue(repository.getObjectCreatePalletByGuid<CreatePallet>(pr.guidDoc) as CreatePallet)
            }
    }

    fun setGuid(guidProduct: String) {
        publishSubject.onNext(guidProduct)
    }
}