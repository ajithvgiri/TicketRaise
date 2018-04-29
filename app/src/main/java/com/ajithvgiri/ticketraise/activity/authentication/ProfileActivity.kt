package com.ajithvgiri.ticketraise.activity.authentication

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.ajithvgiri.movies.volley.ServiceVolley
import com.ajithvgiri.ticketraise.R
import com.ajithvgiri.ticketraise.model.User
import com.ajithvgiri.ticketraise.volley.Api
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_profile.*
import org.json.JSONObject

class ProfileActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        var mobile = ""
        val extras = intent.extras
        if (extras != null) {
            if (extras.getString("mobile") != null) {
                mobile = extras.getString("mobile")
            }
        }

        buttonNext.setOnClickListener {
            if (!editTextFullname.text.isEmpty()){
                login(editTextFullname.text.toString(),mobile)
                progressBar.visibility = View.VISIBLE
                editTextFullname.isEnabled = false
                buttonNext.isEnabled = false
            }else{
                Toast.makeText(this,"Enter valid full name",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun login(fullname: String, mobile: String) {

        val gson = Gson()
        val json = JSONObject(gson.toJson(User(fullname,mobile,"android","")))

        ServiceVolley().post(Api.instance.login, json) { response ->
            if (response != null) {
                if (response.getInt("status") == 1) {
                    var table = response.getJSONObject("result")
//                    Preferences(this).setUserLogin(table.getString("table_name"), table.getInt("user_id"), table.getInt("table_id"), table.getInt("user_role"))
//                    when (table.getInt("user_role")) {
//                        1 -> startActivity(Intent(this, WaiterDashboard::class.java))
//                        2 -> startActivity(Intent(this, CashierDashboard::class.java))
//                        3 -> startActivity(Intent(this, KitchenListing::class.java))
//                    }

                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    progressBar.visibility = View.GONE
                    editTextFullname.isEnabled = true
                    buttonNext.isEnabled = true
                    Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}
