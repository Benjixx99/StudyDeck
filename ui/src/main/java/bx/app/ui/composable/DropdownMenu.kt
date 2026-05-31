package bx.app.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
private fun DropdownMenuItem(
    text: String,
    onClick: () -> Unit,
    closeMenu: () -> Unit
) {
    DropdownMenuItem(
        text = { Text(text) },
        onClick = {
            closeMenu()
            onClick()
        }
    )
}

/**
 * MainDropdownMenu has the items "Import" and "Export"
 */
@Composable
internal fun MainDropdownMenu(
    onClickImport: () -> Unit,
    onClickExport: () -> Unit,
    onClickSortBy: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More options"
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem("Sort by", onClick = onClickSortBy, closeMenu = { expanded = false })
            DropdownMenuItem("Import", onClick = onClickImport, closeMenu = { expanded = false })
            DropdownMenuItem("Export", onClick = onClickExport, closeMenu = { expanded = false })
        }
    }
}

/**
 *  DeckCardsDropdownMenu has the item "Sort by"
 */
@Composable
internal fun DeckCardsDropdownMenu(
    onClickSortBy: () -> Unit,
    onClickImportCards: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More options"
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem("Sort by", onClick = onClickSortBy, closeMenu = { expanded = false })
            DropdownMenuItem("Import text cards", onClick = onClickImportCards, closeMenu = { expanded = false })
        }
    }
}