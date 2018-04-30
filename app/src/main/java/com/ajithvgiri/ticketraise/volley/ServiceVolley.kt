package com.ajithvgiri.movies.volley

import com.ajithvgiri.ticketraise.MyApplication
import com.ajithvgiri.ticketraise.utils.AppUtils
import com.ajithvgiri.ticketraise.volley.Api
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

/**
 * Created by ajithvgiri on 10/11/17.
 */
class ServiceVolley : ServiceInterface {

    val TAG = ServiceVolley::class.java.simpleName

    override fun post(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit) {
        AppUtils.instance.debugLog(TAG, "/post request params: $params")
        val jsonObjReq = object : JsonObjectRequest(Method.POST, Api.instance.server + path, params,
                Response.Listener<JSONObject> { response ->
                    AppUtils.instance.debugLog(TAG, "/post request OK! Response: $response")
                    completionHandler(response)
                },
                Response.ErrorListener { error ->
                    AppUtils.instance.errorLog(TAG, "/post request Failed! Error: ${error.message}")
                    completionHandler(null)
                }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                return headers
            }
        }

        MyApplication.instance?.addToRequestQueue(jsonObjReq, TAG)
    }

    override fun get(path: String, completionHandler: (response: JSONObject?) -> Unit) {
        val jsonObjReq = object : JsonObjectRequest(Method.GET, path, null, Response.Listener<JSONObject> { response ->
            AppUtils.instance.debugLog(TAG, "/get request OK! Response: $response")
            completionHandler(response)
        },
                Response.ErrorListener { error ->
                    AppUtils.instance.errorLog(TAG, "/get request Failed! Error: ${error.message}")
                    completionHandler(null)
                }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                return headers
            }
        }

        MyApplication.instance?.addToRequestQueue(jsonObjReq, TAG)

    }

}