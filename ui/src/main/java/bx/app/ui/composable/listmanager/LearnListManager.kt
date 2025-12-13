package bx.app.ui.composable.listmanager

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import bx.app.data.model.IdentifiedModel
import bx.app.data.model.LearnModel
import bx.app.ui.composable.LargeText
import bx.app.ui.composable.MediumText
import bx.app.ui.ModifierManager

/**
 * It is used to list learn items
 */
internal class LearnListManager(
    items: List<IdentifiedModel>,
    context: Context,
    modifier: Modifier,
    searchText: String,
    val onLearnClick: (id: Long) -> Unit
) : BaseListManager(items, context, modifier, searchText, {}) {

    @Composable
    override fun List() { ItemList { item -> ItemColumn(item) } }

    @Composable
    override fun ItemColumn(item: IdentifiedModel) {
        item as LearnModel

        Column(
            ModifierManager.listColumnModifier
                .border(1.dp, MaterialTheme.colorScheme.outlineVariant)
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .pointerInput(Unit) { detectTapGestures(onTap = { onLearnClick(item.id) }) }
        ) {
            ItemRow(item)
        }
    }

    @Composable
    override fun ItemRow(item: IdentifiedModel) {
        item as LearnModel

        Column(modifier = ModifierManager.paddingListItemRowModifier) {
            Row {
                LargeText(text = item.name)
                Spacer(modifier = Modifier.weight(1f))
            }
            MediumText(text = item.description)
        }
    }

    override fun displayItem(item: IdentifiedModel): Boolean { return true }
}