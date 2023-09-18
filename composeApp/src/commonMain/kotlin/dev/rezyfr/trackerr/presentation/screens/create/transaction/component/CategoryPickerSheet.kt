package dev.rezyfr.trackerr.presentation.screens.create.transaction.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.model.CategoryModel
import dev.rezyfr.trackerr.presentation.VSpacer
import dev.rezyfr.trackerr.presentation.theme.color

@Composable
fun CategoryPickerSheet(
    modifier: Modifier = Modifier,
    currentCategory: CategoryModel? = null,
    categories: UiResult<List<CategoryModel>>,
    onContinue: (Int) -> Unit = {},
    onAddClick : () -> Unit = {}
) {
    var selectedCategory by remember { mutableStateOf(currentCategory) }

    FormTransactionSheet(
        modifier = modifier,
        label = "Category",
        onContinue = {
            selectedCategory?.let {
                onContinue(it.id)
            }
        },
    ) {
        if (categories is UiResult.Success) {
            CategoryGrid(
                categories = categories.data.asSequence(),
                isSelected = { selectedCategory?.id == it.id },
                onClick = { selectedCategory = it },
                onAddClick = onAddClick
            )
        }
    }
}

@Composable
fun CategoryGrid(
    categories: Sequence<CategoryModel>,
    isSelected: (CategoryModel) -> Boolean,
    onClick: (CategoryModel) -> Unit,
    onAddClick: (() -> Unit)? = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories.toList()) { category ->
            CategoryItem(
                category = category,
                isSelected = isSelected(category),
                onClick = { onClick(category) }
            )
        }
        onAddClick?.let {
            item {
                AddCategory {
                    onAddClick.invoke()
                }
            }
        }
    }
}

@Composable
fun AddCategory(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.tertiary)
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Rounded.Add, null, modifier = Modifier.size(28.dp), tint = MaterialTheme.colorScheme.onTertiary)
        Text(
            "Add Category",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelMedium.copy(
                color = MaterialTheme.colorScheme.onTertiary
            ),
        )
        VSpacer(2)
    }
}

@Composable
fun CategoryItem(
    category: CategoryModel,
    isSelected: Boolean,
    onClick: (Int) -> Unit,
) {
    val color = Color(category.color)
    Column(
        modifier = Modifier.aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(color.copy(alpha = 0.2f))
            .run {
                if (isSelected) {
                    border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(8.dp)
                    )
                } else {
                    this
                }
            }
            .clickable {
                onClick(category.id)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            rememberImagePainter(category.icon),
            null,
            modifier = Modifier.size(28.dp)
                .clip(RoundedCornerShape(16.dp)),
            colorFilter = ColorFilter.tint(Color(category.color))
        )
        VSpacer(4)
        Text(
            category.name,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelMedium.copy(
                color = Color(category.color)
            ),
        )
        VSpacer(2)
    }
}