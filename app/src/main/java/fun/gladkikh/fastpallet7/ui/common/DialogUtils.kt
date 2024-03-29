package `fun`.gladkikh.fastpallet7.ui.common

import `fun`.gladkikh.fastpallet7.ui.common.Command.ConfirmDialog
import `fun`.gladkikh.fastpallet7.ui.common.Command.EditNumberDialog
import `fun`.gladkikh.fastpallet7.ui.dialog.EditTextDialog
import android.content.Context
import android.text.InputType
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager

fun startConfirmDialog(
    context: Context,
    startConfirmDialog: ConfirmDialog,
    positiveFun: (startConfirmDialog: ConfirmDialog) -> Unit
) {
    AlertDialog.Builder(context)
        .setTitle("Вы уверены!")
        .setMessage(startConfirmDialog.message)
        .setNegativeButton(
            android.R.string.cancel,
            null
        ) // dismisses by default
        .setPositiveButton(android.R.string.ok) { dialog, which ->
            positiveFun(startConfirmDialog)
        }
        .show()
}

fun startEditDialogNumber(
    supportFragmentManager: FragmentManager,
    startEditNumberDialog: EditNumberDialog,
    positiveFun: (startEditNumberDialog: EditNumberDialog) -> Unit
) {

    val inputType = if (startEditNumberDialog.decimal) {
        (InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL)
    } else {
        InputType.TYPE_CLASS_NUMBER
    }

    val dialog = EditTextDialog.newInstance(
        text = startEditNumberDialog.data,
        hint = startEditNumberDialog.message,
        title = startEditNumberDialog.message,
        inputType = inputType
    )
    dialog.onOk = {
        val str = dialog.editText.text.toString()
        positiveFun(startEditNumberDialog.copy(data = str))
    }

    dialog.show(supportFragmentManager, "editDescription")
}



