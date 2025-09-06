package com.ssu.xyvento.notesapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ssu.xyvento.notesapp.databinding.ActivityNotesLayoutBinding

class Notes_layoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesLayoutBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private var noteId: String? = null
    private var isSaving = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        noteId = intent.getStringExtra("noteId")
        noteId?.let { loadNoteData(it) }

        binding.saveNoteButton.setOnClickListener {
            saveNote()
        }

        binding.BackNoteButton.setOnClickListener {
            finish()
        }


        val backbutton = findViewById<Button>(R.id.BackNoteButton)

        backbutton.setOnClickListener {
            startActivity(Intent(this, notesactivity_Activity::class.java))
            finish()
        }


    }

    private fun loadNoteData(noteId: String) {
        firestore.collection("notes").document(noteId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    binding.notestitle.text = document.getString("title")
                    binding.notescontent.setText(document.getString("content"))
                } else {
                    Toast.makeText(this, "Note not found", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error loading note", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveNote() {
        if (isSaving) return
        isSaving = true

        val updatedContent = binding.notescontent.text.toString()

        if (noteId != null) {
            firestore.collection("notes").document(noteId!!)
                .update("content", updatedContent)
                .addOnSuccessListener {
                    Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
                    isSaving = false
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error saving note", Toast.LENGTH_SHORT).show()
                    isSaving = false
                }
        } else {
            Toast.makeText(this, "Invalid note ID", Toast.LENGTH_SHORT).show()
            isSaving = false
        }
    }
}
