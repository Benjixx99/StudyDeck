package bx.app.ui.composable

import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import bx.app.data.enums.CardSideType
import bx.app.ui.ModifierManager
import bx.app.ui.data.LearningPhaseParams
import kotlin.random.Random

@Composable
internal fun LearningPhase(
    params: LearningPhaseParams,
    learnBothSides: Boolean,
    onKnown: () -> Unit,
    onNotKnown: () -> Unit,
    isActive: Boolean
) {
    var isFront by remember { mutableStateOf(!learnBothSides) }
    val fileName = if (isFront) params.frontFileName else params.backFileName
    val path = if (isFront) params.frontPath else params.backPath
    val audioUri = path.takeIf { it.isNotEmpty() }?.toUri()

    val context = LocalContext.current
    val mediaPlayer = remember(audioUri) { audioUri?.let { MediaPlayer.create(context, it) } }

    DisposableEffect(Unit) { onDispose { mediaPlayer?.release() } }
    LaunchedEffect(isActive) { if (!isActive) { mediaPlayer?.stopPlayback() } }

    Column(
        modifier = ModifierManager.paddingMostTopModifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            modifier = Modifier
                .weight(2.5f)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            isFront = !isFront
                            mediaPlayer?.stopPlayback()
                        }
                    )
                },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.padding(top = 10.dp),
                verticalAlignment = Alignment.Top,
            ) {
                SmallText(
                    text = if (isFront) "FRONT" else "BACK",
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (params.card.frontSideType == CardSideType.TEXT && isFront
                    || params.card.backSideType == CardSideType.TEXT && !isFront) {
                    LargeText(
                        text = if (isFront) params.frontText else params.backText,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AudioPlayer(mediaPlayer)
                        Spacer(Modifier.padding(top = 10.dp))
                        LargeText(
                            text = fileName,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.padding(bottom = 10.dp),
                verticalAlignment = Alignment.Bottom,
            ) {
                SmallText(
                    text = "(Click to switch side)",
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = 45.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    onNotKnown()
                    mediaPlayer?.stopPlayback()
                    if (learnBothSides) isFront = Random.nextBoolean()
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
                modifier = Modifier.size(80.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Not known",
                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Button(
                onClick = {
                    onKnown()
                    mediaPlayer?.stopPlayback()
                    if (learnBothSides) isFront = Random.nextBoolean()
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.size(80.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Known",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

internal fun MediaPlayer.stopPlayback() {
    this.let {
        if (it.isPlaying) it.pause()
        it.seekTo(0)
    }
}