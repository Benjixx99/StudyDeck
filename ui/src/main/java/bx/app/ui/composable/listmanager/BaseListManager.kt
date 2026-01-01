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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import bx.app.data.model.IdentifiedModel
import bx.app.ui.ModifierManager

/**
 * ListManager can be used to display a list
 */
internal abstract class BaseListManager(
    protected val items: List<IdentifiedModel>,
    protected val context: Context,
    protected val modifier: Modifier,
    protected val searchText: String,
    protected val onClick: (id: Long) -> Unit,
    protected val onSelect: (item: Long) -> Unit = {},
    protected val selectedIds: Set<Long> = emptySet<Long>()
) {
    @Composable
    open fun List() { ItemList { item -> ItemColumn(item) } }

    @Composable
    protected fun ItemList(content: @Composable (IdentifiedModel) -> Unit) {
        Box(modifier = modifier.padding(horizontal = 10.dp).padding(bottom = 40.dp)) {
            LazyColumn {
                items(items, key = { it.id }) { item -> content(item) }
            }
        }
    }

    @Composable
    protected open fun ItemColumn(item: IdentifiedModel) {
        if (!displayItem(item)) return
        val isSelected = item.id in selectedIds

        Column(
            ModifierManager.listColumnModifier
                .border(1.dp, MaterialTheme.colorScheme.outlineVariant)
                .background(
                    if (isSelected) MaterialTheme.colorScheme.secondaryContainer
                    else MaterialTheme.colorScheme.surfaceContainerHigh)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = { onSelect(item.id) },
                        onTap = { if (selectedIds.isNotEmpty()) onSelect(item.id) else onClick(item.id) }
                    )
                }
        ) {
            ItemRow(item)
        }
    }

    @Composable
    protected abstract fun ItemRow(item: IdentifiedModel)

    /**
     * This function is used to filter the items with the search text the user entered
     */
    protected abstract fun displayItem(item: IdentifiedModel): Boolean
}