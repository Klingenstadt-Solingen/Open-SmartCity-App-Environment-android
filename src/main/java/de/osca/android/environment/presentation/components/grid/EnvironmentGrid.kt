package de.osca.android.environment.presentation.components.grid

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.unit.dp
import de.osca.android.environment.R
import de.osca.android.environment.presentation.args.LocalEnvironmentDesignArgs
import de.osca.android.essentials.presentation.component.design.LocalMasterDesignArgs

/**
 * Draw a grid and fill with items
 *
 * @param environmentViewModel current view model for design arguments
 * @param itemList list of items to display
 * @param content card content function
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> EnvironmentGrid(
    modifier: Modifier,
    itemList: List<T>,
    onClick: (T) -> Unit,
    backgroundColor: Color?,
    content: @Composable (T) -> Unit,
    fetchMore: (Int) -> Unit = {}
) {
    val masterDesignArgs = LocalMasterDesignArgs.current
    val design = LocalEnvironmentDesignArgs.current
    val gridState = rememberLazyGridState()
    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Fixed(integerResource(id = R.integer.environment_grid_columns)),
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(top = 10.dp)
    ) {
        itemsIndexed(itemList) { index, item ->
            if (index == itemList.lastIndex) {
                LaunchedEffect(index){ fetchMore(index+1)
                }
            }
            Card(
                backgroundColor = backgroundColor ?: masterDesignArgs.mCardBackColor,
                onClick = {
                    onClick(item)
                },
                modifier = Modifier
                    .aspectRatio(1f),
                shape = RoundedCornerShape(design.mShapeCard ?: masterDesignArgs.mShapeCard),
                elevation = design.mCardElevation ?: masterDesignArgs.mCardElevation,
            ) {
                content(item)
            }
        }
    }
}
