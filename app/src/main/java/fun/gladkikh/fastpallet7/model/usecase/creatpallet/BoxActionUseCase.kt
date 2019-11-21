package `fun`.gladkikh.fastpallet7.model.usecase.creatpallet


import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.BoxCreatePallet
import `fun`.gladkikh.fastpallet7.repository.CreatePalletRepositoryUpdate
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class BoxActionUseCase(
    compositeDisposable: CompositeDisposable,
    private val repository: CreatePalletRepositoryUpdate,
    private val beforeAddFun: (box: BoxCreatePallet, buffer: Int) -> Unit,
    private val afterSaveFun: (lastBox: BoxCreatePallet, buffer: Int) -> Unit,
    private val afterDellFun: () -> Unit
) {
    private val publishSubjectSaveBuffer = PublishSubject.create<List<BoxCreatePallet>>()
    private val publishSubjectDell = PublishSubject.create<BoxCreatePallet>()
    private val publishSubjectSave = PublishSubject.create<BoxCreatePallet>()


    private val bufferBoxList: MutableList<BoxCreatePallet> = mutableListOf()


    init {
        compositeDisposable.add(
            getFlowableBox()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )

        compositeDisposable.add(
            deleteCompletable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )

        compositeDisposable.add(
            saveCompletable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )

    }

    private fun getFlowableBox(): Flowable<BoxCreatePallet> {
        return publishSubjectSaveBuffer.toFlowable(BackpressureStrategy.BUFFER)
            .debounce(2000, TimeUnit.MILLISECONDS)
            .map {
                val list = bufferBoxList.map {
                    it.copy()
                }
                bufferBoxList.clear()
                repository.saveListBox(list)
                return@map list.last()
            }
            .doOnNext {
                afterSaveFun(it, bufferBoxList.size)
            }
    }

    private fun saveCompletable(): Completable {
        return publishSubjectSave
            .observeOn(Schedulers.io())
            .map {
                repository.save(it)
                return@map it
            }
            .doOnNext {
                afterSaveFun(it,0)
            }
            .ignoreElements()
    }

    private fun deleteCompletable(): Completable {
        return publishSubjectDell
            .observeOn(Schedulers.io())
            .map {
                repository.delete(it)
                return@map it
            }
            .doOnNext {
                afterDellFun()
            }
            .ignoreElements()
    }

    fun delete(box: BoxCreatePallet) {
        publishSubjectDell.onNext(box)
    }

    fun saveBox(box: BoxCreatePallet){
        publishSubjectSave.onNext(box)
    }

    fun saveBoxByByffer(box: BoxCreatePallet) {
        bufferBoxList.add(box)
        beforeAddFun(box, bufferBoxList.size)
        publishSubjectSaveBuffer.onNext(bufferBoxList)
    }
}