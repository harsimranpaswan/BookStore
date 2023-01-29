package com.workshop.practice.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.workshop.practice.R

class ForgotActivity : AppCompatActivity() {

    lateinit var tvBack: TextView
    lateinit var etMail: EditText
    lateinit var btnOTP: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        setContentView(R.layout.activity_forgot)

        tvBack= findViewById(R.id.back)
        tvBack.setOnClickListener(){
            startActivity(Intent(this@ForgotActivity, LoginActivity::class.java))
        }
        etMail=findViewById(R.id.enter_mail)
        btnOTP=findViewById(R.id.btn_otp)



        btnOTP.setOnClickListener(){
            var mail=etMail.text.toString()
            if(mail.isEmpty()) {
                etMail.setError("Please enter your Mail")
            }
            else{
                var intent=Intent(this@ForgotActivity, GarbageActivity::class.java)
                var bundle=Bundle()
                bundle.putString("src", "forgot")
                bundle.putString("mail", mail)
                intent.putExtra("bundle", bundle)
                startActivity(intent)
            }
        }

    }
}