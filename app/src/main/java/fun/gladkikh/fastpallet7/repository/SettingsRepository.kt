package `fun`.gladkikh.fastpallet7.repository

import `fun`.gladkikh.fastpallet7.model.entity.SettingApp
import android.content.Context
import androidx.preference.PreferenceManager

class SettingsRepository(private val context: Context) {

    var settingApp: SettingApp? = null

    fun refresh() {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)

        val host = "preference_host"
        val login = "preference_login"
        val pass = "preference_pass"
        val code = "preference_code_1c"
        val listTsd = "list_tsd"


        settingApp = SettingApp(
            host = sharedPref.getString(host, "http://172.31.255.150/rmmt/hs/api/"),
            code = sharedPref.getString(code, "333"),
            login = sharedPref.getString(login, "Администратор"),
            pass = sharedPref.getString(pass, ""),
            typeTsd = sharedPref.getString(listTsd, 1.toString()).toIntOrNull()
        )
    }

    fun getSetting(): SettingApp {
        if (settingApp == null) {
            refresh()
        }
        return settingApp!!
    }

}