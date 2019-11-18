package `fun`.gladkikh.fastpallet7.ui.test

import `fun`.gladkikh.fastpallet7.R
import `fun`.gladkikh.fastpallet7.ui.base.BaseFragment
import kotlinx.android.synthetic.main.test_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TestFragment : BaseFragment() {
    override val layoutRes = R.layout.test_fragment
    override val viewModel: TestViewModel by viewModel()

    override fun initSubscription() {
        super.initSubscription()

        btAddTestData.setOnClickListener {
            viewModel.addTestData()
        }
        btRecalc.setOnClickListener {
            viewModel.recalc()
        }
        btOpenBox.setOnClickListener {
            navigateHandler.startCreatePalletBox("0_0_0_0")
        }

        btOpenPallet.setOnClickListener {
            navigateHandler.startPalletCreatePalletBox("0_0_0")
        }

        btSettings.setOnClickListener {
            navigateHandler.startSettings()
        }
    }
}