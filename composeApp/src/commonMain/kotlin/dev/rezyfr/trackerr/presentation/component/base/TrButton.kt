package dev.rezyfr.trackerr.presentation.component.base

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun TrOutlinedButton(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = TrButtonDefaults.padding(),
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    text: @Composable (() -> Unit),
    onClick: () -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = TrButtonDefaults.outlinedColors(),
        contentPadding = contentPadding,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
    ) {
        TrButtonContent(
            text = text,
            leadingIcon = { leadingIcon?.invoke() }
        )
    }
}

@Composable
fun TrDangerButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    TrButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = TrButtonDefaults.dangerColors(),
        text = { ButtonText(text = text) },
        leadingIcon = leadingIcon
    )
}

@Composable
fun TrPrimaryButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable (() -> Unit),
    leadingIcon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit,
) {
    TrButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = TrButtonDefaults.primaryColors(),
        text = text,
        leadingIcon = leadingIcon
    )
}

@Composable
fun TrSecondaryButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit,
) {
    TrButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = TrButtonDefaults.secondaryColors(),
        text = { SecondaryButtonText(text = text) },
        leadingIcon = leadingIcon
    )
}

@Composable
fun TrCapsuleButton(
    text: String,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.primary,
    border: BorderStroke? = null,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Surface(
        onClick = onClick,
        modifier = modifier.semantics { role = Role.Button },
        shape = RoundedCornerShape(50),
        color = containerColor,
        contentColor = contentColor,
        border = border
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            ProvideTextStyle(value = MaterialTheme.typography.labelLarge) {
                Row(
                    Modifier.padding(PaddingValues(horizontal = 16.dp, vertical = 4.dp)),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        TrButtonContent(
                            text = {
                                Text(
                                    text = text, style = MaterialTheme.typography.bodySmall
                                )
                            },
                        )
                    }
                )
            }
        }
    }
}

/**
 * TrackerR filled button with text and icon content slots.
 *
 * @param onClick Will be called when the user clicks the button.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param text The button text label content.
 * @param leadingIcon The button leading icon content. Pass `null` here for no leading icon.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    colors: ButtonColors,
    leadingIcon: @Composable (() -> Unit)? = null,
    padding: PaddingValues = TrButtonDefaults.padding(),
    shape: RoundedCornerShape = RoundedCornerShape(16.dp)
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = if (leadingIcon != null) {
            TrButtonDefaults.paddingWithStartIcon()
        } else {
            padding
        },
        colors = colors,
        shape = shape
    ) {
        TrButtonContent(
            text = text,
            leadingIcon = leadingIcon
        )
    }
}

/**
 * Internal TrackerR button content layout for arranging the text label and leading icon.
 *
 * @param text The button text label content.
 * @param leadingIcon The button leading icon content. Default is `null` for no leading icon.Ï
 */
@Composable
private fun TrButtonContent(
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    if (leadingIcon != null) {
        Box(Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize)) {
            leadingIcon()
        }
    }
    Box(
        Modifier
            .padding(
                start = if (leadingIcon != null) {
                    ButtonDefaults.IconSpacing
                } else {
                    0.dp
                }
            )
    ) {
        text()
    }
}