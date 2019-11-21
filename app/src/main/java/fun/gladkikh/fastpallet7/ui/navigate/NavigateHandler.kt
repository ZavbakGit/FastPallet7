package `fun`.gladkikh.fastpallet7.ui.navigate

import `fun`.gladkikh.fastpallet7.Constants
import `fun`.gladkikh.fastpallet7.R
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController

class NavigateHandler(val navController: NavController){

    fun startSettings(){
        navigate(R.id.settingsFragment,null)
    }

    fun startCreatePalletProductDialog(guid:String){
        val bundle = Bundle()
        bundle.putString(Constants.EXTRA_GUID,guid)
        navigate(R.id.productDialogCreatePalletFragment,bundle)
    }

    fun startCreatePalletBox(guid:String){
        val bundle = Bundle()
        bundle.putString(Constants.EXTRA_GUID,guid)
        navigate(R.id.boxCreatePalletFragment,bundle)
    }

    fun startPalletCreatePalletBox(guid:String){
        val bundle = Bundle()
        bundle.putString(Constants.EXTRA_GUID,guid)
        navigate(R.id.palletCreatePalletFragment,bundle)
    }

    fun popBackStack(){
        navController.popBackStack()
    }

    private fun navigate(@IdRes resId:Int,args: Bundle?){
        navController.navigate(resId,args)
    }



}