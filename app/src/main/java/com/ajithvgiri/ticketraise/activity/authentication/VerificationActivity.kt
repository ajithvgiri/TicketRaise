package com.ajithvgiri.ticketraise.activity.authentication

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.ajithvgiri.ticketraise.R
import com.ajithvgiri.ticketraise.utils.AppUtils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_verification.*

class VerificationActivity : AppCompatActivity() {

    var TAG: String = "PhoneVerificationActivity"

    lateinit var firebaseAuth: FirebaseAuth
    private var mVerificationId = ""
    lateinit var credential: PhoneAuthCredential
    private var mobile = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)

        firebaseAuth = FirebaseAuth.getInstance()

        val extras = intent.extras
        if (extras != null) {
            if (extras.getString("mVerificationId") != null) {
                mVerificationId = extras.getString("mVerificationId")
            }

            if (extras.get("credential") != null) {
                credential = extras.get("credential") as PhoneAuthCredential
                signInWithPhoneAuthCredential(credential)
            }

        }

        buttonNext.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            verifyPhoneNumberWithCode(mVerificationId,editTextCode.text.toString())
        }

    }


    private fun verifyPhoneNumberWithCode(verificationId: String, code: String) {
        // [START verify_with_code]
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential)
    }

    // [START sign_in_with_phone]
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
                    progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        AppUtils.instance.debugLog(TAG, "signInWithCredential:success")
                        val intent = Intent(this, ProfileActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        intent.putExtra("mobile",mobile)
                        startActivity(intent)
                        finish()
                        // [START_EXCLUDE]
                        //updateUI(STATE_SIGNIN_SUCCESS, user);
                        // [END_EXCLUDE]
                    } else {
                        // Sign in failed, display a message and update the UI
                        AppUtils.instance.debugLog(TAG, "signInWithCredential:failure" + task.exception!!)
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            // [START_EXCLUDE silent]
                            Toast.makeText(this,"",Toast.LENGTH_LONG).show()
                            // [END_EXCLUDE]
                        }
                        // [START_EXCLUDE silent]
                        // Update UI
                        //updateUI(STATE_SIGNIN_FAILED);
                        // [END_EXCLUDE]
                    }
                })
    }
    // [END sign_in_with_phone]
}
