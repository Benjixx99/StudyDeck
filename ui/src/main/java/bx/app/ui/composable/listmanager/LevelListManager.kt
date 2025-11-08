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
import bx.app.data.mock.item.LevelItem
import bx.app.ui.composable.LargeText
import bx.app.ui.composable.MediumText
import bx.app.ui.ModifierManager

/**
 * It is used to list level items
 */
internal class LevelListManager(
    items: List<LevelItem>,
    context: Context,
    modifier: Modifier,
    searchText: String,
    onClick: () -> Unit,
    val type: LevelListType
) : BaseListManager(items, context, modifier, searchText, onClick) {

    enum class LevelListType { Edit, Learn }

    @Composable
    override fun ItemRow(item: BaseItem) {
        item as LevelItem

        Column(modifier = ModifierManager.paddingListItemRowModifier) {
            Row {
                LargeText(text = item.name)
                Spacer(modifier = Modifier.weight(1f))

                if (type == LevelListType.Learn) {
                    LargeText(
                        text = item.position.toString(),
                        modifier = Modifier.padding(end = 10.dp)
                    )
                }
            }
            MediumText(text = item.description)
        }
    }
}