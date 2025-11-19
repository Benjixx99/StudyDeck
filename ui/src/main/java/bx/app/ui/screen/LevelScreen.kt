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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import bx.app.presentation.viewmodel.TopBarViewModel
import bx.app.ui.ModifierManager
import bx.app.ui.composable.LargeText
import bx.app.ui.composable.MediumText
import bx.app.ui.composable.RadioButtonGroupDialog
import bx.app.ui.composable.SingleLineTextField

/**
 * This screen displays the content of a specific level
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun LevelScreen(
    topBarViewModel: TopBarViewModel,
) {
    topBarViewModel.setTitle("Level")
    val options = listOf<String>("Week", "Month", "Year")

    Column(
        modifier = ModifierManager.paddingMostTopModifier.fillMaxWidth()
    ) {
        MediumText("Level", modifier = ModifierManager.paddingTopModifier)
        SingleLineTextField(valueText = "", labelText = "Name", modifier = ModifierManager.paddingTopModifier)

        Row(
            modifier = ModifierManager.paddingTopModifier.padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            var number by remember { mutableStateOf("1") }
            var openDialog by remember { mutableStateOf(false) }
            var selectedOptionText by remember { mutableStateOf("Week") }
            var selectedOptionID by remember { mutableIntStateOf(0) }

            TextField(
                value = number,
                onValueChange = { input -> if (input.all { it.isDigit() }) number = input },
                placeholder = { Text("Number") },
                singleLine = true,
                maxLines = 1,
                isError = (!number.all { it.isDigit() }),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                modifier = Modifier.width(100.dp),
            )

            //val hexagon = remember { RoundedPolygon(4, rounding = CornerRounding(0.2f)) }
            //val clip = remember(hexagon) { RoundedPolygonShape(polygon = hexagon) }

            LargeText(" times a", modifier = Modifier.padding(horizontal = 10.dp))

            Box(
                modifier = Modifier.clip(RoundedCornerShape(5.dp)).background(MaterialTheme.colorScheme.tertiary).size(100.dp, 50.dp)
            ) {
                LargeText(
                    text = selectedOptionText,
                    color = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier
                        .combinedClickable(onClick = { openDialog = !openDialog })
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                        .align(Alignment.Center)
                )
            }
            if (openDialog) {
                RadioButtonGroupDialog(
                    headerText = "",
                    selectedOption = selectedOptionID,
                    optionList = options,
                    onDismissRequest = { openDialog = false },
                    onSelectOption = {
                            optionID, optionText ->
                        selectedOptionText = optionText
                        selectedOptionID = optionID
                        openDialog = false
                    }
                )
            }
        }
    }
}