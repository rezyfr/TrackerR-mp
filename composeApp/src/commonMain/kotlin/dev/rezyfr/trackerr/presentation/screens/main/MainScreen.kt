package dev.rezyfr.trackerr.presentation.screens.main

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import dev.rezyfr.trackerr.Res
import dev.rezyfr.trackerr.presentation.component.base.datepicker.Month
import dev.rezyfr.trackerr.presentation.screens.create.transaction.component.RevealingSheet
import dev.rezyfr.trackerr.presentation.screens.main.home.HomeTab
import dev.rezyfr.trackerr.presentation.screens.main.home.components.MonthPickerSheet
import dev.rezyfr.trackerr.presentation.screens.main.transaction.TransactionTab
import dev.rezyfr.trackerr.presentation.theme.Disabled
import dev.rezyfr.trackerr.presentation.theme.Green100
import io.github.skeptick.libres.compose.painterResource
import io.github.skeptick.libres.images.Image
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

@Composable
fun MainMenu(mainComponent: MainComponent) {
    var isFabFocused by remember { mutableStateOf(false) }
    val rotation = remember { Animatable(0f) }
    val bgTranslation = remember { Animatable(0f) }
    val transactionTranslation = remember { Animatable(0f) }
    val snackbarHostState = remember { SnackbarHostState() }
    val activeIndex by mainComponent.activeTabIndex.subscribeAsState()
    val monthPickerState by mainComponent.monthPickerState.collectAsState()

    Box(Modifier.fillMaxSize()) {
        Scaffold(
            bottomBar = {
                RootNavBar(
                    onTabSelected = mainComponent::onTabSelected,
                    selectedTabIndex = activeIndex,
                )
            },
            floatingActionButton = {
                MainFab(
                    rotation = rotation.value,
                    translation = transactionTranslation.value,
                    onClick = {
                        isFabFocused = !isFabFocused
                    },
                    onExpenseClick = {
                        mainComponent.onAction(MainComponent.Action.NavigateToAddTransaction)
                    },
                )
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
            snackbarHost = {
                SnackbarHost(snackbarHostState)
            }
        ) {
            Box(Modifier.padding(bottom = it.calculateBottomPadding())) {
                MainTabContent(
                    child = mainComponent.child,
                    selectedMonth = monthPickerState.selectedMonth,
                    onMonthClick = {
                        monthPickerState.monthPickerSheet.expand()
                    }
                )
                TransparentPrimaryBackground(
                    height = bgTranslation.value, modifier = Modifier.align(
                        Alignment.BottomCenter
                    )
                )
            }
        }

        RevealingSheet(
            monthPickerState.monthPickerSheet,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            MonthPickerSheet(
                modifier = Modifier.fillMaxWidth(),
                month = monthPickerState.selectedMonth,
                monthOptions = monthPickerState.monthOptions,
                onChangeMonth = {
                    monthPickerState.monthPickerSheet.collapse()
                    mainComponent.onEvent(MainComponent.Intent.OnChangeMonth(it))
                }
            )
        }
    }

    LaunchedEffect(isFabFocused) {
        awaitAll(
            async { transactionTranslation.animateTo(targetValue = if (isFabFocused) -57f else 0f) },
            async { bgTranslation.animateTo(targetValue = if (isFabFocused) 250f else 0f) },
            async { rotation.animateTo(targetValue = if (isFabFocused) 45f else 0f) }
        )
    }
}

@Composable
fun MainTabContent(
    child: Value<ChildStack<*, MainComponent.Child>>,
    selectedMonth: Month,
    onMonthClick: () -> Unit = {},
) {
    Children(child) {
        (it.instance as MainComponent.Tab).let { child ->
            when (child) {
                is MainComponent.Tab.Home -> HomeTab(
                    child.homeComponent,
                    onMonthClick = onMonthClick,
                    selectedMonth = selectedMonth
                )

                is MainComponent.Tab.Transaction -> TransactionTab(
                    child.transactionComponent,
                    onMonthClick = onMonthClick,
                    selectedMonth = selectedMonth
                )
            }
        }
    }
}

@Composable
fun MainFab(
    rotation: Float = 0f,
    translation: Float = 0f,
    onClick: () -> Unit = {},
    onExpenseClick: () -> Unit = {},
    onIncomeClick: () -> Unit = {},
) {
    IncomeFab(translation = translation, onClick = onIncomeClick)
    ExpenseFab(translation = translation, onClick = onExpenseClick)
    AddFab(rotation, onClick)
}

@Composable
fun IncomeFab(
    translation: Float = 0f,
    onClick: () -> Unit = {},
) {
    TrFab(
        modifier = Modifier.absoluteOffset(y = translation.dp, x = translation.dp),
        onClick = onClick,
        bgColor = Green100,
        image = Res.image.ic_income
    )
}

@Composable
fun ExpenseFab(
    translation: Float = 0f,
    onClick: () -> Unit = {},
) {
    TrFab(
        modifier = Modifier.absoluteOffset(y = translation.dp, x = -translation.dp),
        onClick = onClick,
        bgColor = MaterialTheme.colorScheme.error,
        image = Res.image.ic_expense
    )
}

@Composable
fun AddFab(
    rotation: Float = 0f,
    onClick: () -> Unit = {},
) {
    TrFab(
        modifier = Modifier.rotate(rotation),
        icon = Icons.Rounded.Add,
        bgColor = MaterialTheme.colorScheme.primary,
        onClick = onClick
    )
}

@Composable
fun TrFab(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    image: Image? = null,
    bgColor: Color,
    onClick: () -> Unit = {},
) {
    FloatingActionButton(
        onClick = onClick,
        content = {
            if (image != null) {
                Image(
                    painter = image.painterResource(),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                    modifier = Modifier.size(36.dp)
                )
            } else if (icon != null) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(36.dp)
                )
            }
        },
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp),
        shape = CircleShape,
        backgroundColor = bgColor,
        modifier = modifier
    )
}

@Composable
fun TransparentPrimaryBackground(modifier: Modifier = Modifier, height: Float) {
    Box(
        modifier
            .fillMaxWidth()
            .height(height.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0f),
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                    )
                )
            )
    )
}

@Composable
fun RootNavBar(
    tabs: List<MainTab> = listOf(MainTab.Home, MainTab.Transaction),
    onTabSelected: (Int) -> Unit,
    selectedTabIndex: Int,
) {
    BottomAppBar(
        backgroundColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier,
        cutoutShape = CircleShape,
    ) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
        ) {
            tabs.forEachIndexed { index, tab ->
                val isSelected = index == selectedTabIndex
                BottomNavigationItem(
                    selected = isSelected,
                    onClick = {
                        onTabSelected(index)
                    },
                    icon = {
                        Image(
                            painter = if (isSelected) tab.activeIcon.painterResource() else tab.inactiveIcon.painterResource(),
                            contentDescription = null,
                        )
                    },
                    label = {
                        Text(
                            tab.title, style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else Disabled
                            )
                        )
                    }
                )
            }
        }
    }
}

sealed class MainTab(
    val title: String,
    val activeIcon: Image,
    val inactiveIcon: Image
) {
    object Home : MainTab("Home", Res.image.ic_menu_home_active, Res.image.ic_menu_home_inactive)
    object Transaction : MainTab(
        "Transaction",
        Res.image.ic_menu_transaction_active,
        Res.image.ic_menu_transaction_inactive
    )
}