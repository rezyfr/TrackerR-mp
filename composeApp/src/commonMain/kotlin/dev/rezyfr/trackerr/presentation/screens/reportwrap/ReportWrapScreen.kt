package dev.rezyfr.trackerr.presentation.screens.reportwrap

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seiko.imageloader.rememberImagePainter
import dev.rezyfr.trackerr.domain.UiResult
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
import kotlinx.datetime.Clock

private const val STORY_MAX_PROGRESS = 1f

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
    Box(Modifier.fillMaxSize().background(if (index == 0) Red100 else Green100)) {
        if (state.report is UiResult.Success) {
            ReportContent(
                totalAmount = state.report.data.totalAmount,
                reportItem = state.report.data.income,
                onStoryProgressFinish = { index++ },
                currentIndex = index,
                onStoryFinish = { onAction(ReportWrapComponent.Action.NavigateBack) }
            )
        }
    }
}
@Composable
private fun BoxScope.ReportContent(
    totalAmount: Long,
    reportItem: TransactionReportModel.ReportItem,
    currentIndex: Int,
    onStoryProgressFinish: () -> Unit = {},
    onStoryFinish: (ReportWrapComponent.Action) -> Unit
) {
    Row(
        Modifier.align(Alignment.TopCenter)
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        StoryProgress(
            onStoryProgressFinish = onStoryProgressFinish,
            currentIndex = currentIndex,
            modifier = Modifier.weight(1f),
            steps = 2,
            onStoryFinish = onStoryFinish
        )
    }
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
fun StoryProgress(
    modifier: Modifier = Modifier,
    onStoryProgressFinish: () -> Unit = {},
    currentIndex: Int,
    steps: Int,
    onStoryFinish: (ReportWrapComponent.Action) -> Unit
) {
    val progress = remember { Animatable(0f) }
    repeat(steps) {
        StoryProgressBar(
            modifier,
            progress = when {
                currentIndex == it -> progress.value
                it <= currentIndex -> STORY_MAX_PROGRESS
                else -> 0f
            }
        )
    }

    LaunchedEffect(currentIndex) {
        if (currentIndex < steps) {
            progress.animateTo(STORY_MAX_PROGRESS, animationSpec = tween(3000, easing = LinearEasing))
        }
        if (currentIndex < steps) {
            onStoryProgressFinish()
        }
        if (progress.value == STORY_MAX_PROGRESS && currentIndex < steps-1) {
            progress.snapTo(0f)
        }
        if (progress.value == STORY_MAX_PROGRESS && currentIndex == steps-1) {
            onStoryFinish(ReportWrapComponent.Action.NavigateBack)
        }
    }
}

@Composable
fun StoryProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
) {
    LinearProgressIndicator(
        progress = progress,
        modifier = modifier.height(4.dp),
        color = MaterialTheme.colorScheme.background,
        trackColor = MaterialTheme.colorScheme.background.copy(alpha = 0.1f),
        strokeCap = StrokeCap.Round
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
        Text(
            reportItem.categoryAmount.format(), style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 36.sp
            )
        )
    }
}