package bx.app.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
    topBarViewModel: TopBarViewModel,
) {
    topBarViewModel.setTitle("Deck settings")
    val optionList = listOf(
        stringResource(R.string.fail_option_back_to_start),
        stringResource(R.string.fail_option_one_level_down),
        stringResource(R.string.fail_option_stays_on_current_level))

    Column(
        modifier = ModifierManager.paddingMostTopModifier.padding(horizontal = 10.dp)
    ) {
        SingleLineTextField(ModifierManager.paddingTopModifier, "Next text", "Name")
        MultiLineTextField(ModifierManager.paddingTopModifier, "Deck 2", "Description")
        SwitchTextRow(headerText = "Learn both sides", bodyText = "Description", modifier = ModifierManager.paddingTopModifier)
        ColorPickerRow(modifier = ModifierManager.paddingTopModifier)
        ClickableRow(text = "What happens by failing", optionList = optionList, modifier = ModifierManager.paddingTopModifier)
    }
}