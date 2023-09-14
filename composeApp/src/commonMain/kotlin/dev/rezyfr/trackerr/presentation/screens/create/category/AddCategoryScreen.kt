package dev.rezyfr.trackerr.presentation.screens.create.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import dev.rezyfr.trackerr.presentation.HSpacer
import dev.rezyfr.trackerr.presentation.component.base.ButtonText
import dev.rezyfr.trackerr.presentation.component.base.TrPrimaryButton
import dev.rezyfr.trackerr.presentation.component.base.TrTextField
import dev.rezyfr.trackerr.presentation.component.ui.IconPicker
import dev.rezyfr.trackerr.presentation.screens.create.category.components.ColorPicker
import dev.rezyfr.trackerr.presentation.screens.create.category.store.AddCategoryStore
import dev.rezyfr.trackerr.presentation.screens.create.transaction.TransactionAppBar
import dev.rezyfr.trackerr.presentation.screens.create.transaction.component.RevealingSheet
import dev.rezyfr.trackerr.presentation.theme.typeIndicatorColor

@Composable
fun AddCategoryScreen(
    component: AddCategoryComponent,
) {
    val state = component.state.collectAsState()

    AddCategoryScreen(
        state = state.value,
        onEvent = component::onEvent,
        onAction = component::onAction,
    )
}

@Composable
private fun AddCategoryScreen(
    state: AddCategoryStore.State,
    onEvent: (AddCategoryStore.Intent) -> Unit,
    onAction: (AddCategoryComponent.Action) -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TransactionAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                    type = state.type,
                    onBack = { onAction(AddCategoryComponent.Action.NavigateBack) },
                    onSelectType = { onEvent(AddCategoryStore.Intent.OnTypeChange(it)) },
                )
            },
            containerColor = state.type.typeIndicatorColor()
        ) {
            Box(
                Modifier.fillMaxSize()
                    .background(state.type.typeIndicatorColor()),
                contentAlignment = Alignment.BottomCenter
            ) {
                AddCategoryDialog(
                    state = state,
                    onEvent = onEvent,
                )
            }
        }
        RevealingSheet(
            bottomSheet = state.iconSheet,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            IconPicker(
                modifier = Modifier.fillMaxWidth(),
                selectedWalletId = state.selectedIcon?.id,
                idAndIcon = state.iconList.map { it.id to it.url },
                onIconChoose = {
                    state.iconSheet.collapse()
                    onEvent(AddCategoryStore.Intent.OnIconChange(it))
                }
            )
        }
    }

    LaunchedEffect(state.result) {
        if (state.result.isSuccess()) {
            onAction(AddCategoryComponent.Action.NavigateBack)
        }
    }
}

@Composable
fun IconPickerSection(
    modifier: Modifier = Modifier,
    state: AddCategoryStore.State,
    onEvent: (AddCategoryStore.Intent) -> Unit,
    onClick: () -> Unit = {},
) {
    Row(
        modifier
            .fillMaxWidth()

    ) {
        ChosenIconButton(
            iconUrl = state.selectedIcon?.url.orEmpty(),
            color = state.selectedColor,
            onClick = { state.iconSheet.expand() }
        )
        HSpacer(16)
        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            items(state.colorList) {
                ColorPicker(
                    color = it,
                    selected = state.selectedColor == it
                ) { color ->
                    onEvent(AddCategoryStore.Intent.OnColorChange(color))
                }
            }
        }
    }
}

@Composable
fun ChosenIconButton(
    iconUrl: String,
    color: Color,
    onClick: () -> Unit = {},
) {
    Image(painter = rememberImagePainter(url = iconUrl),
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
                onClick.invoke()
            }
            .background(color = color.copy(alpha = 0.25f), shape = RoundedCornerShape(12.dp))
            .padding(12.dp),
        colorFilter = ColorFilter.tint(color)
    )
}

@Composable
private fun AddCategoryDialog(
    modifier: Modifier = Modifier,
    state: AddCategoryStore.State,
    onEvent: (AddCategoryStore.Intent) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(
                    topStart = 32.dp, topEnd = 32.dp
                )
            )
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TrTextField(
            placeholder = "Name",
            value = state.name,
            onValueChange = {
                onEvent(AddCategoryStore.Intent.OnNameChange(it))
            }
        )
        IconPickerSection(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
            state = state,
            onEvent = onEvent
        )
        TrPrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = { ButtonText("Continue") },
            onClick = {
                onEvent(AddCategoryStore.Intent.CreateCategory)
            }
        )
    }
}