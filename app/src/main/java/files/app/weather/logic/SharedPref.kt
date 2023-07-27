package files.app.weather.logic

import android.content.Context
import android.content.SharedPreferences
import files.app.weather.R

class SharedPref(context: Context) {

    private val sharedPref: SharedPreferences
    private val prefKey = "pref_key"
    private val defCityNameKey = "def_city"

    fun setDefCityName(cityName: String) {
        val editor = sharedPref.edit()
        editor.putString(defCityNameKey, cityName)
        editor.apply()
    }

    fun getDefCityName(): String {
        val resultValue =
            sharedPref.getString(defCityNameKey, "Khmilnyk")
        return resultValue.toString()
    }

    init {
        sharedPref = context.getSharedPreferences(prefKey, Context.MODE_PRIVATE)
    }
}