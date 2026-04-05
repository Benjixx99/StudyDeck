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

/**
 * MainDropdownMenu has the items "Import" and "Export"
 */
@Composable
internal fun MainDropdownMenu(
    onClickImport: () -> Unit,
    onClickExport: () -> Unit,
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
            DropdownMenuItem(
                text = { Text("Import") },
                onClick = {
                    expanded = false
                    onClickImport()
                }
            )
            DropdownMenuItem(
                text = { Text("Export") },
                onClick = {
                    expanded = false
                    onClickExport()
                }
            )
        }
    }
}

/**
 *  DeckCardsDropdownMenu has the item "Sort by"
 */
@Composable
internal fun DeckCardsDropdownMenu(
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
            DropdownMenuItem(
                text = { Text("Sort by") },
                onClick = {
                    expanded = false
                    onClickSortBy()
                }
            )
        }
    }
}