package com.workshop.practice.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.workshop.practice.R

class RegisterActivity : AppCompatActivity() {
    lateinit var etname: EditText
    lateinit var etmail: EditText
    lateinit var etaddress: EditText
    lateinit var etpassword: EditText
    lateinit var etcnfPassword: EditText
    lateinit var tvBack: TextView
    lateinit var btnSignUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        setContentView(R.layout.activity_register)

        tvBack = findViewById(R.id.back)
        tvBack.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }

        etmail = findViewById(R.id.enter_mail)
        etname = findViewById(R.id.enter_name)
        etaddress = findViewById(R.id.enter_address)
        etpassword = findViewById(R.id.password)
        etcnfPassword = findViewById(R.id.cnfPassword)
        btnSignUp = findViewById(R.id.btnsignup)

        btnSignUp.setOnClickListener {
            val password = etpassword.text.toString()
            val cnfPassword = etcnfPassword.text.toString()
            val mail = etmail.text.toString()
            val address = etaddress.text.toString()
            val name = etname.text.toString()

            if (name.isEmpty())
                etname.error = "Please enter your Name"
            else if (mail.isEmpty())
                etmail.error = "Please enter your Mail"
            else if (address.isEmpty())
                etaddress.error = "Please enter your Address"
            else if (password.length < 8)
                etpassword.error = "Password must be of atleast 8 characters"
            else if (password != cnfPassword)
                etcnfPassword.error = "Passwords do not match"
            else {
                var intent = Intent(this@RegisterActivity, MainActivity::class.java)
                var bundle = Bundle()
                bundle.putString("src", "register")
                bundle.putString("name", name)
                bundle.putString("mail", mail)
                bundle.putString("address", address)
                bundle.putString("password", password)
                intent.putExtra("bundle", bundle)
                startActivity(intent)
            }
        }

    }
}