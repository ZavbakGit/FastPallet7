package `fun`.gladkikh.fastpallet7

import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.BoxCreatePallet
import `fun`.gladkikh.fastpallet7.model.usecase.testdata.AddTestDataUseCase
import `fun`.gladkikh.fastpallet7.repository.CreatePalletRepositoryUpdate
import `fun`.gladkikh.fastpallet7.ui.createpallet.BoxCreatPalletViewModel
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    val addTestDataUseCase : AddTestDataUseCase by inject()

    val rep: CreatePalletRepositoryUpdate by inject()

    val viewModel: BoxCreatPalletViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addTestData()

        //viewModel.setGuid()


    }


    fun addTestData(){
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
            .subscribe{
                tvInfo.text = "Finish"
            }
    }
}
