package com.ajithvgiri.ticketraise.utils

import android.content.Context
import android.preference.PreferenceManager

/**
 * Created by ajithvgiri on 24/11/17.
 */
class AppPreferences(context: Context) {

    private var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun setUserLogin( id: String,fullname:String,mobile: String, status: Int) {
        val editor = sharedPreferences!!.edit()
        editor.putString("id", id)
        editor.putString("fullname",fullname)
        editor.putString("mobile", mobile)
        editor.putInt("status", status)
        editor.apply()
    }

    fun setMobile(mobile:String){
        val editor = sharedPreferences!!.edit()
        editor.putString("mobile", mobile)
        editor.apply()
    }



    fun getMobile(): String = sharedPreferences.getString("mobile", "")

//    fun getTableID(): Int = sharedPreferences.getInt("tableID", 0)

    fun getId(): String = sharedPreferences.getString("id", "")

    fun getRole(): Int = sharedPreferences.getInt("role", 0)


    fun logOut() = sharedPreferences.edit().clear().apply()


}

