package dev.rezyfr.trackerr.utils
/*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun MyComponent() {
    var enabled = mutableStateOf(true) //

    var enabled by mutableStateOf(true)

    var enabled by remember { mutableStateOf(true) }

    var enabled = remember { mutableStateOf(true) }
}

/**
 * remember mengingat state terakhir
 */


@Composable
fun ButtonWithoutHoisting() {
    var enabled by remember { mutableStateOf(true) }

    Button(onClick = { enabled = !enabled }) {
        Text(text = if (enabled) "Disable" else "Enable")
    }
}



@Composable
fun ButtonHoisting(
    onClick: (Boolean) -> Unit,
    enabled: Boolean
) {
    Button(onClick = onClick) {
        Text(text = if (enabled) "Disable" else "Enable")
    }
}
















@Composable
fun ButtonWithHoisting() {
    var enabled by remember { mutableStateOf(true) }

    ButtonWithHoisting(enabled = enabled) {
        enabled = !enabled
    }
}

@Composable
fun ButtonWithHoisting(
    enabled: Boolean,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        enabled = enabled
    ) {
        Text(text = if (enabled) "Disable" else "Enable")
    }
}


data class ProductListFilterModel(
    val type: String,
    val name: String,
    val id: Int
)

enum class PengelolaanDanaType(val label: String) {
    All("Semua"),
    Conventional("Konvensional"),
    Syariah("Syariah")
}
val activeProductListFilterModel = listOf<ProductListFilterModel>(ProductListFilterModel("PENGELOLAANDANA", PengelolaanDanaType.All.label, 0))
val selectedCategoryID = 0

class MutualFundCategoryData {
    object CategoryID {
        const val All = 0
        const val Syariah = 1
    }
}


val newSelectedTypeId = activeProductListFilterModel.firstOrNull { it.type == "PENGELOLAANDANA" }?.name?.let {
    when (it) {
        PengelolaanDanaType.Conventional.label -> PengelolaanDanaType.Conventional.label
        PengelolaanDanaType.Syariah.label -> PengelolaanDanaType.Syariah.label
        else -> PengelolaanDanaType.All.label
    }
} ?: PengelolaanDanaType.All.label

val selectedTypeID = if (activeProductListFilterModel.any { filterList -> filterList.type == "PENGELOLAANDANA" }) {
    when (activeProductListFilterModel.first { it.type == "PENGELOLAANDANA" }.name) {
        PengelolaanDanaType.Conventional.label -> {
            PengelolaanDanaType.Conventional.label
        }
        PengelolaanDanaType.Syariah.label -> {
            PengelolaanDanaType.Syariah.label
        }
        else -> {
            PengelolaanDanaType.All.label
        }
    }
} else PengelolaanDanaType.All.label
fun something() {
    if (activeProductListFilterModel.size == 0 && selectedCategoryID == MutualFundCategoryData.CategoryID.Syariah) {
        selectedCategoryID = 0
        getProduct()
    }
} */