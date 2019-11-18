package `fun`.gladkikh.fastpallet7.ui.createpallet.box


import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.BoxCreatePallet
import `fun`.gladkikh.fastpallet7.repository.CreatePalletRepositoryUpdate
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class AddHandler(
    compositeDisposable: CompositeDisposable,
    private val repository: CreatePalletRepositoryUpdate,
    private val beforeAddFun: (box: BoxCreatePallet, buffer: Int) -> Unit,
    private val afterSaveFun: (lastBox: BoxCreatePallet, buffer: Int) -> Unit
) {
    private val publishSubject = PublishSubject.create<List<BoxCreatePallet>>()
    private val bufferBoxList: MutableList<BoxCreatePallet> = mutableListOf()


    init {
        compositeDisposable.add(
            getFlowableBox()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    private fun getFlowableBox(): Flowable<BoxCreatePallet> {
        return publishSubject.toFlowable(BackpressureStrategy.BUFFER)
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


    fun saveBox(box: BoxCreatePallet) {
        bufferBoxList.add(box)
        beforeAddFun(box, bufferBoxList.size)
        publishSubject.onNext(bufferBoxList)
    }
}