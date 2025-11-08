package bx.app.ui.screen

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import bx.app.core.maxLength
import bx.app.data.mock.MockData
import bx.app.data.mock.item.CardSide.CardType
import bx.app.data.mock.item.BaseItem
import bx.app.presentation.viewmodel.TopBarViewModel
import bx.app.ui.composable.AudioPlayer
import bx.app.ui.composable.ButtonInCorner
import bx.app.ui.composable.listmanager.CardListManager
import bx.app.ui.composable.ClickableRow
import bx.app.ui.composable.ColorPickerRow
import bx.app.ui.composable.listmanager.DeckListManager
import bx.app.ui.composable.LargeText
import bx.app.ui.composable.listmanager.LearnListManager
import bx.app.ui.composable.listmanager.LevelListManager
import bx.app.ui.composable.MediumText
import bx.app.ui.composable.MultiLineTextField
import bx.app.ui.composable.RadioButtonGroupDialog
import bx.app.ui.composable.SearchBar
import bx.app.ui.composable.SingleLineTextField
import bx.app.ui.composable.SwitchTextRow
import bx.app.ui.composable.cardTypeSegmentedControl
import bx.app.ui.data.LearnData
import bx.app.ui.ModifierManager
import bx.app.ui.R
import bx.app.ui.composable.listmanager.LevelListManager.LevelListType
import bx.app.ui.getFileNameFromUri

// TODO: I have to spit it into a base class and sub classes
/**
 * Contains all the screens for the app
 */
class ScreenManager(modifier: Modifier = Modifier, context: Context, private val topBarViewModel: TopBarViewModel
) {
    private var myModifier: Modifier = modifier
    private var myContext: Context = context

    /**
     * This screen displays a list of all decks that the user created
     */
    @Composable
    fun Decks(
        context: Context = myContext,
        onClickCreateNewDeck: () -> Unit = {},
        onClickDeck: () -> Unit = {},
    ) {
        topBarViewModel.setTitle("Decks")

        Column(
            modifier = ModifierManager.paddingMostTopModifier
        ) {
            //ShowDataFromDatabase(DatabaseProvider.getDatabase(context).userDao())
            val searchText = SearchBar()
            val deckListManager = DeckListManager(
                items = MockData.deckItems,
                context = context,
                modifier = ModifierManager.paddingTopModifier.fillMaxSize().padding(bottom = 10.dp),
                searchText = searchText,
                onClick = onClickDeck
            )
            deckListManager.List()
        }
        ButtonInCorner(onClickCreateNewDeck)
    }

    /**
     * This screen displays a list of all cards that a deck contains
     */
    @Composable
    fun DeckCards(
        context: Context = myContext,
        onClickCreateNewCard: () -> Unit = {},
        onClickCard: () -> Unit = {},
    ) {
        topBarViewModel.setTitle("Cards")

        Column(
            modifier = ModifierManager.paddingMostTopModifier
        ) {
            val searchText = SearchBar()
            val cardListManager = CardListManager(
                items = MockData.cardItems,
                context = context,
                modifier = ModifierManager.paddingTopModifier.fillMaxSize().padding(bottom = 40.dp),
                searchText = searchText,
                onClick = onClickCreateNewCard,
            )
            cardListManager.List()
        }
        ButtonInCorner(onClickCreateNewCard)
    }

    /**
     * This screen displays the settings that can be configured by the user
     */
    @Composable
    fun DeckSettings(
        context: Context = myContext,
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

    /**
     * This screen displays a list of all levels that a deck contains
     */
    @Composable
    fun DeckLevels(
        context: Context = myContext,
        onClickCreateNewLevel: () -> Unit = {},
        onClickLevel: () -> Unit = {},
    ) {
        topBarViewModel.setTitle("Levels")

        Column(
            modifier = ModifierManager.paddingMostTopModifier
        ) {
            val searchText = SearchBar()
            val levelListManager = LevelListManager(
                items = MockData.levelItems,
                context = context,
                modifier = ModifierManager.paddingTopModifier.fillMaxSize().padding(bottom = 40.dp),
                searchText = searchText,
                onClick = onClickLevel,
                type = LevelListType.Edit
            )
            levelListManager.List()
        }
        ButtonInCorner(onClickCreateNewLevel)
    }

    /**
     * This screen displays the learn options that are available
     */
    @Composable
    fun DeckLearn(
        context: Context = myContext,
        onClickLearn: (id: Int) -> Unit = {},
    ) {
        topBarViewModel.setTitle("Learning")

        // TODO: Move this list to a own file
        val items = listOf<BaseItem>(
            BaseItem(
                LearnData.RANDOM_ID,
                stringResource(R.string.random_learn_name),
                stringResource(R.string.random_learn_description)
            ),
            BaseItem(
                LearnData.LEVEL_ID,
                stringResource(R.string.level_learn_name),
                stringResource(R.string.level_learn_description)
            ),
        )
        Column(
            ModifierManager.paddingMostTopModifier
        ) {
            val learnListManager = LearnListManager(
                context = context,
                items = items,
                modifier = Modifier,
                searchText = "",
                onLearnClick = onClickLearn
            )
            learnListManager.List()
        }
    }

    /**
     * This screen displays the content of a specific card
     */
    @Composable
    fun Card(
        context: Context = myContext
    ) {
        topBarViewModel.setTitle("Card")

        Column(
            modifier = ModifierManager.paddingMostTopModifier.fillMaxSize(),
            //horizontalAlignment = Alignment.CenterHorizontally,
            //verticalArrangement = Arrangement.SpaceBetween
        ) {
            var cardType = cardTypeSegmentedControl()

            // Text Type
            if (cardType == CardType.Text) {
                MultiLineTextField(
                    modifier = ModifierManager.paddingTopModifier.fillMaxSize().padding(start = 10.dp, end = 10.dp, bottom = 100.dp),
                    labelText = "Text to learn",
                    maxLines = 5 // TODO: Need to calculate the right amount and find out how to manage the right behaviour if the text hits the keyboard
                )
            }

            // Audio Type
            // Select audio file
            if (cardType == CardType.Audio) {
                // TODO: Need to write a function like AudioPlayerRow
                var audioFile by remember { mutableStateOf<Uri?>(null) }
                var mediaPlayer = if (audioFile != null) MediaPlayer.create(context, audioFile) else null
                val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { audioFile = it }

                Row (
                    modifier = ModifierManager.paddingTopModifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 10.dp)
                        .border(1.dp, MaterialTheme.colorScheme.outline)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    if (audioFile == null) { launcher.launch(arrayOf("audio/*")) }
                                }
                            )
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AudioPlayer(mediaPlayer, audioFile)
                    LargeText(
                        text = if (audioFile != null) context.getFileNameFromUri(audioFile).maxLength(34).replace(".mp3", "") else "Select file",
                        maxLines = 1,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    Spacer(Modifier.weight(1.0f))
                    IconButton(
                        onClick = {
                            mediaPlayer?.stop()
                            audioFile = null
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "",
                            Modifier.size(100.dp)
                        )
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun Level(
        context: Context = myContext
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

    @Composable
    // TODO: Probably I won't need a random learn screen. Just need a learn phase screen
    // TODO: Hide the navigation bar on this screen!
    fun LearnPhase() {
        topBarViewModel.setTitle("Learn phase")

        Column(
            modifier = ModifierManager.paddingMostTopModifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Row(
                modifier = Modifier
                    .weight(2.5f)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceContainer),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                LargeText(text = "Available", color = MaterialTheme.colorScheme.onSurface)
            }
//            Spacer(Modifier.height(10.dp))
//            HorizontalDivider()
//            Spacer(Modifier.height(10.dp))

            Row(
                modifier = ModifierManager.paddingTopModifier.fillMaxWidth().weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            )
            {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
                    modifier = Modifier.size(80.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onTertiaryContainer,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.size(80.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }

    @Composable
    fun LearnLevel(onClickLearn: () -> Unit) {
        topBarViewModel.setTitle("Level system learning")

        Column(
            modifier = ModifierManager.paddingMostTopModifier
        ) {
            val levelListManager = LevelListManager(
                items = MockData.levelItems,
                context = myContext,
                modifier = Modifier,
                searchText = "",
                onClick = onClickLearn,
                type = LevelListType.Learn
            )
            levelListManager.List()
        }
    }
}

