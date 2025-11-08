package bx.app.ui.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import kotlin.math.max

@Composable
internal fun ColorPicker(
    modifier: Modifier = Modifier,
    initialColor: Color = Color.Green,
    onColorChanged: (Color) -> Unit = {}
): Color {
    val initialHsv = FloatArray(3)

    android.graphics.Color.RGBToHSV(
        (initialColor.red * 255).toInt(),
        (initialColor.green * 255).toInt(),
        (initialColor.blue * 255).toInt(),
        initialHsv
    )

    var hue by remember { mutableFloatStateOf(initialHsv[0]) }       // 0..360
    var saturation by remember { mutableFloatStateOf(initialHsv[1]) } // 0..1
    var value by remember { mutableFloatStateOf(initialHsv[2]) }      // 0..1
    val selectedColor = remember(hue, saturation, value) { hsvToColor(hue, saturation, value) }
    //LaunchedEffect(selectedColor) { onColorChanged(selectedColor) } // Not needed right now!

    Row(modifier = modifier) {
        Box(modifier = Modifier.aspectRatio(1f).padding(end = 8.dp)) {
            var position by remember { mutableStateOf(Size.Zero) }
            Canvas(modifier = Modifier
                .fillMaxSize()
                .pointerInput(hue) {
                    detectDragGestures { change, _ ->
                        saturation = (change.position.x / position.width).coerceIn(0f, 1f)
                        value = (1f - (change.position.y / position.height)).coerceIn(0f, 1f)
                    }
                }
            ) {
                // Draw the rect
                drawRoundRect(brush = Brush.horizontalGradient(listOf(Color.White, hsvToColor(hue, 1f, 1f))), cornerRadius = CornerRadius(20.0f))
                drawRoundRect(brush = Brush.verticalGradient(listOf(Color.Transparent, Color.Black)), cornerRadius = CornerRadius(20.0f),
                    blendMode = BlendMode.SrcOver)

                // Draw selection indicator (circle)
                position = this.size
                val cx = saturation * position.width
                val cy = (1f - value) * position.height
                drawCircle(
                    color = if (value > 0.5f && saturation < 0.5f) Color.Black else Color.White,
                    radius = 10f,
                    center = Offset(cx, cy),
                    style = Stroke(width = 3f)
                )
            }
        }

        // Hue panel
        Box(modifier = Modifier.width(60.dp).fillMaxHeight()) {
            var heightPx by remember { mutableFloatStateOf(0f) }
            Canvas(modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures { change, _ ->
                        val t = change.position.y / max(1f, heightPx) // 0..1
                        hue = (t * 360f).coerceIn(0f, 360f)
                    }
                }
            ) {
                heightPx = this.size.height

                drawRoundRect(
                    brush = Brush.verticalGradient(colors = List(7) { i -> hsvToColor(i * 60f % 360f, 1f, 1f) }),
                    cornerRadius = CornerRadius(10.0f)
                )

                // Draw hue selector indicator
                val yPos = (hue / 360f) * this.size.height
                drawRoundRect(
                    color = Color.White.copy(alpha = 0.9f),
                    topLeft = Offset(0f, yPos - 4f),
                    size = Size(this.size.width, 8f),
                    cornerRadius = CornerRadius(4f),
                    style = Stroke(2f)
                )
            }
        }
    }
    return selectedColor
}

/**
 * Converts HSV to [Color] (h: 0..360, s: 0..1, v: 0..1)
 */
private fun hsvToColor(h: Float, s: Float, v: Float): Color {
    val c = v * s
    val x = c * (1 - abs((h / 60f) % 2 - 1))
    val m = v - c
    val (r1, g1, b1) = when {
        h < 60f -> Triple(c, x, 0f)
        h < 120f -> Triple(x, c, 0f)
        h < 180f -> Triple(0f, c, x)
        h < 240f -> Triple(0f, x, c)
        h < 300f -> Triple(x, 0f, c)
        else -> Triple(c, 0f, x)
    }
    return Color(
        red = (r1 + m),
        green = (g1 + m),
        blue = (b1 + m),
        alpha = 1f
    )
}
