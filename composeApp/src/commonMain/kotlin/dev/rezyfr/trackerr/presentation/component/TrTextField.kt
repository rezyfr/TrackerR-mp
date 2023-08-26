package dev.rezyfr.trackerr.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.rezyfr.trackerr.presentation.theme.Light20

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrTextField(
    modifier: Modifier = Modifier,
    value: String? = "",
    placeholder: String = "",
    onValueChange: (String) -> Unit = {},
    trailingIcon: ImageVector? = null,
    onClick: (() -> Unit)? = null,
) {
    OutlinedTextField(
        value = value.orEmpty(),
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Light20
                ),
                textAlign = TextAlign.Center
            )
        },
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onBackground
        ),
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) Modifier.clickable(onClick = onClick)
                else Modifier
            ),
        keyboardOptions = KeyboardOptions.Default,
        keyboardActions = KeyboardActions(onDone = { }),
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
            unfocusedBorderColor = MaterialTheme.colorScheme.outline
        ),
        trailingIcon = {
            if (trailingIcon != null) {
                Icon(
                    trailingIcon,
                    contentDescription = "trailing_icon"
                )
            }
        },
        enabled = onClick == null
    )
}