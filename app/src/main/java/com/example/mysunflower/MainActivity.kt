package com.example.mysunflower

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mysunflower.data.MY_GARDEN
import com.example.mysunflower.data.PLANT_LIST
import com.example.mysunflower.screen.MyGardenScreen
import com.example.mysunflower.screen.PlantListScreen
import com.example.mysunflower.ui.theme.MySunflowerTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MySunflowerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    val state = rememberPagerState()

    val scope = rememberCoroutineScope()

    Column(modifier = modifier.fillMaxSize()) {
        TitleAppbar(title = "My Garden Store")
        TabPage()
        PageScreen(state = state) {
            scope.launch {
                state.animateScrollToPage(state.pageCount - 1, 0f)
            }
        }
    }
}

@Composable
fun TitleAppbar(modifier: Modifier = Modifier, title: String) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(56.dp), contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier,
            text = title
        )
    }
}

@Composable
fun TabPage() {

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PageScreen(
    viewModel: MyGardenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    state: PagerState,
    addPlants: () -> Unit
) {

    val context = LocalContext.current

    val list = viewModel.getGardenStore(context).observeAsState()

    val listPage = mutableListOf(
        MY_GARDEN,
        PLANT_LIST
    )

    HorizontalPager(
        count = listPage.size,
        state = state,
        content = { index ->
            when (listPage[index]) {
                MY_GARDEN -> {
                    val listMyGarden =
                        list.value?.filter { it.isMyGarden } ?: return@HorizontalPager
                    MyGardenScreen(
                        list = listMyGarden,
                        addPlants = {
                            addPlants()
                        })
                }
                PLANT_LIST -> {
                    PlantListScreen(list = list.value ?: emptyList()) {
                        viewModel.addToMyGarden(context, it)
                    }
                }
            }
        })
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MySunflowerTheme {
        MainScreen()
    }
}