package com.ssu.xyvento.notesapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ssu.xyvento.notesapp.databinding.ActivityNotesactivityBinding

class notesactivity_Activity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesactivityBinding
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Setup Toolbar
        setSupportActionBar(binding.toolbar)

        // ✅ FloatingActionButton -> open Create_Notes_Activity
        binding.floatingactionbutton.setOnClickListener {
            startActivity(Intent(this, Create_Notes_Activity::class.java))
        }

        // ✅ RecyclerView setup
        binding.recycleview.layoutManager = LinearLayoutManager(this)

        // ✅ Firestore Query for current user
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val query = FirebaseFirestore.getInstance()
                .collection("notes")
                .whereEqualTo("uid", currentUser.uid)
                .orderBy("title")

            val options = FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query, Note::class.java)
                .build()

            adapter = NoteAdapter(options)
            binding.recycleview.adapter = adapter
        }
    }

    override fun onStart() {
        super.onStart()
        if (::adapter.isInitialized) adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        if (::adapter.isInitialized) adapter.stopListening()
    }

    // ✅ Toolbar menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menuitem, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // 🔹 Logout button
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, Login_Page_Activity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
