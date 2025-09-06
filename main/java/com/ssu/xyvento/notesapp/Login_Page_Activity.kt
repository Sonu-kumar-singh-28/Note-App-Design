package com.ssu.xyvento.notesapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.ssu.xyvento.notesapp.databinding.ActivityLoginPageBinding

class Login_Page_Activity : AppCompatActivity() {
    private val binding: ActivityLoginPageBinding by lazy {
        ActivityLoginPageBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()


        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, notesactivity_Activity::class.java))
            finish()
        }

        binding.registerbutttonloginpage.setOnClickListener {
            startActivity(Intent(this, Register_User_Activity::class.java))
            finish()
        }

        binding.forgetpassword.setOnClickListener {
            startActivity(Intent(this, Forget_Passwordf_Activity::class.java))
            finish()
        }

        val progressbar = findViewById<ProgressBar>(R.id.progressbarofmainactivity)


        binding.loginButton.setOnClickListener {
            val email = binding.emailtextfield.text.toString().trim()
            val password = binding.passwodinputfield.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Show progress
            progressbar.visibility = View.VISIBLE

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    progressbar.visibility = View.GONE

                    if (task.isSuccessful) {
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, notesactivity_Activity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
