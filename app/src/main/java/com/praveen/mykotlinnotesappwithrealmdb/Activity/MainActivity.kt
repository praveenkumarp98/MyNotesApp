package com.praveen.mykotlinnotesappwithrealmdb.Activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.praveen.mykotlinnotesappwithrealmdb.Adapter.NotesAdapter
import com.praveen.mykotlinnotesappwithrealmdb.Model.Notes
import com.praveen.mykotlinnotesappwithrealmdb.R
import io.realm.Realm
import io.realm.RealmResults

/**
 * created by : praveen
 * created on : 31/05/2020
 * description : MainActivity with recyclerview and fab
 */
class MainActivity : AppCompatActivity() {

    private lateinit var addNotes : FloatingActionButton
    private lateinit var recyclerView : RecyclerView
    private lateinit var notesList: ArrayList<Notes>
    private lateinit var realm: Realm
    private lateinit var result: RealmResults<Notes>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        realm = Realm.getDefaultInstance()
        addNotes = findViewById(R.id.fab_addNote)
        recyclerView = findViewById(R.id.recyclerview_notes)

        addNotes.setOnClickListener {
            startActivity(Intent(this, AddNotesActivity::class.java))
            finish()
        }
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        getAllNotes()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.clear_all) {
            realm.beginTransaction()
            result.deleteAllFromRealm()
            realm.commitTransaction()
            recyclerView.adapter!!.notifyDataSetChanged()
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun getAllNotes() {
        notesList = ArrayList()
        result = realm.where(Notes::class.java).findAll()
        recyclerView.adapter =
            NotesAdapter(
                this,
                result
            )
        recyclerView.adapter!!.notifyDataSetChanged()
    }
}
