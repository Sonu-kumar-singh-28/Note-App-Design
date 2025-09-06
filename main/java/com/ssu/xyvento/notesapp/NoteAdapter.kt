package com.ssu.xyvento.notesapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.ssu.xyvento.notesapp.databinding.ItemNoteBinding
import kotlin.random.Random

class NoteAdapter(options: FirestoreRecyclerOptions<Note>) :
    FirestoreRecyclerAdapter<Note, NoteAdapter.NoteViewHolder>(options) {

    inner class NoteViewHolder(val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root)

    // ✅ Available colors
    private val colors = listOf(
        R.color.orange,
        R.color.deep_orange,
        R.color.peach,
        R.color.lavender,
        R.color.purple,
        R.color.indigo,
        R.color.blue_gray,
        R.color.teal,
        R.color.aqua,
        R.color.mint,
        R.color.lime,
        R.color.yellow,
        R.color.rose,
        R.color.magenta,
        R.color.brown,
        R.color.cream,
        R.color.red
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int, model: Note) {
        holder.binding.titleText.text = model.title
        holder.binding.contentText.text = model.content

        // ✅ Random color background
        val randomColorRes = colors[Random.nextInt(colors.size)]
        val color = ContextCompat.getColor(holder.itemView.context, randomColorRes)
        holder.binding.root.setBackgroundColor(color)

        // ✅ Click listener (open detail activity)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, Notes_layoutActivity::class.java)

            // Firestore se document id bhejna
            intent.putExtra("noteId", snapshots.getSnapshot(position).id)

            context.startActivity(intent)
        }
    }
}
