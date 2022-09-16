package com.example.mysunflower.screen

import androidx.compose.runtime.Composable
import com.example.mysunflower.data.Plants

@Composable
fun PlantListScreen(list: List<Plants>, addToMyGarden : (String) -> Unit) {
    GridPlant(list = list) {
        addToMyGarden(it)
    }
}