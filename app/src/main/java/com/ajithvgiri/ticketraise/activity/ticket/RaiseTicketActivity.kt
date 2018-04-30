package com.ajithvgiri.ticketraise.activity.ticket

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.ajithvgiri.movies.volley.ServiceVolley
import com.ajithvgiri.ticketraise.R
import com.ajithvgiri.ticketraise.activity.home.HomeActivity
import com.ajithvgiri.ticketraise.model.Ticket
import com.ajithvgiri.ticketraise.utils.AppPreferences
import com.ajithvgiri.ticketraise.utils.AppUtils
import com.ajithvgiri.ticketraise.volley.Api
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_raise_ticket.*
import org.json.JSONObject

class RaiseTicketActivity : AppCompatActivity() {

    var TAG: String = "RaiseTicketActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_raise_ticket)

        buttonSubmit.setOnClickListener {
            val title = editTextTitle.text.toString()
            val details = editTextDetails.text.toString()

            if (!title.isEmpty() && !details.isEmpty()){
                editTextTitle.isEnabled = false
                editTextDetails.isEnabled = false
                buttonSubmit.isEnabled = false
                progressBar.visibility =View.VISIBLE

                raiseTicket(title,details)
            }else{
                Toast.makeText(this,"Enter Title and Details to Raise a Ticket",Toast.LENGTH_LONG).show()
            }
        }
    }

    fun raiseTicket(title:String,details:String){
        val gson = Gson()
        val json = JSONObject(gson.toJson(Ticket("",AppPreferences(this).getId(),details,title,"REQUESTED")))

        ServiceVolley().post(Api.instance.raiseticket, json) { response ->
            if (response != null) {
                if (response.getInt("status") == 1) {
                    Toast.makeText(this,"Successfully ticket created",Toast.LENGTH_LONG).show()
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show()
                }
            }else{
                AppUtils.instance.errorLog(TAG,"Something went wrong !")
            }
            progressBar.visibility =View.GONE
            editTextTitle.isEnabled = true
            editTextDetails.isEnabled = true
            buttonSubmit.isEnabled = true
        }
    }
}
