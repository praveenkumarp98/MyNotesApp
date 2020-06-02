package com.praveen.mykotlinnotesappwithrealmdb

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * created by : praveen
 * created on : 31/05/2020
 * description : Initializing realm db
 */
class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        // initializing realm db
        Realm.init(this)
        val configuration = RealmConfiguration.Builder().name("myNotes.db")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .build()
        Realm.setDefaultConfiguration(configuration)
    }
}