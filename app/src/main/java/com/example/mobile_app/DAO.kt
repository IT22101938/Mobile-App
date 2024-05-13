package com.example.mobile_app

import androidx.room.*
//import androidx.room.Delete
//import androidx.room.Insert
//import androidx.room.Query
//import androidx.room.Update
import com.example.mobile_app.Entity
import com.example.mobile_app.CardInfo

@Dao
interface DAO {
    @Insert
    suspend fun insertTask(entity: Entity)

    @Update
    suspend fun updateTask(entity: Entity)

    @Delete
    suspend fun deleteTask(entity: Entity)

    @Query("DELETE FROM To_Do")
    suspend fun deleteAll(): Int

    @Query("SELECT * from To_Do")
    suspend fun getTasks():List<CardInfo>

}
