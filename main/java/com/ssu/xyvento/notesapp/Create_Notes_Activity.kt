package com.ssu.xyvento.notesapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ssu.xyvento.notesapp.databinding.ActivityCreateNotesBinding

class Create_Notes_Activity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var binding: ActivityCreateNotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // ✅ ViewBinding setup
        binding = ActivityCreateNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Firebase instances
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // ✅ Current user
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // ✅ Toolbar setup
        setSupportActionBar(binding.toolbars)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbars.setNavigationOnClickListener {
            startActivity(Intent(this, notesactivity_Activity::class.java))
            finish()
        }

        // ✅ Save button click
        binding.savenotebutton.setOnClickListener {
            val title = binding.toolbarcreatetitlenote.text.toString().trim()
            val content = binding.toolbarofcreatenote.text.toString().trim()

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "Please enter title and content", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ✅ Note with UID for Firestore rules
            val note = hashMapOf(
                "title" to title,
                "content" to content,
                "uid" to currentUser.uid
            )

            firestore.collection("notes")
                .add(note)
                .addOnSuccessListener {
                    Toast.makeText(this, "Note Saved Successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, notesactivity_Activity::class.java))
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
