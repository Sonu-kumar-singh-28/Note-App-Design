package com.ssu.xyvento.notesapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.ssu.xyvento.notesapp.databinding.ActivityRegisterUserBinding

class Register_User_Activity : AppCompatActivity() {
    private val binding: ActivityRegisterUserBinding by lazy {
        ActivityRegisterUserBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.wanttologin.setOnClickListener {
            startActivity(Intent(this, Login_Page_Activity::class.java))
            finish()
        }

        binding.registerbutttonregisterpage.setOnClickListener {
            val username  = binding.registerusername.text.toString();
            val useremail = binding.registeremailtextfield.text.toString()
            val password = binding.registerpasswodinputfield.text.toString()
            val repeatpassword = binding.registerrepeatpasswodinputfield.text.toString()

            if(username.isEmpty() || password.isEmpty() || useremail.isEmpty() || repeatpassword.isEmpty()){
                Toast.makeText(this, "Please fill all inputField", Toast.LENGTH_SHORT).show()
            }else if(password.length <5 && repeatpassword.length <5){
                Toast.makeText(this, "Please enter Minimum five character", Toast.LENGTH_SHORT).show()
            }else if(password!=repeatpassword){
                Toast.makeText(this, "password are not same", Toast.LENGTH_SHORT).show()
            }else{
                // registered user
                auth.createUserWithEmailAndPassword(useremail, password)
                    .addOnCompleteListener {task ->
                        if(task.isSuccessful){
                            Toast.makeText(this, "Registration SuccessFull", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, Login_Page_Activity::class.java))
                            finish()
                        }else{
                            Toast.makeText(this, "Registration Failed${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}

