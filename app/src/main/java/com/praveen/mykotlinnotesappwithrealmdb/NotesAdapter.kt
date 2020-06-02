package com.praveen.mykotlinnotesappwithrealmdb

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.item_layout.view.*

/**
 * created by : praveen
 * created on : 31/05/2020
 * description : adapter for recyclerview
 */
class NotesAdapter (private val context: Context, private val notesList : RealmResults<Notes>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private var realm = Realm.getDefaultInstance()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.titleTextView.text = notesList[position]!!.title
        holder.itemView.descriptionTextView.text = notesList[position]!!.description
        holder.itemView.idTextView.text = notesList[position]!!.id.toString()
        val noteId = holder.itemView.idTextView.text.toString()
        val noteIdNew = noteId.toInt()

        holder.itemView.setOnLongClickListener {
            MaterialAlertDialogBuilder(context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure?")
                .setMessage("Do you want to delete this note?")
                .setPositiveButton("Yes") { _, _ ->
                    realm.beginTransaction()
                    notesList[position]?.deleteFromRealm()
                    realm.commitTransaction()
                   Toast.makeText(context, "field deleted", Toast.LENGTH_SHORT).show()
                    notifyDataSetChanged()
                }
                .setNegativeButton("No", null)
                .show()
            return@setOnLongClickListener true
        }
        holder.itemView.setOnClickListener {v->
            val intent = Intent(context,UpdateNoteActivity::class.java)
            intent.putExtra("noteId",noteIdNew)
            intent.putExtra("noteTitle",v.titleTextView.text)
            intent.putExtra("noteDescription",v.descriptionTextView.text)
            v.context.startActivity(intent)
        }
    }


    class ViewHolder(v: View?): RecyclerView.ViewHolder(v!!){
        val title: TextView = itemView.findViewById(R.id.titleTextView)
        //val description: TextView = itemView.findViewById(R.id.descriptionTextView)
        val id: TextView = itemView.findViewById(R.id.idTextView)
    }
}