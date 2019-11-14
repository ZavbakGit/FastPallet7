package `fun`.gladkikh.fastpallet7.ui.createpallet

import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.BoxCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.PalletCreatePallet
import `fun`.gladkikh.fastpallet7.repository.createpallet.BoxCreatePalletScreenRepository
import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.util.*

class BoxCreatPalletViewModel(private val repository: BoxCreatePalletScreenRepository) :
    ViewModel() {

    private var guidBox: String? = null
    private var guidPallet: String? = null

    private val liveDataPallet: MutableLiveData<PalletCreatePallet> = MutableLiveData()

    private val liveDataBox: MutableLiveData<BoxCreatePallet> = MutableLiveData()

    private val observerPallet = Observer<PalletCreatePallet> {
        liveDataPallet.postValue(it)
    }

    private val observerBox = Observer<BoxCreatePallet> {
        liveDataBox.postValue(it)
    }

    private var repPalletCreatePallet: LiveData<PalletCreatePallet>? = null
    private var repBoxCreatePallet: LiveData<BoxCreatePallet>? = null


    fun getliveDataPallet(): LiveData<PalletCreatePallet> = liveDataPallet
    fun getliveDataBox(): LiveData<BoxCreatePallet> = liveDataBox

    fun setGuid(guidBox: String, guidPallet: String) {
        this.guidBox = guidBox
        this.guidPallet = guidPallet

        repPalletCreatePallet = repository.getPallet(guidPallet)
        repPalletCreatePallet?.observeForever(observerPallet)

        repBoxCreatePallet = repository.getBox(guidBox)
        repBoxCreatePallet?.observeForever(observerBox)
    }

    @SuppressLint("CheckResult")
    fun addBox() {
        val box = BoxCreatePallet(
            guid = UUID.randomUUID().toString(),
            guidPallet = guidPallet!!,
            count = 100f,
            countBox = 2,
            barcode = "64654",
            dateChanged = Date()
        )


        Flowable.just(box)
            .doOnNext {
                repository.saveBox(box)
            }
            .subscribeOn(Schedulers.io())
            .subscribe {
                setGuid(box.guid, box.guidPallet)
            }
    }

}

