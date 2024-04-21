package com.android4you.overlappingviewscompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import com.android4you.overlappingviewscompose.ui.theme.OverlappingViewsComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OverlappingViewsComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    CoverAndProfileImage(
        coverImage = "https://9to5google.com/wp-content/uploads/sites/4/2021/02/android-jetpack-header.png",
        profileImage = "https://3.bp.blogspot.com/-VVp3WvJvl84/X0Vu6EjYqDI/AAAAAAAAPjU/ZOMKiUlgfg8ok8DY8Hc-ocOvGdB0z86AgCLcBGAsYHQ/s1600/jetpack%2Bcompose%2Bicon_RGB.png",
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OverlappingViewsComposeTheme {
        CoverAndProfileImage(
            coverImage = "https://9to5google.com/wp-content/uploads/sites/4/2021/02/android-jetpack-header.png",
            profileImage = "https://3.bp.blogspot.com/-VVp3WvJvl84/X0Vu6EjYqDI/AAAAAAAAPjU/ZOMKiUlgfg8ok8DY8Hc-ocOvGdB0z86AgCLcBGAsYHQ/s1600/jetpack%2Bcompose%2Bicon_RGB.png",
        )
    }
}

@Composable
fun OverlappingBoxes(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = content,
    ) { measurables, constraints ->
        val largeBox = measurables[0]
        val smallBox = measurables[1]
        val looseConstraints = constraints.copy(
            minWidth = 0,
            minHeight = 0,
        )
        val largePlaceable = largeBox.measure(looseConstraints)
        val smallPlaceable = smallBox.measure(looseConstraints)
        layout(
            width = constraints.maxWidth,
            height = largePlaceable.height + smallPlaceable.height / 2,
        ) {
            largePlaceable.placeRelative(
                x = 0,
                y = 0,
            )
            smallPlaceable.placeRelative(
                x = (constraints.maxWidth - smallPlaceable.width) / 2,
                y = largePlaceable.height - smallPlaceable.height / 2,
            )
        }
    }
}

@Composable
fun CoverAndProfileImage(
    modifier: Modifier = Modifier,
    coverImage: Any?,
    profileImage: Any?,
    onCoverClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
) {
    OverlappingBoxes(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clickable { onCoverClick() },
        ) {
            ImageItem(
                modifier = Modifier.fillMaxSize(),
                data = coverImage ?: R.drawable.coverage,
                contentScale = ContentScale.FillWidth,
            )
        }

        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable { onProfileClick() },
        ) {
            ImageItem(
                modifier = Modifier.fillMaxSize(),
                data = profileImage ?: R.drawable.avatar,
                contentScale = ContentScale.Crop,
            )
        }
    }
}

@Composable
fun ImageItem(
    modifier: Modifier,
    data: Any?,
    crossfadeValue: Int = 300,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
) {
    coil.compose.AsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalContext.current)
            .data(data)
            .crossfade(crossfadeValue)
            .build(),
        contentDescription = contentDescription,
        placeholder = painterResource(id = R.drawable.avatar),
        contentScale = contentScale,
    )
}
