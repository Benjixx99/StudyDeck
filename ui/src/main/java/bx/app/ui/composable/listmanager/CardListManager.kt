package bx.app.ui.composable.listmanager

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bx.app.data.enums.CardSideType
import bx.app.data.model.IdentifiedModel
import bx.app.data.model.CardModel
import bx.app.ui.composable.LargeText
import bx.app.ui.ModifierManager

/**
 * It is used to list card items
 */
internal class CardListManager(
    items: List<CardModel>,
    context: Context,
    modifier: Modifier,
    searchText: String,
    onClick: (id: Long) -> Unit,
    onSelect: (id: Long) -> Unit,
    selectedIds: Set<Long>,
) : BaseListManager(items, context, modifier, searchText, onClick, onSelect, selectedIds) {

    @Composable
    override fun ItemRow(item: IdentifiedModel) {
        item as CardModel
        var isFront by remember { mutableStateOf(true) }

        Row(modifier = ModifierManager.paddingListItemRowModifier) {
            LargeText(
                text = if (isFront) item.frontText else item.backText,
                maxLines = 2,
                modifier = Modifier.weight(6.0f)
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { isFront = !isFront },
                colors = ButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary,
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContentColor = MaterialTheme.colorScheme.secondary,
                    disabledContainerColor = MaterialTheme.colorScheme.onSecondary
                ),
                modifier = Modifier.padding(end = 10.dp)
            ) {
                Text(text = if (isFront) "Back" else "Front")
            }
        }
    }

    override fun displayItem(item: IdentifiedModel): Boolean {
        item as CardModel
        return item.frontText.contains(searchText, true) || item.backText.contains(searchText, true)
    }
}