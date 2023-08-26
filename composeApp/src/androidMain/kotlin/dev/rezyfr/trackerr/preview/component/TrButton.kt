package dev.rezyfr.trackerr.preview.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.rezyfr.trackerr.presentation.component.TrDangerButton
import dev.rezyfr.trackerr.presentation.component.TrOutlinedButton
import dev.rezyfr.trackerr.presentation.component.TrPrimaryButton
import dev.rezyfr.trackerr.presentation.theme.AppTheme


@Preview(showBackground = true)
@Composable
private fun PreviewButton() {
    AppTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            TrPrimaryButton(
                text = "Primary Button",
                onClick = {}
            )
            TrDangerButton(
                text = "Danger Button",
                onClick = {}
            )
            TrOutlinedButton(
                text = "Outlined Button",
                onClick = {}
            )
        }
    }
}
