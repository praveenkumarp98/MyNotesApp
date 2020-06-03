package com.praveen.mykotlinnotesappwithrealmdb.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.praveen.mykotlinnotesappwithrealmdb.Model.Notes
import com.praveen.mykotlinnotesappwithrealmdb.R
import io.realm.Realm

/**
 * created by : praveen
 * created on : 31/05/2020
 * description : class adding new notes
 */
class AddNotesActivity : AppCompatActivity() {

    private lateinit var title:EditText
    private lateinit var description: EditText
    private lateinit var btnSave: Button
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

        realm = Realm.getDefaultInstance()
        title = findViewById(R.id.et_title)
        description = findViewById(R.id.et_description)
        btnSave = findViewById(R.id.btn_save_note)

        btnSave.setOnClickListener {

            addNotesToDB()
        }
    }

    private fun addNotesToDB() {
        try {
            // Auto Increment Id
            realm.beginTransaction()
            val currentIdNumber : Number? = realm.where(Notes::class.java).max("id")
            val nextId : Int

            nextId = if ( currentIdNumber == null){
                1
            }
            else{
                currentIdNumber.toInt() + 1
            }

            val notes = Notes()
            notes.title = title.text.toString()
            notes.description = description.text.toString()
            notes.id = nextId

            if (notes.title.isNullOrEmpty() || notes.description.isNullOrEmpty()) {
                Toast.makeText(this,"Fields can't be empty",Toast.LENGTH_SHORT).show()
            }
            else {
                // copy this transaction and commit
                realm.copyToRealmOrUpdate(notes)
                realm.commitTransaction()
                Toast.makeText(this,"Notes Add Sucessfully", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
        catch (e : Exception){
            Toast.makeText(this,"Failed : $e ",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,
            MainActivity::class.java))
        finish()
    }
}