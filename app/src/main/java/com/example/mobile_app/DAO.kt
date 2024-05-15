package com.example.mobile_app

import androidx.room.*


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
