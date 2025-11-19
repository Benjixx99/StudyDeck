package bx.app.ui.screen

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import bx.app.core.maxLength
import bx.app.data.enums.CardSideType
import bx.app.presentation.viewmodel.TopBarViewModel
import bx.app.ui.ModifierManager
import bx.app.ui.composable.AudioPlayer
import bx.app.ui.composable.LargeText
import bx.app.ui.composable.MultiLineTextField
import bx.app.ui.composable.cardTypeSegmentedControl
import bx.app.ui.getFileNameFromUri

/**
 * This screen displays the content of a specific card
 */
@Composable
internal fun CardScreen(
    context: Context,
    topBarViewModel: TopBarViewModel,
) {
    topBarViewModel.setTitle("Card")

    Column(
        modifier = ModifierManager.paddingMostTopModifier.fillMaxSize(),
        //horizontalAlignment = Alignment.CenterHorizontally,
        //verticalArrangement = Arrangement.SpaceBetween
    ) {
        var cardType = cardTypeSegmentedControl()

        // Text Type
        if (cardType == CardSideType.TEXT) {
            MultiLineTextField(
                modifier = ModifierManager.paddingTopModifier.fillMaxSize().padding(start = 10.dp, end = 10.dp, bottom = 100.dp),
                labelText = "Text to learn",
                maxLines = 5 // TODO: Need to calculate the right amount and find out how to manage the right behaviour if the text hits the keyboard
            )
        }

        // Audio Type
        // Select audio file
        if (cardType == CardSideType.AUDIO) {
            // TODO: Need to write a function like AudioPlayerRow
            var audioFile by remember { mutableStateOf<Uri?>(null) }
            var mediaPlayer = if (audioFile != null) MediaPlayer.create(context, audioFile) else null
            val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { audioFile = it }

            Row(
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