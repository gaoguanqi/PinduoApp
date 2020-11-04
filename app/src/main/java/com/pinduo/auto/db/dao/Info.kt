package com.pinduo.auto.db.dao
import androidx.room.*
@Entity(tableName = "infos")
class Info {

    @PrimaryKey(autoGenerate = true)
    var id:Long = 0

    @ColumnInfo(name = "username")
    var username:String? = ""

    @ColumnInfo(name = "imei")
    var imei:String? = ""

    @ColumnInfo(name = "userconfig")
    var userconfig:String? = ""

}