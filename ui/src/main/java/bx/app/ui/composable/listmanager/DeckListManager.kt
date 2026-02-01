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
import bx.app.data.model.DeckModel
import bx.app.ui.composable.LargeText
import bx.app.ui.composable.MediumText
import bx.app.ui.ModifierManager

/**
 * It is used to list deck items
 */
internal class DeckListManager(
    items: List<DeckModel>,
    context: Context,
    modifier: Modifier,
    searchText: String,
    onClick: (id: Long) -> Unit,
    onSelect: (id: Long) -> Unit,
    selectedIds: Set<Long>,
    val cardsCountByDeckId: Map<Long, Int?>
) : BaseListManager(items, context, modifier, searchText, onClick, onSelect, selectedIds) {

    @Composable
    override fun ItemRow(item: IdentifiedModel) {
        item as DeckModel

        Column(ModifierManager.paddingListItemRowModifier) {
            Row {
                LargeText(text = item.name)
                Spacer(modifier = Modifier.weight(1f))
                LargeText(text = cardsCountByDeckId[item.id].toString(), modifier = Modifier.padding(end = 10.dp))
            }
            MediumText(
                text = item.description.toString(),
                maxLines = 1,
            )
        }
    }

    override fun displayItem(item: IdentifiedModel): Boolean {
        item as DeckModel
        return item.name.contains(searchText, true) || (item.description?.contains(searchText, true) == true)
    }
}