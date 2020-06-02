package com.praveen.mykotlinnotesappwithrealmdb

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
/**
 * created by : praveen
 * created on : 31/05/2020
 * description : realm object
 */
open class Notes(
    @PrimaryKey
    var id: Int? = null,
    var title:String? = null,
    var description:String? = null
) : RealmObject()