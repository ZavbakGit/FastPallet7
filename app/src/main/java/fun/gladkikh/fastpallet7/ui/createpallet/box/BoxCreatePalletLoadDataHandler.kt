package `fun`.gladkikh.fastpallet7.ui.createpallet.box


import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.BoxCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.CreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.PalletCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.ProductCreatePallet
import `fun`.gladkikh.fastpallet7.repository.CreatePalletRepositoryUpdate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class BoxCreatePalletLoadDataHandler(
    compositeDisposable: CompositeDisposable,
    private val repository: CreatePalletRepositoryUpdate
) {
    private val publishSubject = PublishSubject.create<String>()

    private val box = MutableLiveData<BoxCreatePallet>()
    private val pallet = MutableLiveData<PalletCreatePallet>()
    private val product = MutableLiveData<ProductCreatePallet>()
    private val doc = MutableLiveData<CreatePallet>()

    fun getBoxLiveData():LiveData<BoxCreatePallet> = box
    fun getPalletLiveData():LiveData<PalletCreatePallet> = pallet
    fun getProductLiveData():LiveData<ProductCreatePallet> = product
    fun getDocLiveData():LiveData<CreatePallet> = doc

    fun getCurrentBox() = box.value
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
                box.postValue(repository.getObjectCreatePalletByGuid<BoxCreatePallet>(it) as BoxCreatePallet)
                pallet.postValue(repository.getObjectCreatePalletByGuid<PalletCreatePallet>("0_0_0") as PalletCreatePallet)
                product.postValue(repository.getObjectCreatePalletByGuid<ProductCreatePallet>("0_0") as ProductCreatePallet)
                doc.postValue(repository.getObjectCreatePalletByGuid<CreatePallet>("0") as CreatePallet)
            }
    }

    fun setOnlyBox(boxCreatePallet: BoxCreatePallet){
        box.postValue(boxCreatePallet)
    }

    fun setGuid(guid: String) {
        publishSubject.onNext(guid)
    }
}