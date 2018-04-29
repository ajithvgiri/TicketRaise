package com.ajithvgiri.ticketraise.volley

class Api private constructor(){

    companion object {
        private var api: Api = Api()

        val instance: Api
            get() = api
    }

    val server= "http://example.com/"

    //Login
    val login = "login"
}