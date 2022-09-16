package com.example.mysunflower.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlantsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<Plants>)

    @Query("SELECT * FROM plants")
    fun getAll() : List<Plants>

    @Query("SELECT * FROM plants")
    fun getPlants(): LiveData<List<Plants>>

    @Query("UPDATE plants SET isMyGarden = :isMyGarden WHERE plantId = :plantId")
    fun addToMyGarden(plantId: String, isMyGarden: Boolean)

    @Query("SELECT isMyGarden FROM plants WHERE plantId = :plantId")
    fun isMyGarden(plantId: String) : Boolean
}