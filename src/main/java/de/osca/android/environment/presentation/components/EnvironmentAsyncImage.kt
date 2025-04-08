package de.osca.android.environment.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.decode.SvgDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest

@Composable
fun EnvironmentAsyncImage(url: String,
         modifier: Modifier = Modifier
             .width(30.dp)
             .height(30.dp),
                          color: Color = Color.White,
                          description: String = "") {

    return SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current).memoryCachePolicy(CachePolicy.ENABLED).diskCachePolicy(CachePolicy.ENABLED).networkCachePolicy(CachePolicy.ENABLED).data(url).decoderFactory(
            SvgDecoder.Factory()).build(),
        loading = {
            CircularProgressIndicator()
        },
        contentDescription = description,
        modifier = modifier,
        colorFilter = ColorFilter.tint(color)
    )
}
