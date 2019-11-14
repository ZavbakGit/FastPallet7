package `fun`.gladkikh.fastpallet7

import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.BoxCreatePallet
import `fun`.gladkikh.fastpallet7.model.usecase.testdata.AddTestDataUseCase
import `fun`.gladkikh.fastpallet7.repository.CreatePalletRepositoryUpdate
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    val addTestDataUseCase : AddTestDataUseCase by inject()

    val rep: CreatePalletRepositoryUpdate by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Completable.fromCallable {
            addTestDataUseCase.save()

            val b =  BoxCreatePallet(
                guid = "777",
                guidPallet = "0_0_0",
                countBox = 100,
                count = 50f,
                barcode = "545",
                dateChanged = null
            )

            val b1 =  BoxCreatePallet(
                guid = "7778",
                guidPallet = "0_0_0",
                countBox = 100,
                count = 50f,
                barcode = "545",
                dateChanged = null
            )

            rep.save(b)
            rep.save(b1)

        }
            .subscribeOn(Schedulers.io())
            .subscribe()




    }
}
