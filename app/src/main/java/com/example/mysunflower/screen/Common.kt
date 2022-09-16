package com.example.mysunflower.screen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.mysunflower.data.Plants

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridPlant(
    modifier: Modifier = Modifier,
    list: List<Plants>,
    onClickAction: (String) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        cells = GridCells.Fixed(2)
    ) {
        items(list.size) { index ->
            Log.d("abc", list[index].imageUrl)
            ItemPlant(thumbUrl = list[index].imageUrl, name = list[index].name) {
                if (!list[index].isMyGarden)
                    onClickAction(list[index].plantId)
            }
        }
    }
}


@Composable
fun ItemPlant(
    modifier: Modifier = Modifier,
    thumbUrl: String,
    name: String,
    onClickAction: () -> Unit
) {

    Surface(
        modifier = modifier
            .padding(8.dp)
            .clickable { onClickAction() },
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(thumbUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(topEnd = 8.dp, bottomStart = 8.dp)),
                contentScale = ContentScale.Crop,
            )
            Text(
                modifier = Modifier
                    .heightIn(56.dp)
                    .wrapContentHeight(),
                text = name,
                style = MaterialTheme.typography.subtitle1
            )
        }
    }

}

@Preview(widthDp = 360, heightDp = 720)
@Composable
fun ItemPlantPreview() {
    ItemPlant(
        thumbUrl = "https://upload.wikimedia.org/wikipedia/commons/5/55/Apple_orchard_in_Tasmania.jpg",
        name = "abc"
    ) {

    }
}