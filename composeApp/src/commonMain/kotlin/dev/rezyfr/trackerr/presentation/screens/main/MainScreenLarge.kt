package dev.rezyfr.trackerr.presentation.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.NavigationRail
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import dev.rezyfr.trackerr.domain.model.CategoryType
import dev.rezyfr.trackerr.presentation.HSpacer
import dev.rezyfr.trackerr.presentation.VSpacer
import dev.rezyfr.trackerr.presentation.component.base.datepicker.Month
import dev.rezyfr.trackerr.presentation.component.util.NoRippleInteractionSource
import dev.rezyfr.trackerr.presentation.component.util.noRippleClick
import dev.rezyfr.trackerr.presentation.screens.create.transaction.component.RevealingSheet
import dev.rezyfr.trackerr.presentation.screens.main.home.HomeTabLarge
import dev.rezyfr.trackerr.presentation.screens.main.home.components.MonthPickerSheet
import dev.rezyfr.trackerr.presentation.screens.main.transaction.TransactionTab
import dev.rezyfr.trackerr.presentation.screens.main.transaction.components.FilterPickerSheet
import io.github.skeptick.libres.compose.painterResource

@Composable
fun MainScreenLarge(mainComponent: MainComponent) {
    val activeIndex by mainComponent.activeTabIndex.subscribeAsState()
    val monthPickerState by mainComponent.monthPickerState.collectAsState()
    val filterPickerState by mainComponent.filterPickerState.collectAsState()

    Row(Modifier.fillMaxSize()) {
        RootNavRail(
            onTabSelected = mainComponent::onTabSelected,
            selectedTabIndex = activeIndex,
            onFabClick = { mainComponent.onAction(MainComponent.Action.NavigateToAddTransaction) }
        )
        HSpacer(8)
        Box(Modifier.weight(1f)) {
            MainTabContent(
                child = mainComponent.child,
                selectedMonth = monthPickerState.selectedMonth,
                selectedSort = filterPickerState.selectedSortOrder,
                selectedType = filterPickerState.selectedType,
                categoryIds = filterPickerState.selectedCategoryIds,
                appliedFilter = filterPickerState.appliedFilter,
                onMonthClick = {
                    monthPickerState.monthPickerSheet.expand()
                },
                onFilterClick = {
                    filterPickerState.filterPickerSheet.expand()
                },
                onAppliedFilter = {
                    mainComponent.onEvent(MainComponent.Intent.OnApplyFilter)
                },
                onReportWrapClick = {
                    mainComponent.onAction(MainComponent.Action.NavigateToReportWrap)
                },
            )
        }
    }
    Box(Modifier.fillMaxSize()) {
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

        RevealingSheet(
            filterPickerState.filterPickerSheet,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            FilterPickerSheet(
                modifier = Modifier.fillMaxWidth(),
                categorySheet = filterPickerState.categoryPickerSheet,
                sortOrders = filterPickerState.sortOrders,
                sortOrder = filterPickerState.selectedSortOrder.orEmpty(),
                type = filterPickerState.selectedType,
                categories = filterPickerState.categories,
                selectedCategories = filterPickerState.selectedCategoryIds,
                onTypeSelect = {
                    mainComponent.onEvent(MainComponent.Intent.OnSelectType(it))
                },
                onResetClick = {
                    mainComponent.onEvent(MainComponent.Intent.OnResetFilter)
                },
                onSortSelect = {
                    mainComponent.onEvent(MainComponent.Intent.OnSelectSort(it))
                },
                onContinue = {
                    mainComponent.onEvent(MainComponent.Intent.OnApplyFilter)
                },
                onCategorySelect = {
                    mainComponent.onEvent(MainComponent.Intent.OnSelectCategories(it.asSequence()))
                },
            )
        }
    }
}
@Composable
private fun MainTabContent(
    child: Value<ChildStack<*, MainComponent.Child>>,
    selectedMonth: Month,
    selectedSort: String? = null,
    selectedType: CategoryType? = null,
    categoryIds: Sequence<Int>? = null,
    appliedFilter: Boolean = false,
    onMonthClick: () -> Unit = {},
    onFilterClick: () -> Unit = {},
    onAppliedFilter: () -> Unit = {},
    onReportWrapClick: () -> Unit = {}
) {
    Children(child) {
        (it.instance as MainComponent.Tab).let { child ->
            when (child) {
                is MainComponent.Tab.Home -> HomeTabLarge(
                    child.homeComponent,
                    selectedMonth = selectedMonth,
                )

                is MainComponent.Tab.Transaction -> {
                    TransactionTab(
                        child.transactionComponent,
                        selectedMonth = selectedMonth,
                        selectedSort = selectedSort,
                        selectedType = selectedType,
                        categoryIds = categoryIds,
                        onMonthClick = onMonthClick,
                        onFilterClick = onFilterClick,
                        appliedFilter = appliedFilter,
                        onAppliedFilter = onAppliedFilter,
                        onReportWrapClick = onReportWrapClick
                    )
                }
            }
        }
    }
}
@Composable
private fun AddFab(
    onClick: () -> Unit = {},
) {
    TrFab(
        icon = Icons.Rounded.Add,
        bgColor = MaterialTheme.colorScheme.primary,
        onClick = onClick,
        modifier = Modifier.size(40.dp)
    )
}
@Composable
private fun RootNavRail(
    tabs: List<MainTab> = listOf(MainTab.Home, MainTab.Transaction),
    onTabSelected: (Int) -> Unit,
    selectedTabIndex: Int,
    onFabClick: () -> Unit = {},
) {
    NavigationRail(
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier,
        content = {
            Box(
                Modifier.padding(16.dp)
                    .clip(CircleShape)
                    .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
            ) {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(4.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                        .align(Alignment.Center)
                        .size(32.dp)
                )
            }
            VSpacer(8)
            tabs.forEachIndexed { index, tab ->
                val isSelected = index == selectedTabIndex
                Box(Modifier.size(72.dp)) {
                    Image(
                        modifier = Modifier.size(24.dp).noRippleClick { onTabSelected(index) }.align(Alignment.Center),
                        painter = if (isSelected) tab.activeIcon.painterResource() else tab.inactiveIcon.painterResource(),
                        contentDescription = null,
                    )
                    if (isSelected) {
                        Box(
                            Modifier.width(6.dp).height(12.dp).align(Alignment.CenterEnd).background(
                                MaterialTheme.colorScheme.primary,
                                RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
                            )
                        )
                    }
                }
            }
            VSpacer(8)
            AddFab(onClick = onFabClick)
        }
    )
}