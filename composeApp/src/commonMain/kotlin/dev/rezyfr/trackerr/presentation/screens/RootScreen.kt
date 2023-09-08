package dev.rezyfr.trackerr.presentation.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import dev.rezyfr.trackerr.Res
import dev.rezyfr.trackerr.presentation.screens.create.transaction.AddTransactionScreen
import dev.rezyfr.trackerr.presentation.screens.home.HomeTab
import dev.rezyfr.trackerr.presentation.screens.transaction.TransactionTab
import dev.rezyfr.trackerr.presentation.theme.Disabled
import dev.rezyfr.trackerr.presentation.theme.Green100
import dev.rezyfr.trackerr.presentation.theme.GreenIcon
import io.github.skeptick.libres.compose.painterResource
import io.github.skeptick.libres.images.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext


class RootScreen : Screen {

    private val tabs = listOf(HomeTab(), TransactionTab())

    @Composable
    override fun Content() {
        var isFabFocused by remember { mutableStateOf(false) }

        val rotation = remember { Animatable(0f) }
        val bgTranslation = remember { Animatable(0f) }
        val transactionTranslation = remember { Animatable(0f) }
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(isFabFocused) {
            awaitAll(
                async { transactionTranslation.animateTo(targetValue = if (isFabFocused) -57f else 0f) },
                async { bgTranslation.animateTo(targetValue = if (isFabFocused) 100f else 0f) },
                async { rotation.animateTo(targetValue = if (isFabFocused) 45f else 0f) }
            )
        }
        TabNavigator(tab = HomeTab()) { nav ->
            Scaffold(
                bottomBar = {
                    RootNavBar(
                        tabs = tabs,
                        onTabSelected = { tab ->
                            nav.current = tab
                        },
                        selectedTab = nav.current
                    )
                },
                floatingActionButton = {
                    HomeFab(
                        rotation = rotation.value,
                        translation = transactionTranslation.value,
                        onClick = {
                            isFabFocused = !isFabFocused
                        },
                        onExpenseClick = {
                            navigator.push(AddTransactionScreen())
                        },
                    )
                },
                floatingActionButtonPosition = FabPosition.Center,
                isFloatingActionButtonDocked = true
            ) {
                Box(Modifier.padding(bottom = it.calculateBottomPadding())) {
                    nav.current.Content()
                    TransparentPrimaryBackground(
                        height = bgTranslation.value, modifier = Modifier.align(
                            Alignment.BottomCenter
                        )
                    )
                }
            }
        }
    }

    @Composable
    fun HomeFab(
        rotation: Float = 0f,
        translation: Float = 0f,
        onClick: () -> Unit = {},
        onExpenseClick: () -> Unit = {},
        onIncomeClick: () -> Unit = {},
    ) {
        IncomeFab(translation = translation, onClick = onIncomeClick)
        ExpenseFab(translation = translation, onClick = onExpenseClick)
        MainFab(rotation, onClick)
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
    fun MainFab(
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
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                            MaterialTheme.colorScheme.primary.copy(alpha = 0f)
                        ),
                        start = Offset(0f, 200f),
                        end = Offset(0f, 0f)
                    )
                )
        )
    }

    @Composable
    fun RootNavBar(
        tabs: List<Tab>,
        onTabSelected: (Tab) -> Unit,
        selectedTab: Tab
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
                tabs.forEach {
                    val isSelected = it == selectedTab
                    BottomNavigationItem(
                        selected = isSelected,
                        onClick = {
                            onTabSelected(it)
                        },
                        icon = {
                            Image(
                                painter = it.options.icon!!,
                                contentDescription = null,
                            )
                        },
                        label = {
                            Text(
                                it.options.title, style = MaterialTheme.typography.labelSmall.copy(
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
}