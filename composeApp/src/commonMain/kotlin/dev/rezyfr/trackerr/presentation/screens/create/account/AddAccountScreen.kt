package dev.rezyfr.trackerr.presentation.screens.create.account

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import dev.rezyfr.trackerr.Res.image
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.model.IconModel
import dev.rezyfr.trackerr.presentation.VSpacer
import dev.rezyfr.trackerr.presentation.component.AmountTextField
import dev.rezyfr.trackerr.presentation.component.base.ButtonText
import dev.rezyfr.trackerr.presentation.component.base.TrPrimaryButton
import dev.rezyfr.trackerr.presentation.component.base.TrTextField
import dev.rezyfr.trackerr.presentation.component.base.TrTopBar
import dev.rezyfr.trackerr.presentation.component.base.TrTopBarDefaults
import dev.rezyfr.trackerr.presentation.component.griddropdown.GridDropdownMenu
import dev.rezyfr.trackerr.presentation.screens.create.account.store.AddAccountStore
import io.github.skeptick.libres.compose.painterResource
import kotlinx.coroutines.delay

@Composable
fun AddAccountScreen(
    component: AddAccountComponent,
    onBoardingSuccess: (() -> Unit)? = null,
) {
    val uiState by component.state.collectAsState()

    AddAccountScreen(
        onEvent = component::onEvent,
        onAction = component::onAction,
        onBoardingSuccess = onBoardingSuccess,
        state = uiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddAccountScreen(
    onEvent: (AddAccountStore.Intent) -> Unit,
    onAction: (AddAccountComponent.Action) -> Unit,
    onBoardingSuccess: (() -> Unit)? = null,
    state: AddAccountStore.State
) {
    Box(Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TrTopBar(
                    text = "Add new account",
                    onBackPressed = { onAction.invoke(AddAccountComponent.Action.NavigateBack) },
                    color = TrTopBarDefaults.primaryBarColor()
                )
            },
        ) {
            Box(
                Modifier.fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.BottomCenter
            ) {
                AddAccountDialog(
                    onEvent = onEvent,
                    state = state
                )
            }
        }
        AnimatedVisibility(
            modifier = Modifier.fillMaxSize(),
            visible = state.result is UiResult.Success,
            enter = slideInVertically(
                animationSpec = tween(1000),
                initialOffsetY = { fullHeight -> fullHeight }
            ),
        ) {
            SuccessScreen(Modifier.fillMaxSize())

            LaunchedEffect(state.result) {
                if (state.result is UiResult.Success) {
                    delay(3000)
                    onBoardingSuccess?.invoke()
                }
            }
        }
    }
}

@Composable
fun SuccessScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(image.ic_success),
            null
        )
        VSpacer(16)
        Text(
            text = "You are set!",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun AddAccountDialog(
    modifier: Modifier = Modifier,
    onEvent: (AddAccountStore.Intent) -> Unit,
    state: AddAccountStore.State
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        AmountTextField(
            modifier = Modifier.padding(16.dp),
            label = "Balance",
            onValueChange = { onEvent(AddAccountStore.Intent.OnBalanceChange(it)) },
            value = state.balance
        )
        Column(
            Modifier
                .fillMaxWidth()
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
                onValueChange = { onEvent(AddAccountStore.Intent.OnNameChange(it)) }
            )
            BankSection(
                chosenIcon = state.icon,
                icons = state.iconList,
                onIconChoose = { onEvent(AddAccountStore.Intent.OnIconChange(it)) }
            )
            TrPrimaryButton(
                Modifier.fillMaxWidth(),
                text = { ButtonText("Continue") },
                onClick = { onEvent(AddAccountStore.Intent.CreateWallet) }
            )
        }
    }
}

@Composable
fun BankSection(
    chosenIcon: Int,
    icons: List<IconModel>,
    onIconChoose: (Int) -> Unit,
) {
    Text("Bank", style = MaterialTheme.typography.bodyMedium)
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(icons) { icon ->
            val isChosen = chosenIcon == icon.id
            IconButton(
                onClick = { onIconChoose(icon.id) },
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .then(
                        if (isChosen) {
                            Modifier.background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(8.dp)
                            ).border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(8.dp)
                            )
                        } else {
                            Modifier.background(
                                color = MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(8.dp)
                            ).border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(8.dp)
                            )
                        }
                    )
                    .padding(4.dp)
            ) {
                Image(
                    painter = rememberImagePainter(icon.url),
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