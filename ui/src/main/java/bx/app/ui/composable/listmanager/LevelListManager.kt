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
    val type: LevelListType
) : BaseListManager(items, context, modifier, searchText, onClick) {

    enum class LevelListType { Edit, Learn }

    @Composable
    override fun ItemRow(item: IdentifiedModel) {
        item as LevelModel

        Column(modifier = ModifierManager.paddingListItemRowModifier) {
            Row {
                // TODO: Need to add the name to the table later!
                //LargeText(text = item.name)
                Spacer(modifier = Modifier.weight(1f))

                if (type == LevelListType.Learn) {
                    LargeText(
                        text = "Dummy",
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    // TODO: Display the amount of cards the level contains
                }
            }
            // TODO: Display maybe the interval number and type
           // MediumText(text = item.description)
        }
    }
}