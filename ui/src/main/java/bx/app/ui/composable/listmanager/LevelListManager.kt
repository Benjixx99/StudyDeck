package bx.app.ui.composable.listmanager

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bx.app.data.model.IdentifiedModel
import bx.app.data.model.LevelModel
import bx.app.ui.composable.LargeText
import bx.app.ui.ModifierManager

/**
 * It is used to list level items
 */
internal class LevelListManager(
    items: List<IdentifiedModel>,
    context: Context,
    modifier: Modifier,
    searchText: String,
    onClick: (id: Long) -> Unit,
    onSelect: (id: Long) -> Unit = {},
    selectedIds: Set<Long> = emptySet<Long>(),
    val type: LevelListType
) : BaseListManager(items, context, modifier, searchText, onClick, onSelect, selectedIds) {

    enum class LevelListType { Edit, Learn }

    @Composable
    override fun ItemRow(item: IdentifiedModel) {
        item as LevelModel

        Column(modifier = ModifierManager.paddingListItemRowModifier) {
            Row {
                LargeText(text = item.name)
                Spacer(modifier = Modifier.weight(1f))

                if (type == LevelListType.Learn) {
                    LargeText(
                        text = "is coming soon", // TODO: Display the amount of cards the level contains
                        modifier = Modifier.padding(end = 10.dp)
                    )
                }
            }
        }
    }

    override fun displayItem(item: IdentifiedModel): Boolean {
        item as LevelModel
        return item.name.contains(searchText, true)
    }
}