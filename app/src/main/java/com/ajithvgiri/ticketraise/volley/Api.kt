package com.ajithvgiri.ticketraise.volley

class Api private constructor(){

    companion object {
        private var api: Api = Api()

        val instance: Api
            get() = api
    }

    val server= "https://coopathon.herokuapp.com/"

    //Login
    val register = "registerUser"

    var raiseticket = "createRequest"
}