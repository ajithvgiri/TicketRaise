package com.ajithvgiri.ticketraise.activity.home

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ajithvgiri.ticketraise.R
import com.ajithvgiri.ticketraise.activity.ticket.RaiseTicketActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        floatingActionButton.setOnClickListener {
            startActivity(Intent(this,RaiseTicketActivity::class.java))
        }
    }
}
