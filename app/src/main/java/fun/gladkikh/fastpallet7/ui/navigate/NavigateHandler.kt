package `fun`.gladkikh.fastpallet7.ui.navigate

import `fun`.gladkikh.fastpallet7.Constants
import `fun`.gladkikh.fastpallet7.R
import `fun`.gladkikh.fastpallet7.ui.createpallet.box.BoxCreatePalletFragment
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import com.gladkikh.mylibrary.BarcodeHelper

class NavigateHandler(val navController: NavController){

    fun startSettings(){
        navigate(R.id.settingsFragment,null)
    }

    fun startCreatePalletBox(guidBox:String){
        val bundle = Bundle()
        bundle.putString(Constants.EXTRA_GUID,guidBox)
        navigate(R.id.boxCreatePalletFragment,bundle)
    }

    fun startPalletCreatePalletBox(guidPallet:String){
        val bundle = Bundle()
        bundle.putString(Constants.EXTRA_GUID,guidPallet)
        navigate(R.id.palletCreatePalletFragment,bundle)
    }

    fun popBackStack(){
        navController.popBackStack()
    }

    private fun navigate(@IdRes resId:Int,args: Bundle?){
        navController.navigate(resId,args)
    }



}