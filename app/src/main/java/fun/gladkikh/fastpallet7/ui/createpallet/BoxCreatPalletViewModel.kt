package `fun`.gladkikh.fastpallet7.ui.createpallet

import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.BoxCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.PalletCreatePallet
import `fun`.gladkikh.fastpallet7.repository.createpallet.BoxCreatePalletScreenRepository
import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.*

class BoxCreatPalletViewModel(private val repository: BoxCreatePalletScreenRepository) :
    ViewModel() {

    private val publishSubject = PublishSubject.create<BoxCreatePallet>()
    private val guidBoxLiveData = MutableLiveData<String>()
    private val palletResult = Transformations.map(guidBoxLiveData) {
        repository.getPallet(it)
    }

    private val boxResult = Transformations.map(guidBoxLiveData) {
        repository.getBox(it)
    }

    val boxLiveData: LiveData<BoxCreatePallet> = Transformations.switchMap(boxResult) { it }
    val palletLiveData: LiveData<PalletCreatePallet> =
        Transformations.switchMap(palletResult) { it }

   private val disposables = CompositeDisposable()
    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    init {
        disposables.add(
            publishSubject.toFlowable(BackpressureStrategy.BUFFER)
                .observeOn(Schedulers.io())
                .doOnNext {
                    repository.saveBox(it)
                }
                .subscribe {
                    setGuid(it.guid)
                }

        )

    }


    fun setGuid(guidBox: String) {
        guidBoxLiveData.postValue(guidBox)
    }

    @SuppressLint("CheckResult")
    fun addBox() {
        val box = BoxCreatePallet(
            guid = UUID.randomUUID().toString(),
            guidPallet = palletLiveData.value!!.guid,
            count = 100f,
            countBox = 2,
            barcode = "64654",
            dateChanged = Date()
        )

        publishSubject.onNext(box)
    }

}

