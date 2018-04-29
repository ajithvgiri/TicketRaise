package com.ajithvgiri.ticketraise.utils

import android.util.Log

class AppUtils private constructor(){

    companion object {
        private var utils: AppUtils = AppUtils()

        val instance: AppUtils
            get() = utils
    }

    fun debugLog(tag:String, message:String){
        Log.d(tag,message)
    }

    fun errorLog(tag:String, message:String){
        Log.e(tag,message)
    }
}