package bruno.fernandez.dogs.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

//This is a Singleton class. Looks almost like DogDatabase.
class SharedPreferencesHelper {

    //This creates static functions and classes to be accesed from outside the scope of the class
    companion object {

        private const val PREF_TIME = "Pref time"
        private var prefs: SharedPreferences? = null

        @Volatile
        private var instance: SharedPreferencesHelper? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): SharedPreferencesHelper =
            instance ?: synchronized(LOCK) {
                instance ?: buildHelper(context).also {
                    instance = it
                }
            }

        private fun buildHelper(context: Context): SharedPreferencesHelper {
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPreferencesHelper()
        }
    }

    fun saveUpdateTime(time: Long) {
        //This is a kotlin improvement only
        prefs?.edit(commit = true) {
            putLong(PREF_TIME, time)
        }
    }

    //0 is the default value
    fun getUpdateTime() = prefs?.getLong(PREF_TIME, 0)

}