package com.ajithvgiri.ticketraise.utils

import android.content.Context
import android.preference.PreferenceManager

/**
 * Created by ajithvgiri on 24/11/17.
 */
class AppPreferences(context: Context) {

    private var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun setUserLogin(table: String, id: Int, tableID: Int, role: Int) {
        val editor = sharedPreferences!!.edit()
        editor.putInt("id", id)
        editor.putString("table", table)
        editor.putInt("tableID", tableID)
        editor.putInt("role", role)
        editor.apply()
    }


    fun getTable(): String = sharedPreferences.getString("table", "")

//    fun getTableID(): Int = sharedPreferences.getInt("tableID", 0)

    fun getId(): Int = sharedPreferences.getInt("id", 0)

    fun getRole(): Int = sharedPreferences.getInt("role", 0)


    fun logOut() = sharedPreferences.edit().clear().apply()


}

