package bx.app.ui.composable.listmanager

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import bx.app.data.model.IdentifiedModel
import bx.app.ui.ModifierManager

/**
 * ListManager can be used to display a list
 */
internal abstract class BaseListManager(
    val items: List<IdentifiedModel>,
    val context: Context,
    val modifier: Modifier,
    val searchText: String,
    val onClick: (id: Long) -> Unit
) {
    @Composable
    open fun List() { ItemList { item -> ItemColumn(item) } }

    @Composable
    protected fun ItemList(content: @Composable (IdentifiedModel) -> Unit) {
        Box(modifier = modifier.padding(horizontal = 10.dp).padding(bottom = 40.dp)) {
            LazyColumn {
                items(items) { item -> content(item) }
            }
        }
    }

    @Composable
    protected open fun ItemColumn(item: IdentifiedModel) {
        if (!displayItem(item)) return

        var expandDropDownMenu by remember { mutableStateOf(false) }
        var posInParent by remember { mutableStateOf(Offset.Zero) }

        Column(
            ModifierManager.listColumnModifier
                .border(1.dp, MaterialTheme.colorScheme.outlineVariant)
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = { expandDropDownMenu = !expandDropDownMenu },
                        onTap = { onClick(item.id) }
                    )
                }
                .onGloballyPositioned { coordinates -> posInParent = coordinates.positionInParent() }
        ) {
            ItemRow(item)
        }
        // TODO: Just a test
        DropdownMenu(
            expanded = expandDropDownMenu,
            onDismissRequest = { expandDropDownMenu = false },
            offset = LocalDensity.current.run { DpOffset(posInParent.x.toDp(), posInParent.y.toDp() + 100.dp) }
        ) {
            DropdownMenuItem(
                text = { Text("Delete") },
                onClick = { }
            )
            DropdownMenuItem(
                text = { Text("Select") },
                onClick = { }
            )
        }
    }

    @Composable
    protected abstract fun ItemRow(item: IdentifiedModel)

    /**
     * This function is used to filter the items with the search text the user entered
     */
    protected abstract fun displayItem(item: IdentifiedModel): Boolean
}