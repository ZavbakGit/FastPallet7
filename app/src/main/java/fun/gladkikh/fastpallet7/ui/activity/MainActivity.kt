package `fun`.gladkikh.fastpallet7.ui.activity

import `fun`.gladkikh.fastpallet7.R
import `fun`.gladkikh.fastpallet7.ui.base.BaseActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.progress_overlay.*

class MainActivity : BaseActivity() {
    override val layoutRes = R.layout.activity_main
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
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