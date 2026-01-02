package bx.app.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import bx.app.data.enums.CardFailing
import bx.app.presentation.data.IdValidator
import bx.app.presentation.viewmodel.DeckViewModel
import bx.app.presentation.viewmodel.HideNavigationBarViewModel
import bx.app.presentation.viewmodel.TopBarViewModel
import bx.app.ui.ModifierManager
import bx.app.ui.R
import bx.app.ui.composable.ClickableRow
import bx.app.ui.composable.ColorPickerRow
import bx.app.ui.composable.MultiLineTextField
import bx.app.ui.composable.SingleLineTextField
import bx.app.ui.composable.SwitchTextRow

/**
 * This screen displays the settings that can be configured by the user
 */
@Composable
internal fun DeckSettingsScreen(
    context: Context,
    deckViewModel: DeckViewModel,
    topBarViewModel: TopBarViewModel,
    hideNavigationBarViewModel: HideNavigationBarViewModel,
) {
    val deck by deckViewModel.deck.collectAsState()
    if (deck.id >= IdValidator.MIN_VALID_ID) hideNavigationBarViewModel.setHide(false)

    topBarViewModel.setTitle("Deck settings")
    val optionList = listOf(
        stringResource(R.string.fail_option_back_to_start),
        stringResource(R.string.fail_option_one_level_down),
        stringResource(R.string.fail_option_stays_on_current_level))

    Column(
        modifier = ModifierManager.paddingMostTopModifier.padding(horizontal = 10.dp)
    ) {
        SingleLineTextField(
            modifier = ModifierManager.paddingTopModifier,
            valueText = if (!deck.name.isEmpty()) deck.name else "Name",
            labelText = "Name",
            onValueChange = {
                if (it.isEmpty()) {
                    deckViewModel.changeName(deck.name)
                    Toast.makeText(context, "Name is missing!", Toast.LENGTH_SHORT).show()
                }
                else {
                    deckViewModel.changeName(it)
                }
            },
        )
        MultiLineTextField(
            modifier = ModifierManager.paddingTopModifier,
            valueText = if (!deck.description.isNullOrEmpty()) deck.description.toString() else "",
            labelText = "Description",
            onValueChange = { deckViewModel.changeDescription(it) }
        )
        SwitchTextRow(
            headerText = "Learn both sides",
            bodyText = "When enabled you learn both sides of the card",
            modifier = ModifierManager.paddingTopModifier,
            isChecked = deck.learnBothSides,
            onCheckedChange = { deckViewModel.changeLearnBothSides(it) }
        )
        ColorPickerRow(
            modifier = ModifierManager.paddingTopModifier,
            color = Color(deck.color),
            onColorChanged = { deckViewModel.changeColor(it) }
        )
        ClickableRow(
            text = "What happens by failing",
            optionList = optionList,
            modifier = ModifierManager.paddingTopModifier,
            selectedOptionId = deck.onFailing.ordinal,
            onOptionChanged = { deckViewModel.changeOnFailing(CardFailing.entries[it]) }
        )
    }
}