package dev.rezyfr.trackerr.presentation.screens.create.transaction.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.rezyfr.trackerr.presentation.VSpacer
import dev.rezyfr.trackerr.presentation.component.base.ButtonText
import dev.rezyfr.trackerr.presentation.component.base.TrPrimaryButton


@Composable
fun FormTransactionSheet(
    modifier: Modifier = Modifier,
    label: String,
    onContinue: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    Column(
        modifier
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(
                    topStart = 32.dp, topEnd = 32.dp
                )
            )
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .scrollable(rememberScrollState(), Orientation.Vertical),
    ) {
        Text(
            label,
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground)
        )
        VSpacer(16)
        content()
        VSpacer(16)
        TrPrimaryButton(
            Modifier.fillMaxWidth(),
            text = { ButtonText("Continue") },
            onClick = onContinue
        )
    }
}