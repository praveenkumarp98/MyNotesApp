package com.praveen.mykotlinnotesappwithrealmdb.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.praveen.mykotlinnotesappwithrealmdb.Model.Notes
import com.praveen.mykotlinnotesappwithrealmdb.R
import io.realm.Realm
/**
 * created by : praveen
 * created on : 02/06/2020
 * description : class updating note notes
 */
class UpdateNoteActivity : AppCompatActivity() {
    private lateinit var title: EditText
    private lateinit var description: EditText
    private lateinit var btnUpdate: Button
    private lateinit var btnCancel: Button
    private lateinit var realm: Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_note)

        realm = Realm.getDefaultInstance()
        title = findViewById(R.id.et_title)
        description = findViewById(R.id.et_description)
        btnUpdate = findViewById(R.id.btn_update_note)
        btnCancel = findViewById(R.id.btn_cancel)

        val noteId = intent.getIntExtra("noteId",0)
        val oldTitle = intent.getStringExtra("noteTitle")
        val oldDescription = intent.getStringExtra("noteDescription")

        setContent(oldTitle,oldDescription)
        btnUpdate.setOnClickListener {
            updateNotesToDB(noteId)
        }
        btnCancel.setOnClickListener {
            startActivity(Intent(this,
                MainActivity::class.java))
            finish()
        }
    }

    /**
     * method setting already existing data in fields
     */
    private fun setContent(oldTitle: String?, oldDescription: String?) {
        title.setText(oldTitle)
        description.setText(oldDescription)
    }

    /**
     * method updating realm data
     */
    private fun updateNotesToDB(noteid: Int) {
        try {
            realm.beginTransaction()
            val notes = Notes()
            notes.title = title.text.toString()
            notes.description = description.text.toString()
            notes.id = noteid
            if (notes.title.isNullOrEmpty() || notes.description.isNullOrEmpty()) {
                Toast.makeText(this,"Fields can't be empty", Toast.LENGTH_SHORT).show()
            }
            else {
                // copy this transaction and commit
                realm.copyToRealmOrUpdate(notes)
                realm.commitTransaction()
                Toast.makeText(this,"Notes Updated Successfully", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
        catch (e : Exception){
            Toast.makeText(this,"Failed : $e ",Toast.LENGTH_SHORT).show()
        }
    }
}