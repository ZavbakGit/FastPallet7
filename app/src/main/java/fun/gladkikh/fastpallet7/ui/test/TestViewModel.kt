package `fun`.gladkikh.fastpallet7.ui.test

import `fun`.gladkikh.fastpallet7.model.usecase.recalcdb.RecalcDbUseCase
import `fun`.gladkikh.fastpallet7.model.usecase.testdata.AddTestDataUseCase
import `fun`.gladkikh.fastpallet7.repository.CreatePalletRepositoryUpdate
import `fun`.gladkikh.fastpallet7.ui.base.BaseViewModel
import android.annotation.SuppressLint
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

class TestViewModel(
    private val addTestDataUseCase: AddTestDataUseCase,
    private val recalcDbUseCase: RecalcDbUseCase,
    private val rep: CreatePalletRepositoryUpdate
) : BaseViewModel() {

    @SuppressLint("CheckResult")
    fun addTestData() {
        Completable.fromCallable {
            addTestDataUseCase.save()

//            val b = BoxCreatePallet(
//                guid = "777",
//                guidPallet = "0_0_0",
//                countBox = 100,
//                count = 50f,
//                barcode = "545",
//                dateChanged = null
//            )
//
//            val b1 = BoxCreatePallet(
//                guid = "7778",
//                guidPallet = "0_0_0",
//                countBox = 100,
//                count = 50f,
//                barcode = "545",
//                dateChanged = null
//            )
//
//            rep.save(b)
//            rep.save(b1)

        }
            .subscribeOn(Schedulers.io())
            .subscribe {
                messageChannel.postValue("Закончили!")
            }

    }


    fun recalc() {
        Completable.fromCallable {
            recalcDbUseCase.recalc()
        }
            .subscribeOn(Schedulers.io())
            .subscribe {
                messageChannel.postValue("Закончили!")
            }

    }
}