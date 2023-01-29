package com.workshop.practice.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.workshop.practice.R

class LoginActivity : AppCompatActivity() {
    lateinit var tvForgot: TextView
    lateinit var tvSignup: TextView

    lateinit var login_mail: EditText
    lateinit var etPassword: EditText

    lateinit var btnlogin: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        setContentView(R.layout.activity_login)


        login_mail=findViewById(R.id.login_mail)
        etPassword=findViewById(R.id.password)

        btnlogin= findViewById(R.id.btnlogin)
        btnlogin.setOnClickListener(){
            var mail=login_mail.text.toString()
            var pass=etPassword.text.toString()
            if(mail.isEmpty())
                login_mail.setError("Please enter your Name")
            else if(pass.length<8)
                etPassword.setError("Please enter a valid password")
            else {
                var intent = Intent(this@LoginActivity, GarbageActivity::class.java)
                var bundle = Bundle()
                bundle.putString("src", "login")
                bundle.putString("mail", mail)
                bundle.putString("pass", pass)
                intent.putExtra("bundle", bundle)
                startActivity(intent)
            }
        }

        tvForgot= findViewById(R.id.forgot)
        tvForgot.setOnClickListener(){
            startActivity(Intent(this@LoginActivity, ForgotActivity::class.java))
        }
        tvSignup= findViewById(R.id.signup)
        tvSignup.setOnClickListener(){
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }


    }
}