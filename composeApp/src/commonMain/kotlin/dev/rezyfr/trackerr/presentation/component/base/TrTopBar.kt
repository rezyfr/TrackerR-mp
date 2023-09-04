package dev.rezyfr.trackerr.presentation.component.base

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrTopBar(
    text: String,
    color: TopAppBarColors = TrTopBarDefaults.whiteBarColor(),
    onBackPressed: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = { Text(text = text, style = MaterialTheme.typography.titleSmall) },
        navigationIcon = {
            IconButton(
                onClick = onBackPressed
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    null,
                    modifier = Modifier.padding(start = 16.dp),
                )
            }
        },
        actions = { },
        modifier = Modifier.fillMaxWidth(),
        colors = color
    )
}

@OptIn(ExperimentalMaterial3Api::class)
object TrTopBarDefaults {
    @Composable
    fun whiteBarColor() = TopAppBarDefaults.centerAlignedTopAppBarColors()

    @Composable
    fun primaryBarColor() = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        titleContentColor = MaterialTheme.colorScheme.onPrimary,
        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
    )
}