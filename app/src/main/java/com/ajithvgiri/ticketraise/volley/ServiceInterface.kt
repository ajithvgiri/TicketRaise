package com.ajithvgiri.movies.volley

import org.json.JSONObject

/**
 * Created by ajithvgiri on 10/11/17.
 */
interface ServiceInterface {
    fun post(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit)
    fun get(path: String, completionHandler: (response: JSONObject?) -> Unit)
}