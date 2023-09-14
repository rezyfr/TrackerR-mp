package dev.rezyfr.trackerr.presentation.component.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter

typealias IconIdAndIconUrl = Pair<Int, String>

@Composable
fun IconPicker(
    modifier: Modifier = Modifier,
    selectedWalletId: Int?,
    idAndIcon: List<IconIdAndIconUrl>,
    onIconChoose: (Int) -> Unit = {},
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = modifier.fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(
                    topStart = 32.dp, topEnd = 32.dp
                )
            )
            .padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(idAndIcon) { data ->
            val isChosen = selectedWalletId == data.first
            IconButton(
                onClick = {
                    onIconChoose.invoke(data.first)
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .run {
                        if (isChosen) {
                            background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(8.dp)
                            )
                            border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(8.dp)
                            )
                        } else {
                            background(
                                color = MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(8.dp)
                            )
                            border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(8.dp)
                            )
                        }
                    }
                    .padding(4.dp)
            ) {
                Image(
                    painter = rememberImagePainter(data.second),
                    contentDescription = null,
                    modifier = Modifier
                        .height(48.dp)
                        .wrapContentWidth(),
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}