package com.example.artspaceapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspaceapp.ui.theme.ArtSpaceAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

data class Artwork(
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val artist: Int,
    val year: String
)

private const val COUNT = 5

fun artworkFor(index: Int): Artwork = when (index) {
    1 -> Artwork(R.drawable.art_1, R.string.art_1_title, R.string.art_1_artist, "2019")
    2 -> Artwork(R.drawable.art_2, R.string.art_2_title, R.string.art_2_artist, "2021")
    3 -> Artwork(R.drawable.art_3, R.string.art_3_title, R.string.art_3_artist, "2018")
    4 -> Artwork(R.drawable.art_4, R.string.art_4_title, R.string.art_4_artist, "2022")
    else -> Artwork(R.drawable.art_5, R.string.art_5_title, R.string.art_5_artist, "2020")
}

@Composable
fun ArtSpaceApp(modifier: Modifier = Modifier) {
    var index by rememberSaveable { mutableIntStateOf(1) }
    val art = artworkFor(index)

    val onPrev = { index = if (index == 1) COUNT else index - 1 }
    val onNext = { index = if (index == COUNT) 1 else index + 1 }
    when (LocalConfiguration.index.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> LandscapeLayout(art, onPrev, onNext, modifier)
        else -> PortraitLayout(art, onPrev, onNext, modifier)
    }
}

@Composable
private fun PortraitLayout(
    art: Artwork,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ArtworkWall(
            art = art,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        ArtworkDescriptor(art = art)
        Spacer(modifier = Modifier.height(24.dp))
        DisplayController(onPrev = onPrev, onNext = onNext)
    }
}

@Composable
private fun LandscapeLayout(
    art: Artwork,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ArtworkWall(
            art = art,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        )
        Spacer(modifier = Modifier.width(32.dp))
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ArtworkDescriptor(art = art)
            Spacer(modifier = Modifier.height(32.dp))
            DisplayController(onPrev = onPrev, onNext = onNext)
        }
    }
}

@Composable
fun ArtworkWall(art: Artwork, modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Surface(
            modifier = Modifier
                .padding(8.dp)
                .shadow(elevation = 12.dp, shape = RoundedCornerShape(2.dp)),
            color = MaterialTheme.colorScheme.surface
        ) {
            Image(
                painter = painterResource(art.image),
                contentDescription = stringResource(
                    R.string.artwork_description,
                    stringResource(art.title)
                ),
                contentScale = ContentScale.Fit,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun ArtworkDescriptor(art: Artwork, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(art.title),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(
                        R.string.artwork_info,
                        stringResource(art.artist),
                        art.year
                    ),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun DisplayController(
    onPrev: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(onClick = onPrev, modifier = Modifier.weight(1f)) {
            Text(stringResource(R.string.previous))
        }
        Button(onClick = onNext, modifier = Modifier.weight(1f)) {
            Text(stringResource(R.string.next))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview1() {
    ArtSpaceAppTheme { ArtSpaceApp() }
}

@Preview(showBackground = true, widthDp = 800, heightDp = 360)
@Composable
fun PreviewLandscape() {
    ArtSpaceAppTheme {
        LandscapeLayout(artworkFor(1), {}, {})
    }
}
