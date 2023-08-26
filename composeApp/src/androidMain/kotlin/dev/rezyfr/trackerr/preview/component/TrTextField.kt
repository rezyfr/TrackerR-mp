package dev.rezyfr.trackerr.preview.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.rezyfr.trackerr.presentation.component.TrTextField
import dev.rezyfr.trackerr.presentation.theme.AppTheme

@Preview(showBackground = true)
@Composable
fun PreviewTrxTextField() {
    AppTheme {
        Column(Modifier.padding(16.dp)) {
            TrTextField(
                placeholder = "Category"
            )
            Spacer(modifier = Modifier.padding(8.dp))
            TrTextField(
                placeholder = "Category",
                trailingIcon = Icons.Outlined.Visibility
            )
            Spacer(modifier = Modifier.padding(8.dp))
            TrTextField(
                value = "Category"
            )
        }
    }
}