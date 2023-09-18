package dev.rezyfr.trackerr.presentation.screens.main.transaction.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.rezyfr.trackerr.domain.model.CategoryModel
import dev.rezyfr.trackerr.domain.model.CategoryType
import dev.rezyfr.trackerr.domain.model.Granularity
import dev.rezyfr.trackerr.presentation.VSpacer
import dev.rezyfr.trackerr.presentation.component.base.ButtonText
import dev.rezyfr.trackerr.presentation.component.base.TrCapsuleButton
import dev.rezyfr.trackerr.presentation.component.base.TrPrimaryButton
import dev.rezyfr.trackerr.presentation.component.base.datepicker.DefaultWheelDatePicker
import dev.rezyfr.trackerr.presentation.component.base.datepicker.Month
import dev.rezyfr.trackerr.presentation.component.ui.BottomSheet
import dev.rezyfr.trackerr.presentation.screens.create.transaction.component.CategoryGrid
import dev.rezyfr.trackerr.presentation.screens.create.transaction.component.CategoryPickerSheet
import dev.rezyfr.trackerr.presentation.screens.create.transaction.component.FormTransactionSheet
import dev.rezyfr.trackerr.presentation.screens.create.transaction.component.RevealingSheet
import dev.rezyfr.trackerr.presentation.theme.Violet40
import dev.rezyfr.trackerr.presentation.theme.Yellow100
import dev.rezyfr.trackerr.presentation.theme.Yellow20

@Composable
fun FilterPickerSheet(
    modifier: Modifier,
    categorySheet: BottomSheet,
    sortOrders: List<String>,
    sortOrder: String,
    type: CategoryType?,
    categories: Sequence<CategoryModel>,
    selectedCategories: Sequence<Int>,
    onResetClick: () -> Unit = {},
    onTypeSelect: (CategoryType) -> Unit = {},
    onSortSelect: (String) -> Unit = {},
    onCategorySelect: (List<Int>) -> Unit = {},
    onContinue: () -> Unit = {},
) {
    Box(Modifier.fillMaxSize()) {
        Column(
            modifier
                .align(Alignment.BottomCenter)
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(
                        topStart = 32.dp, topEnd = 32.dp
                    )
                )
                .padding(start = 16.dp, end = 16.dp, bottom = 24.dp, top = 16.dp)
                .scrollable(rememberScrollState(), Orientation.Vertical),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                Modifier.background(Violet40, RoundedCornerShape(2.dp)).height(4.dp).width(36.dp)
                    .align(Alignment.CenterHorizontally)
            )
            ResetSection(
                modifier = Modifier.fillMaxWidth(),
                onResetClick = onResetClick,
            )
            FilterSection(
                modifier = Modifier.wrapContentWidth(),
                selectedType = type,
                onTypeSelect = onTypeSelect
            )
            SortSection(
                modifier = Modifier.fillMaxWidth(),
                selectedSortOrder = sortOrder,
                sortOrders = sortOrders,
                onSortSelect = onSortSelect
            )
            CategorySection(
                modifier = Modifier.fillMaxWidth(),
                selectedCategories = selectedCategories,
                onClick = { categorySheet.expand() }
            )
            TrPrimaryButton(
                Modifier.fillMaxWidth(),
                text = { ButtonText("Continue") },
                onClick = onContinue
            )
        }
        RevealingSheet(
            categorySheet,
            Modifier.align(Alignment.BottomCenter),
        ) {
            CategoryFilterSheet(
                Modifier.fillMaxWidth(),
                categories = categories,
                selectedCategories = selectedCategories,
                onCategorySelect = onCategorySelect
            )
        }
    }
}

@Composable
fun CategoryFilterSheet(
    modifier: Modifier = Modifier,
    categories: Sequence<CategoryModel>,
    selectedCategories: Sequence<Int>,
    onCategorySelect: (List<Int>) -> Unit = {},
) {
    val selected = remember {
        mutableStateListOf<Int>().apply {
            addAll(selectedCategories)
        }
    }
    FormTransactionSheet(
        modifier, label = "Category", onContinue = {
            onCategorySelect.invoke(selected)
        }
    ) {
        CategoryGrid(
            categories,
            isSelected = { selected.contains(it.id) },
            onClick = {
                if (selected.contains(it.id)) {
                    selected.remove(it.id)
                } else {
                    selected.add(it.id)
                }
            }
        )
    }
}

@Composable
fun CategorySection(
    modifier: Modifier,
    selectedCategories: Sequence<Int>,
    onClick: () -> Unit = {},
) {
    Text(
        "Category",
        style = MaterialTheme.typography.bodyMedium
    )
    Row(
        modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Choose Category", style = MaterialTheme.typography.bodyLarge)
        Row(
            Modifier.clickable { onClick.invoke() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "${selectedCategories.count()} Selected",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.tertiary
                )
            )
            Icon(Icons.Rounded.ChevronRight, null, tint = MaterialTheme.colorScheme.primary)
        }
    }
    VSpacer(16)
}

@Composable
fun SortSection(
    modifier: Modifier,
    selectedSortOrder: String,
    sortOrders: List<String>,
    onSortSelect: (String) -> Unit
) {
    Text(
        "Sort By",
        style = MaterialTheme.typography.bodyMedium
    )
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        content = {
            items(sortOrders) { item ->
                FilterButton(
                    modifier = Modifier.wrapContentWidth(),
                    isActive = selectedSortOrder == item,
                    text = item,
                    onClick = onSortSelect
                )
            }
        }
    )
}

@Composable
fun FilterSection(
    modifier: Modifier,
    selectedType: CategoryType?,
    onTypeSelect: (CategoryType) -> Unit
) {
    Text(
        "Filter By",
        style = MaterialTheme.typography.bodyMedium
    )
    Row(
        modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilterButton(
            modifier = Modifier.wrapContentWidth(),
            isActive = selectedType == CategoryType.EXPENSE,
            text = CategoryType.EXPENSE.label,
            onClick = { onTypeSelect.invoke(CategoryType.EXPENSE) }
        )
        FilterButton(
            modifier = Modifier.wrapContentWidth(),
            isActive = selectedType == CategoryType.INCOME,
            text = CategoryType.INCOME.label,
            onClick = { onTypeSelect.invoke(CategoryType.INCOME) }
        )
    }
}

@Composable
fun ResetSection(
    modifier: Modifier = Modifier,
    onResetClick: () -> Unit = {},
) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Filter Transaction",
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground)
        )
        TrCapsuleButton("Reset", onClick = onResetClick, modifier = Modifier.height(32.dp))
    }
}

@Composable
fun FilterButton(
    modifier: Modifier = Modifier,
    isActive: Boolean,
    text: String,
    onClick: (String) -> Unit,
) {
    TrCapsuleButton(
        text = text,
        modifier = modifier
            .height(42.dp),
        containerColor = if (isActive) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
        contentColor = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
        border = if (isActive) null else BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        onClick = { onClick.invoke(text) }
    )
}
