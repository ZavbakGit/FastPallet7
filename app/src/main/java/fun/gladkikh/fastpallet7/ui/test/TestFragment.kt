package `fun`.gladkikh.fastpallet7.ui.test

import `fun`.gladkikh.fastpallet7.R
import `fun`.gladkikh.fastpallet7.ui.base.BaseFragment
import `fun`.gladkikh.fastpallet7.ui.createpallet.BoxCreatePalletFragment
import android.os.Bundle
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
            val bundle = Bundle()
            bundle.putString(BoxCreatePalletFragment.EXTRA_GUID, "0_0_0_0")
            mainActivity.navController
                .navigate(R.id.action_testFragment_to_boxCreatPalletFragment, bundle)

        }
    }
}