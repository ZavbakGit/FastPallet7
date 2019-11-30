package `fun`.gladkikh.fastpallet7.model.usecase.creatpallet

import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.BoxCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.ProductCreatePallet
import `fun`.gladkikh.fastpallet7.repository.CreatePalletRepositoryUpdate
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class ProductActionUseCase(
    compositeDisposable: CompositeDisposable,
    private val repository: CreatePalletRepositoryUpdate,
    private val afterSaveFun: (product: ProductCreatePallet) -> Unit
) {

    private val publishSubjectSave = PublishSubject.create<ProductCreatePallet>()

    init {
        compositeDisposable.add(
            saveCompletable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    private fun saveCompletable(): Completable {
        return publishSubjectSave
            .observeOn(Schedulers.io())
            .map {
                repository.save(it)
                return@map it
            }
            .doOnNext {
                afterSaveFun(it)
            }
            .ignoreElements()
    }

    fun save(product: ProductCreatePallet){
        publishSubjectSave.onNext(product)
    }

}