package com.example.mysunflower.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mysunflower.data.Plants
import com.example.mysunflower.data.PlantsDao
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

const val DATABASE_NAME = "plants_database"

@Database(entities = [Plants::class], version = 1, exportSchema = false)
abstract class PlantsDatabase : RoomDatabase() {

    abstract fun plantDao(): PlantsDao

    companion object {


        // For Singleton instantiation
        @Volatile
        private var instance: PlantsDatabase? = null

        fun getInstance(context: Context): PlantsDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): PlantsDatabase {
            return Room.databaseBuilder(context, PlantsDatabase::class.java, DATABASE_NAME)
                .addCallback(object :
                    RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        Log.d("abc", "onCreate(db)")
                        CoroutineScope(Dispatchers.IO).launch {
                            val listPlants = loadPlantsFromJson(context.assets.open("plants.json"))
                            addPlantsToDB(context, listPlants);
                        }
                        super.onCreate(db)
                    }
                }).build()
        }
    }

}

private suspend fun addPlantsToDB(context: Context, listPlants: List<Plants>) {
    PlantsDatabase.getInstance(context).plantDao().insertAll(listPlants)
}

private suspend fun loadPlantsFromJson(open: InputStream?): List<Plants> = coroutineScope {
    val inputStream = open ?: return@coroutineScope emptyList()
    var streamReader: BufferedReader? = null
    try {
        streamReader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
        val responseStrBuilder = StringBuilder()

        var inputStr: String? = ""
        while (
            if (isActive) {
                streamReader.readLine().also {
                    inputStr = it
                } != null
            } else {
                false
            }
        )
            responseStrBuilder.append(inputStr)

        val listPlants = mutableListOf<Plants>()
        val jsonArray = JSONArray(responseStrBuilder.toString())
        for (i in 0 until jsonArray.length()) {
            val jsonObject = JSONObject(jsonArray[i].toString())
            val plants = Plants(
                plantId = jsonObject.getString("plantId"),
                name = jsonObject.getString("name"),
                descriptor = jsonObject.getString("description"),
                growZoneNumber = jsonObject.getInt("growZoneNumber"),
                imageUrl = jsonObject.getString("imageUrl"),
                wateringInterval = jsonObject.getInt("wateringInterval")
            )
            listPlants.add(plants)
        }
        return@coroutineScope listPlants
    } catch (e: Exception) {
        Log.d("abc", "error ${e.message}")
    } finally {
        streamReader?.close()
        inputStream.close()
    }
    return@coroutineScope emptyList()
}
