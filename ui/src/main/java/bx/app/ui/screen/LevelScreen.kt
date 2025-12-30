package bx.app.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import bx.app.data.enums.IntervalType
import bx.app.presentation.viewmodel.LevelViewModel
import bx.app.presentation.viewmodel.TopBarViewModel
import bx.app.ui.ModifierManager
import bx.app.ui.composable.LargeText
import bx.app.ui.composable.RadioButtonGroupDialog
import bx.app.ui.composable.SingleLineTextField

/**
 * This screen displays the content of a specific level
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun LevelScreen(
    levelViewModel: LevelViewModel,
    topBarViewModel: TopBarViewModel,
) {
    topBarViewModel.setTitle("Level")

    val level by levelViewModel.level.collectAsState()
    val options = listOf<String>("Week", "Month", "Year")

    Column(
        modifier = ModifierManager.paddingMostTopModifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        SingleLineTextField(
            modifier = ModifierManager.paddingTopModifier,
            valueText = if (level.name.isNotEmpty()) level.name else "",
            labelText = "Name",
            onValueChange = { levelViewModel.changeName(it) }
        )

        Row(
            modifier = ModifierManager.paddingTopModifier.padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            var openDialog by remember { mutableStateOf(false) }
            var intervalNumber by remember { mutableStateOf(level.intervalNumber.toString()) }
            LaunchedEffect(level.intervalNumber) { intervalNumber = level.intervalNumber.toString() }
            var isError by remember { mutableStateOf(intervalNumber.isEmpty()) }

            SingleLineTextField(
                modifier = Modifier.width(100.dp),
                valueText = intervalNumber,
                labelText = "Number",
                isError = isError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                    if (!it.all { it.isDigit() }) return@SingleLineTextField

                    val validInput = isInputValid(it, level.intervalType)
                    isError = (!validInput)

                    if (validInput) levelViewModel.changeIntervalNumber(it.toInt())
                    intervalNumber = it
                },
            )

            LargeText(" times a", modifier = Modifier.padding(horizontal = 10.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .background(MaterialTheme.colorScheme.tertiary)
                    .size(100.dp, 50.dp)
            ) {
                LargeText(
                    text = level.intervalType.asString(),
                    color = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier
                        .combinedClickable(onClick = { openDialog = !openDialog })
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                        .align(Alignment.Center)
                )
            }

            if (!openDialog) return
            RadioButtonGroupDialog(
                headerText = "Select interval type",
                selectedOption = level.intervalType.ordinal,
                optionList = options,
                onDismissRequest = { openDialog = false },
                onSelectOption = {
                    optionID, optionText ->
                    levelViewModel.changeIntervalType(IntervalType.valueOf(optionText.uppercase()))
                    intervalNumber = "1"
                    isError = (!isInputValid(intervalNumber, level.intervalType))
                    openDialog = false
                }
            )
        }
    }
}

private fun isInputValid(input: String, intervalType: IntervalType): Boolean {
    val number = if (input.isNotEmpty()) input.toInt() else 0
    return when (intervalType) {
        IntervalType.WEEK -> (number >= 1 && number <= 7)
        IntervalType.MONTH -> (number >= 1 && number <= 3)
        IntervalType.YEAR -> (number >= 1 && number <= 11)
    }
}