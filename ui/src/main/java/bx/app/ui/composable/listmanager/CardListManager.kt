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
    val textById: Map<Long, String?>,
    val fileNameById: Map<Long, String?>,
) : BaseListManager(items, context, modifier, searchText, onClick) {

    @Composable
    override fun ItemRow(item: IdentifiedModel) {
        item as CardModel
        var isFront by remember { mutableStateOf(true) }

        Row(modifier = ModifierManager.paddingListItemRowModifier) {
            LargeText(
                text = if (isFront) getFrontValue(item) else getBackValue(item),
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

    private fun getFrontValue(item: CardModel): String {
        return (
            if (item.frontSideType == CardSideType.TEXT)
                textById[item.frontSideId]
            else
                fileNameById[item.frontSideId]
        ).toString()
    }

    private fun getBackValue(item: CardModel): String {
        return (
            if (item.backSideType == CardSideType.TEXT)
                textById[item.backSideId]
            else
                fileNameById[item.backSideId]
        ).toString()
    }

    override fun displayItem(item: IdentifiedModel): Boolean {
        item as CardModel
        val frontValue = getFrontValue(item)
        val backValue = getBackValue(item)
        return frontValue.contains(searchText, true) || backValue.contains(searchText, true)
    }
}