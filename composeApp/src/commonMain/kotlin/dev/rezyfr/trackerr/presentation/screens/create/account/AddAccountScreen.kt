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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
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
import dev.rezyfr.trackerr.presentation.screens.RootScreen
import kotlinx.coroutines.delay
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class AddAccountScreen : Screen, KoinComponent {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel by inject<AddAccountViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        AddAccountScreen(
            onContinue = { viewModel.onContinue() },
            onChangeName = { viewModel.onChangeName(it) },
            onChangeBalance = { viewModel.onChangeBalance(it) },
            onSelectIcon = { viewModel.onSelectIcon(it) },
            onBackPressed = { navigator.pop() },
            onSuccess = { navigator.replaceAll(RootScreen()) },
            state = uiState
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun AddAccountScreen(
        onContinue: () -> Unit = { },
        onChangeName: (String) -> Unit = { },
        onChangeBalance: (TextFieldValue) -> Unit = { },
        onSelectIcon: (Int) -> Unit = { },
        onBackPressed: () -> Unit = { },
        onSuccess: () -> Unit = { },
        state: AddAccountState
    ) {
        Box(Modifier.fillMaxSize()) {
            Scaffold(
                topBar = {
                    TrTopBar(
                        text = "Add new account",
                        onBackPressed = onBackPressed,
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
                        onContinue = onContinue,
                        onChangeName = onChangeName,
                        onChangeBalance = onChangeBalance,
                        onSelectIcon = onSelectIcon,
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
                    if (state.result is UiResult.Success){
                        delay(3000)
                        onSuccess()
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
        onContinue: () -> Unit,
        onChangeName: (String) -> Unit,
        onChangeBalance: (TextFieldValue) -> Unit,
        onSelectIcon: (Int) -> Unit,
        state: AddAccountState
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            AmountTextField(
                modifier = Modifier.padding(16.dp),
                label = "Balance",
                onValueChange = { onChangeBalance(it) },
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
                    onValueChange = { onChangeName(it) }
                )
                BankSection(
                    chosenIcon = state.icon,
                    icons = state.iconList,
                    onIconChoose = { onSelectIcon(it) }
                )
                TrPrimaryButton(
                    Modifier.fillMaxWidth(),
                    text = { ButtonText("Continue") },
                    onClick = onContinue
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
}


@Composable
fun ChooseIconButton(
    chosenIcon: String,
    icons: List<IconModel>,
    onIconChoose: (String) -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    onClick: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
    ) {
        Image(painter = rememberImagePainter(chosenIcon),
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
                items(icons) { icon ->
                    Image(
                        modifier = Modifier
                            .size(40.dp)
                            .padding(0.5.dp)
                            .clip(CircleShape)
                            .clickable {
                                expanded = false
                                onIconChoose(icon.url)
                            }
                            .padding(7.5.dp),
                        painter = rememberImagePainter(icon.url),
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