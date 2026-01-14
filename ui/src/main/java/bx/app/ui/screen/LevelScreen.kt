package bx.app.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import bx.app.data.enums.IntervalType
import bx.app.presentation.viewmodel.LevelViewModel
import bx.app.presentation.viewmodel.TopBarViewModel
import bx.app.ui.ModifierManager
import bx.app.ui.composable.ConfirmationDialog
import bx.app.ui.composable.HeaderWithTextFieldAndButtonRow
import bx.app.ui.composable.RadioButtonGroupDialog
import bx.app.ui.composable.SingleLineTextField

/**
 * This screen displays the content of a specific level
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun LevelScreen(
    levelViewModel: LevelViewModel,
    navHostController: NavHostController,
    topBarViewModel: TopBarViewModel,
    isInsert: Boolean,
    deleteLevel: (id: Long) -> Unit,
) {
    topBarViewModel.setTitle("Level")

    val intervalExists by levelViewModel.intervalExists.collectAsState()
    val level by levelViewModel.level.collectAsState()
    var showExitDialog by remember { mutableStateOf(false) }
    val options = listOf<String>("Day", "Week", "Month", "Year")

    BackHandler {
        if (intervalExists) showExitDialog = true else navHostController.popBackStack()
    }
    ConfirmationDialog(
        isVisible = showExitDialog,
        message = "Interval already exists! \nDiscard changes?",
        onConfirm = {
            deleteLevel(level.id)
            showExitDialog = false
            navHostController.popBackStack()
        },
        onDismiss = { showExitDialog = false }
    )

    Column(
        modifier = ModifierManager.paddingMostTopModifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        var openDialog by remember { mutableStateOf(false) }
        var intervalNumber by remember { mutableStateOf(level.intervalNumber.toString()) }
        LaunchedEffect(level.intervalNumber) { intervalNumber = level.intervalNumber.toString() }
        var isError by remember { mutableStateOf(intervalNumber.isEmpty()) }

        SingleLineTextField(
            modifier = ModifierManager.paddingTopModifier,
            valueText = if (level.name.isNotEmpty()) level.name else "",
            labelText = "Name",
            onValueChange = {
                levelViewModel.changeName(it)
                if (isInsert) {
                    levelViewModel.existsByInterval(level.intervalNumber, level.intervalType)
                }
            }
        )

        Spacer(modifier = Modifier.padding(vertical = 10.dp))

        HeaderWithTextFieldAndButtonRow(
            headerText = "Space between learn units",
            textFieldValue = intervalNumber,
            textFieldReadOnly = !isInsert,
            textFieldIsError = isError,
            textFieldKeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {
                if (!it.all { it.isDigit() }) return@HeaderWithTextFieldAndButtonRow

                val validInput = isInputValid(it)
                isError = (!validInput)

                if (validInput) {
                    levelViewModel.changeIntervalNumber(it.toInt())
                    levelViewModel.existsByInterval(it.toInt(), level.intervalType)
                }
                intervalNumber = it
            },
            buttonText = level.intervalType.asString(),
            onButtonClick = { openDialog = !openDialog }
        )

        if (!openDialog || !isInsert) return
        RadioButtonGroupDialog(
            headerText = "Select interval type",
            selectedOption = level.intervalType.ordinal,
            optionList = options,
            onDismissRequest = { openDialog = false },
            onSelectOption = {
                optionID, optionText ->
                val intervalType = IntervalType.valueOf(optionText.uppercase())
                levelViewModel.changeIntervalType(intervalType)
                levelViewModel.existsByInterval(level.intervalNumber, intervalType)
                intervalNumber = "1"
                isError = (!isInputValid(intervalNumber))
                openDialog = false
            }
        )
    }
}

private fun isInputValid(input: String): Boolean {
    val number = if (input.isNotEmpty()) input.toInt() else 0
    return (number >= 1 && number <= 99)
}