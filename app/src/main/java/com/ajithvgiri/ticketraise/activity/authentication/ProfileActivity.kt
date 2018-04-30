package com.ajithvgiri.ticketraise.activity.authentication

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.ajithvgiri.movies.volley.ServiceVolley
import com.ajithvgiri.ticketraise.R
import com.ajithvgiri.ticketraise.activity.home.HomeActivity
import com.ajithvgiri.ticketraise.model.User
import com.ajithvgiri.ticketraise.utils.AppPreferences
import com.ajithvgiri.ticketraise.utils.AppUtils
import com.ajithvgiri.ticketraise.volley.Api
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_profile.*
import org.json.JSONObject

class ProfileActivity : AppCompatActivity() {

    lateinit var firebaseInstanceId: FirebaseInstanceId
    private var TAG = "ProfileActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        firebaseInstanceId = FirebaseInstanceId.getInstance()

        buttonNext.setOnClickListener {
            if (!editTextFullname.text.isEmpty()){
                login(editTextFullname.text.toString())
                progressBar.visibility = View.VISIBLE
                editTextFullname.isEnabled = false
                buttonNext.isEnabled = false
            }else{
                Toast.makeText(this,"Enter valid full name",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun login(fullname: String) {

        val gson = Gson()
        val json = JSONObject(gson.toJson(User("0",fullname,AppPreferences(this).getMobile(),"android", firebaseInstanceId.token!!)))

        ServiceVolley().post(Api.instance.register, json) { response ->
            if (response != null) {
                if (response.getInt("status") == 1) {
                    var userObject = response.getJSONObject("data").toString()
                    val gson = Gson()
                    val user = gson.fromJson(userObject, User::class.java)
                    //set user preference
                    AppPreferences(this).setUserLogin(user._id, user.fullName,user.mobile,0)

                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    progressBar.visibility = View.GONE
                    editTextFullname.isEnabled = true
                    buttonNext.isEnabled = true
                    Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show()
                }
            }else{
                AppUtils.instance.errorLog(TAG,"Something went wrong !")
            }

        }
    }
}
