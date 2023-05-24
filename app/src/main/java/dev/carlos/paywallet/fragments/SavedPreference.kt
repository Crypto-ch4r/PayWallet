package dev.carlos.paywallet.fragments


import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object SavedPreference {
    const val EMAIL = "email"
    const val NAMES = "names"
    const val LASTNAMES = "lastnames"
    const val RFID = "rfid"

    private fun getSharedPreference(ctx: Context?): SharedPreferences? {
        return PreferenceManager.getDefaultSharedPreferences(ctx)
    }

    private fun editor(context: Context, const: String, string: String) {
        getSharedPreference(
            context
        )?.edit()?.putString(const, string)?.apply()
    }

    fun getEmail(context: Context) = getSharedPreference(
        context
    )?.getString(EMAIL, "")

    fun setEmail(context: Context, email: String) {
        editor(
            context,
            EMAIL,
            email
        )
    }

    fun setNames(context: Context, names: String) {
        editor(
            context,
            NAMES,
            names
        )
    }

    fun getName(context: Context) = getSharedPreference(
        context
    )?.getString(NAMES, "")
}