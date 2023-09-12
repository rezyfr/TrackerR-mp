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
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories.data) { category ->
                    CategoryItem(
                        category = category,
                        isSelected = selectedCategory?.id == category.id,
                        onClick = { selectedCategory = category }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryItem(
    category: CategoryModel,
    isSelected: Boolean,
    onClick: (Int) -> Unit,
) {
    Column(
        modifier = Modifier.aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
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
            colorFilter = ColorFilter.tint(category.color.color())
        )
        VSpacer(4)
        Text(
            category.name,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelMedium.copy(
                color = category.color.color()
            ),
        )
        VSpacer(2)
    }
}