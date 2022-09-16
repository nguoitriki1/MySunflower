package com.example.mysunflower.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "plants",
)
data class Plants(
    @PrimaryKey
    @ColumnInfo(name = "plantId")
    val plantId: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "descriptor")
    val descriptor: String,
    @ColumnInfo(name = "growZoneNumber")
    val growZoneNumber: Int,
    @ColumnInfo(name = "wateringInterval")
    val wateringInterval : Int,
    @ColumnInfo(name = "imageUrl")
    val imageUrl : String,
    @ColumnInfo(name = "isMyGarden")
    val isMyGarden : Boolean = false,
) {

}