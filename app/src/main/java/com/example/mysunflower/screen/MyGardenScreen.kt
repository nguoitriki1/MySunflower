package com.example.mysunflower.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.mysunflower.data.Plants

@Composable
fun MyGardenScreen(modifier: Modifier = Modifier, list: List<Plants>, addPlants: () -> Unit) {
    Row(
        modifier = modifier.fillMaxSize().background(Color.Yellow),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (list.isEmpty()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Your garden is empty")
                OutlinedButton(onClick = {
                    addPlants()
                }) {
                    Text(text = "ADD PLANT")
                }
            }
        } else {
            GridPlant(modifier = Modifier.fillMaxSize(), list = list) {

            }
        }
    }
}
