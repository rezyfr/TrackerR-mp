package dev.rezyfr.trackerr.presentation.component
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TrOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = TrButtonDefaults.padding(),
    enabled: Boolean = true,
    text: String,
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
            text = { OutlinedButtonText(text) },
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
@Composable
fun TrButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    colors: ButtonColors,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = if (leadingIcon != null) {
            TrButtonDefaults.paddingWithStartIcon()
        } else {
            TrButtonDefaults.padding()
        },
        colors = colors,
        shape = RoundedCornerShape(16.dp)
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