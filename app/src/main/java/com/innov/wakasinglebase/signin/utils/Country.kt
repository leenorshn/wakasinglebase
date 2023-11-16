package com.innov.wakasinglebase.signin.utils

import android.content.Context
import java.util.Locale


/**
 * Country object that holds the country iso2 code, name, and dial code.
 * @author Ismail Almetwally
 */
class Country(val code: String, val name: String, val dialCode: Int) {

    val displayName: String
        get() = Locale("", this.code).getDisplayCountry(Locale.US)

    fun getResId(context: Context): Int {
        val name = String.format("country_flag_%s", code.lowercase(Locale.getDefault()))
        val resources = context.resources
        return resources.getIdentifier(name, "drawable", context.packageName)
    }
}