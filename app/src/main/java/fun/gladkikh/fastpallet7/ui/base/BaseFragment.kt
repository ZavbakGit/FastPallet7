package `fun`.gladkikh.fastpallet7.ui.base

import `fun`.gladkikh.fastpallet7.ui.activity.MainActivity
import `fun`.gladkikh.fastpallet7.ui.common.Command
import `fun`.gladkikh.fastpallet7.ui.common.Command.ConfirmDialog
import `fun`.gladkikh.fastpallet7.ui.common.Command.EditNumberDialog
import `fun`.gladkikh.fastpallet7.ui.common.startConfirmDialog
import `fun`.gladkikh.fastpallet7.ui.common.startEditDialogNumber
import `fun`.gladkikh.fastpallet7.ui.navigate.NavigateHandler
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController

abstract class BaseFragment : Fragment() {
    protected abstract val layoutRes: Int

    protected lateinit var mainActivity: MainActivity
    protected lateinit var navigateHandler: NavigateHandler
    protected abstract val viewModel: BaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity = activity as MainActivity
        navigateHandler = mainActivity.navigateHandler
    }

    override fun onResume() {
        super.onResume()
        initSubscription()
    }

    private val commandObservable = Observer<Command> {
        commandListener(it)
    }

    private val keyDownObservable = Observer<Int> {
        keyDownListener(it)
    }

    open fun keyDownListener(keyCode: Int) {

    }

    open fun commandListener(command: Command) {
        when (command) {
            is ConfirmDialog -> {
                startConfirmDialog(activity!!, command) {
                    viewModel.callBackConfirmDialog(it)
                }
            }
            is EditNumberDialog -> {
                startEditDialogNumber(
                    activity!!
                        .supportFragmentManager, command
                ) {
                    viewModel.callBackEditNumberDialog(it)
                }
            }
        }
    }

    protected open fun initSubscription() {
        viewModel.getMessageChannel().observe(viewLifecycleOwner, Observer {
            mainActivity.showMessage(it)
        })

        viewModel.getMessageErrorChannel().observe(viewLifecycleOwner, Observer {
            mainActivity.showErrorMessage(it)
        })

        viewModel.getShowProgressChannel().observe(viewLifecycleOwner, Observer {
            if (it) {
                mainActivity.showProgress()
            } else {
                mainActivity.hideProgress()
            }
        })

        mainActivity.getKeyDownLiveData().observe(viewLifecycleOwner, keyDownObservable)
        viewModel.getCommandChannel().observe(viewLifecycleOwner, commandObservable)
    }
}