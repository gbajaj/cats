package com.datalayer.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.datalayer.demo.datalayer.cats.api.Cat
import com.datalayer.demo.ui.theme.DemoDataLayerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel: CatsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemoDataLayerTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    val catsState = viewModel.catsState.collectAsState().value
                    when (catsState) {
                        is CatsState.Error -> {
                            Text(text = catsState.message)
                        }
                        is CatsState.Loading -> {
                            Text(text = "Loading")
                        }
                        is CatsState.Success -> {
                            CatSuccessDownload(cats = catsState.cats, modifier = Modifier.padding(innerPadding))
                        }
                    }
                }
            }
        }
        viewModel.getCats()
    }
}
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CatSuccessDownload(cats: List<Cat>, modifier: Modifier = Modifier) {
    Box (modifier = Modifier
        .fillMaxSize().background(color = Color.Gray)) {
        LazyColumn(modifier = modifier) {
            items(cats) { cat ->
                GlideImage(model = cat.downloadUrl, contentDescription = "Cat image", loading = placeholder(
                    ColorPainter(Color.LightGray)
                ), modifier = Modifier.size(100.dp).padding(1.dp), contentScale = ContentScale.Crop)
            }
        }
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DemoDataLayerTheme {
        Greeting("Android")
    }
}