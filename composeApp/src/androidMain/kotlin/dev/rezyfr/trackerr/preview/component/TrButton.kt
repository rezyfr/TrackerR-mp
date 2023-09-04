package dev.rezyfr.trackerr.preview.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.rezyfr.trackerr.presentation.component.base.ButtonText
import dev.rezyfr.trackerr.presentation.component.base.TrDangerButton
import dev.rezyfr.trackerr.presentation.component.base.TrOutlinedButton
import dev.rezyfr.trackerr.presentation.component.base.TrPrimaryButton
import dev.rezyfr.trackerr.presentation.theme.AppTheme


@Preview(showBackground = true)
@Composable
private fun PreviewButton() {
    AppTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            TrPrimaryButton(
                text = { ButtonText("Primary Button") },
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
