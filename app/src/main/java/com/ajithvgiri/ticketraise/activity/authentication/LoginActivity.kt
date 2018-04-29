package com.ajithvgiri.ticketraise.activity.authentication

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.ajithvgiri.ticketraise.R
import com.ajithvgiri.ticketraise.activity.home.HomeActivity
import com.ajithvgiri.ticketraise.utils.AppUtils
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit


class LoginActivity : AppCompatActivity() {

    var TAG: String = "LoginActivity"

    //Firebase Authentication
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var mResendToken: PhoneAuthProvider.ForceResendingToken
    var mVerificationId = ""
    private val mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verificaiton without
            //     user action.
            AppUtils.instance.debugLog(TAG, "onVerificationCompleted:" + credential)
            val intent = Intent(this@LoginActivity, VerificationActivity::class.java)
            intent.putExtra("credential", credential)
            startActivity(intent)
            finish()
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            AppUtils.instance.debugLog(TAG, "onVerificationFailed" + e)
            progressBar.visibility = View.GONE
            buttonNext.isEnabled = true
            editTextPhone.isEnabled = true

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                AppUtils.instance.debugLog(TAG, "Invalid request")
                // ...
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                AppUtils.instance.debugLog(TAG, "The SMS quota for the project has been exceeded")
                // ...
            }

            // Show a message and update the UI
            // ...
        }

        override fun onCodeSent(verificationId: String?,
                                token: PhoneAuthProvider.ForceResendingToken?) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            // Save verification ID and resending token so we can use them later
            mVerificationId = verificationId!!

            val intent = Intent(this@LoginActivity, VerificationActivity::class.java)
            intent.putExtra("mVerificationId", mVerificationId)
            startActivity(intent)
            AppUtils.instance.debugLog(TAG, "onCodeSent:" + verificationId!!)
//            mResendToken = token
            // ...
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        buttonNext.setOnClickListener {
            if (editTextPhone.text.length == 10) {
                val phoneUtil = PhoneNumberUtil.getInstance()
                try {
                    val phoneNumber = phoneUtil.parse(editTextPhone.text.toString(), "IN")
                    val formatedNumber = phoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164)
                    val phoneAuthProvider = PhoneAuthProvider.getInstance()
                    phoneAuthProvider.verifyPhoneNumber(
                            formatedNumber, // Phone number to verify
                            60, // Timeout duration
                            TimeUnit.SECONDS, // Unit of timeout
                            this, // Activity (for callback binding)
                            mCallbacks)     // OnVerificationStateChangedCallbacks
                } catch (e: NumberParseException) {
                    System.err.println("NumberParseException was thrown: " + e.toString())
                }
            }else{
                Toast.makeText(this,getString(R.string.error_message_phone), Toast.LENGTH_LONG).show()
            }

        }

    }
}
