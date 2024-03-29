package `fun`.gladkikh.fastpallet7.ui.activity

import `fun`.gladkikh.fastpallet7.R
import `fun`.gladkikh.fastpallet7.repository.SettingsRepository
import `fun`.gladkikh.fastpallet7.ui.base.BaseActivity
import `fun`.gladkikh.fastpallet7.ui.navigate.NavigateHandler
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.gladkikh.mylibrary.BarcodeHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.progress_overlay.*
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity() {
    override val layoutRes = R.layout.activity_main

    private var barcodeHelper: BarcodeHelper? = null
    private val settingsRepository: SettingsRepository by inject()

    private val barcodeObserver = Observer<String> {
        if (isShowProgress.value != true) {
            barcodeLiveData.postValue(it)
        }
    }


    lateinit var navigateHandler: NavigateHandler
        private set


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateHandler =
            NavigateHandler(Navigation.findNavController(this, R.id.nav_host_fragment))

        refreshSettingApp()
    }

    fun refreshSettingApp() {
        settingsRepository.refresh()

        barcodeHelper?.getBarcodeLiveData()?.removeObserver(barcodeObserver)

        barcodeHelper = BarcodeHelper(
            this,
            BarcodeHelper.TYPE_TSD.getTypeTSD(settingsRepository.settingApp!!.typeTsd)
        )
        barcodeHelper?.getBarcodeLiveData()?.observe(this, barcodeObserver)
    }

    fun showMessage(text: CharSequence) {
        Snackbar.make(root, text, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    fun showErrorMessage(text: CharSequence) {
        Snackbar.make(root, text, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    fun showProgress() {
        progressView.visibility = View.VISIBLE
        isShowProgress.postValue(true)
    }

    fun hideProgress() {
        progressView.visibility = View.GONE
        isShowProgress.postValue(false)
    }
}