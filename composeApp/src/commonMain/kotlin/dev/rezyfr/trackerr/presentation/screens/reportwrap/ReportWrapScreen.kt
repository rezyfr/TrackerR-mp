package dev.rezyfr.trackerr.presentation.screens.reportwrap

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seiko.imageloader.rememberImagePainter
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.model.CategoryModel
import dev.rezyfr.trackerr.domain.model.transaction.TransactionReportModel
import dev.rezyfr.trackerr.presentation.HSpacer
import dev.rezyfr.trackerr.presentation.VSpacer
import dev.rezyfr.trackerr.presentation.component.util.format
import dev.rezyfr.trackerr.presentation.screens.reportwrap.store.ReportWrapStore
import dev.rezyfr.trackerr.presentation.theme.Dark100
import dev.rezyfr.trackerr.presentation.theme.Green100
import dev.rezyfr.trackerr.presentation.theme.Light40
import dev.rezyfr.trackerr.presentation.theme.Light80
import dev.rezyfr.trackerr.presentation.theme.Red100
import io.github.aakira.napier.Napier

@Composable
fun ReportWrapScreen(
    reportWrapComponent: ReportWrapComponent
) {
    val state by reportWrapComponent.state.collectAsState()

    ReportWrapContent(
        state = state,
        onAction = reportWrapComponent::onAction
    )
}
@Composable
private fun ReportWrapContent(
    state: ReportWrapStore.State,
    onAction: (ReportWrapComponent.Action) -> Unit
) {
    var index by remember { mutableStateOf(0) }

    Box(
        Modifier.fillMaxSize()
            .background(if (index == 0) Red100 else Green100)
    ) {
        if (state.report is UiResult.Success) {
            ReportContent(state.report.data.totalAmount, state.report.data.income)
        }
    }
}
@Composable
private fun BoxScope.ReportContent(
    totalAmount: Long,
    reportItem: TransactionReportModel.ReportItem,
) {
    Column(
        Modifier.align(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "You Spend \uD83D\uDCB8",
            style = MaterialTheme.typography.titleLarge.copy(color = Color.White)
        )
        VSpacer(16)
        Text(
            totalAmount.format(),
            style = MaterialTheme.typography.headlineLarge.copy(color = Color.White)
        )
    }
    ReportCategoryCard(
        Modifier.align(Alignment.BottomCenter),
        reportItem
    )
}
@Composable
private fun ReportCategoryCard(
    modifier: Modifier = Modifier,
    reportItem: TransactionReportModel.ReportItem,
) {
    if (reportItem.category == null) return
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 50.dp)
            .background(Color.White, RoundedCornerShape(24.dp))
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "and your biggest\nspending is from",
            style = MaterialTheme.typography.titleMedium.copy(color = Dark100),
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )
        VSpacer(14)
        Box(
            modifier = Modifier
                .background(Light80, RoundedCornerShape(24.dp))
                .border(1.dp, Light40, RoundedCornerShape(24.dp))
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val color = Color(reportItem.category.color)
                Image(
                    rememberImagePainter(reportItem.category.icon),
                    null,
                    modifier = Modifier.size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(color.copy(alpha = 0.2f))
                        .padding(4.dp),
                    colorFilter = ColorFilter.tint(color)
                )
                HSpacer(12)
                Text(reportItem.category.name, style = MaterialTheme.typography.titleSmall)
            }
        }
        Text(reportItem.categoryAmount.format(), style = MaterialTheme.typography.bodySmall.copy(
            fontSize = 36.sp
        ))
    }
}