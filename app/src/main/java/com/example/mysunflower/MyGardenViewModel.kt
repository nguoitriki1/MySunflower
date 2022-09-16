package com.example.mysunflower

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysunflower.data.Plants
import com.example.mysunflower.data.PlantsPage
import com.example.mysunflower.database.PlantsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyGardenViewModel : ViewModel() {

    fun getGardenStore(context: Context): LiveData<List<Plants>> {
        return PlantsDatabase.getInstance(context).plantDao().getPlants()
    }

    fun addToMyGarden(context: Context, plantId: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                PlantsDatabase.getInstance(context).plantDao().addToMyGarden(plantId, true)
            }
        }
    }

    fun removeFromMyGarden(context: Context, plantId: String) {
        PlantsDatabase.getInstance(context).plantDao().addToMyGarden(plantId, false)
    }

    fun isMyGarden(context: Context, plantId: String) : Boolean{
       return PlantsDatabase.getInstance(context).plantDao().isMyGarden(plantId)
    }

}