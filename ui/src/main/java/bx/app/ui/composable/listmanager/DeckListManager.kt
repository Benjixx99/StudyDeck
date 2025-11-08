package bx.app.ui.composable.listmanager

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bx.app.data.mock.item.BaseItem
import bx.app.data.mock.item.DeckItem
import bx.app.ui.composable.LargeText
import bx.app.ui.composable.MediumText
import bx.app.ui.ModifierManager

/**
 * It is used to list deck items
 */
internal class DeckListManager(
    items: List<DeckItem>,
    context: Context,
    modifier: Modifier,
    searchText: String,
    onClick: () -> Unit
) : BaseListManager(items, context, modifier, searchText, onClick) {

    @Composable
    override fun ItemRow(item: BaseItem) {
        item as DeckItem

        Column(modifier = ModifierManager.paddingListItemRowModifier) {
            Row {
                LargeText(text = item.name)
                Spacer(modifier = Modifier.weight(1f))
                LargeText(text = item.counter.toString(), modifier = Modifier.padding(end = 10.dp))
            }
            MediumText(
                text = item.description,
                maxLines = 1,
                //modifier = Modifier.background(if (item.isFavorite) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.background)
            )
        }
    }
}