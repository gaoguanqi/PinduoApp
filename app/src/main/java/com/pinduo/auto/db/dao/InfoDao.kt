package com.pinduo.auto.db.dao


import androidx.room.*

@Dao
interface InfoDao {
    @Query("SELECT * FROM infos")
    fun getAllInfo():List<Info>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInfo(vararg user:Info)

    @Query("SELECT * FROM infos WHERE id = :id")
    fun getInfoById(id:Long):Info

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateInfo(info: Info)

    @Delete
    fun deleteInfo(info:Info)

    @Query("DELETE FROM infos")
    fun deleteAll()


    fun getCurrentInfo():Info?{
        val infos = getAllInfo()
        if(!infos.isEmpty()){
            return infos.last()
        }
        return null
    }
}