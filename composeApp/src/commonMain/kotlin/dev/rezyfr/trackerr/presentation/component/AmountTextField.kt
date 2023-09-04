package dev.rezyfr.trackerr.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import dev.rezyfr.trackerr.presentation.component.base.TransparentText
import dev.rezyfr.trackerr.presentation.component.util.PrefixTransformation


@Composable
fun AmountTextField(
    modifier: Modifier = Modifier,
    label: String,
    onValueChange: (TextFieldValue) -> Unit,
    value: TextFieldValue
) {
    Column(modifier) {
        val textStyles = MaterialTheme.typography.headlineLarge.copy(
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            label,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.65f)
        )
        BasicTextField(
            visualTransformation = PrefixTransformation("Rp"),
            value = value,
            decorationBox = { innerTextField ->
                if (value.text.isEmpty()) {
                    Row {
                        TransparentText(
                            "Rp",
                            style = textStyles,
                        )
                        Text(
                            "0",
                            style = textStyles.copy(
                                color = MaterialTheme.colorScheme.onPrimary.copy(
                                    alpha = 0.65f
                                )
                            ),
                        )
                    }
                }
                innerTextField()
            },
            onValueChange = onValueChange,
            textStyle = textStyles,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onPrimary),
        )
    }
}