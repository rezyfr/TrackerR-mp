package dev.rezyfr.trackerr.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberAsyncImagePainter
import com.seiko.imageloader.rememberImagePainter
import dev.rezyfr.trackerr.presentation.component.griddropdown.GridDropdownMenu

@Composable
fun ChooseIconButton(
    chosenIcon: String,
    icons: List<String>,
    onIconChoose: (String) -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    onClick: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
    ) {
        Image(painter = rememberImagePainter(url = chosenIcon),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(12.dp)
                )
                .clip(RoundedCornerShape(12.dp))
                .clickable {
                    expanded = true
                    onClick.invoke()
                }
                .background(color = color.copy(alpha = 0.25f), shape = RoundedCornerShape(12.dp))
                .padding(12.dp)
                .align(Alignment.Center),
            colorFilter = ColorFilter.tint(color)
        )
        Box {
            CategoryIconDropDown(
                modifier = Modifier
                    .requiredSizeIn(maxHeight = 360.dp, maxWidth = 300.dp),
                expanded = expanded,
                onDismissRequest = { expanded = false },
                columnSize = 40.dp,
                yOffset = 52.dp
            ) {
                items(icons) { iconUrl ->
                    Image(
                        modifier = Modifier
                            .size(40.dp)
                            .padding(0.5.dp)
                            .clip(CircleShape)
                            .clickable {
                                expanded = false
                                onIconChoose(iconUrl)
                            }
                            .padding(7.5.dp),
                        painter = rememberAsyncImagePainter(iconUrl),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryIconDropDown(
    columnSize: Dp,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier,
    xOffset: Dp = 0.dp,
    yOffset: Dp,
    content: LazyGridScope.() -> Unit
) {
    GridDropdownMenu(
        columnSize = columnSize,
        modifier = modifier,
        expanded = expanded,
        offset = DpOffset(x = xOffset, y = yOffset),
        onDismissRequest = onDismissRequest,
        content = content
    )
}