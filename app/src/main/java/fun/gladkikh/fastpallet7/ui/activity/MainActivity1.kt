package `fun`.gladkikh.fastpallet7.ui.activity

import `fun`.gladkikh.fastpallet7.R
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.BoxCreatePallet
import `fun`.gladkikh.fastpallet7.model.usecase.testdata.AddTestDataUseCase
import `fun`.gladkikh.fastpallet7.repository.CreatePalletRepositoryUpdate
import `fun`.gladkikh.fastpallet7.ui.createpallet.BoxCreatePalletViewModel
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.create_pallet_fragment_box.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity1 : AppCompatActivity() {

    val addTestDataUseCase: AddTestDataUseCase by inject()

    val rep: CreatePalletRepositoryUpdate by inject()

    val viewModel: BoxCreatePalletViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_pallet_fragment_box)
        val fadeIn = AnimationUtils.loadAnimation(
            getApplicationContext(),
            R.anim.fade_in
        )

        tvCountBox.setOnClickListener {
            tvCountBox.startAnimation(fadeIn)
            tvCountPlaceBox.startAnimation(fadeIn)
            tvCountRowBox.startAnimation(fadeIn)

            AnimationUtils.loadAnimation(
                getApplicationContext(),
                R.anim.fade_in
            )
        }


        //addTestData()

//        viewModel.setGuid("0_0_0_0_")
//
//        viewModel.boxLiveData.observe(this, Observer {
//            it?.let {
//                tvInfo.text = "Box: ${it.guid} \nКол. ${it.count} Мест. ${it.countBox}"
//            }
//
//        })
//
//        viewModel.palletLiveData.observe(this, Observer {
//            it?.let {
//                tvPallet.text = "Pallet: ${it.guid}\n Кол. ${it.count} Мест. ${it.countBox} Стр. ${it.countRow}"
//            }
//        })
//
//        tvInfo.setOnClickListener {
//            viewModel.addBox()
//        }


    }


    fun addTestData() {
        Completable.fromCallable {
            addTestDataUseCase.save()

            val b = BoxCreatePallet(
                guid = "777",
                guidPallet = "0_0_0",
                countBox = 100,
                count = 50f,
                barcode = "545",
                dateChanged = null
            )

            val b1 = BoxCreatePallet(
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
            .subscribe {
                //tvInfo.text = "Finish"
            }
    }
}
