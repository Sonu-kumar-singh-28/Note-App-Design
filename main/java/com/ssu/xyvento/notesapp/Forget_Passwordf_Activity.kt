package com.ssu.xyvento.notesapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.ssu.xyvento.notesapp.databinding.ActivityForgetPasswordfBinding
import kotlin.math.PI

class Forget_Passwordf_Activity : AppCompatActivity() {

    private lateinit var  auth: FirebaseAuth
    private val binding: ActivityForgetPasswordfBinding by lazy {
        ActivityForgetPasswordfBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.redirecttoforgetpage.setOnClickListener {
            startActivity(Intent(this, Login_Page_Activity::class.java))
            finish()
        }

        binding.forgetbutttonforgetpage.setOnClickListener {
            var emailid = binding.forgetpasswordtextfield.text.toString().trim()

            if(emailid.isEmpty()){
                Toast.makeText(this, "Enter the email First", Toast.LENGTH_SHORT).show()
            }else{
                auth.sendPasswordResetEmail(emailid)
                    .addOnCompleteListener {task ->
                        if(task.isSuccessful){
                            Toast.makeText(this, "Reset Password  Sent Your Email", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, Login_Page_Activity::class.java))
                            finish()
                        }else{
                            Toast.makeText(this, "Error ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}