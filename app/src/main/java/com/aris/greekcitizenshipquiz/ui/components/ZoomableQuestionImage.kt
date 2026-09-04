package com.aris.greekcitizenshipquiz.ui.components

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun ZoomableQuestionImage(
    model: Any?,
    modifier: Modifier = Modifier
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    AsyncImage(
        model = model,
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                translationX = offset.x
                translationY = offset.y
            }
            .pointerInput(Unit) {

                detectDragGesturesAfterLongPress(

                    onDragStart = { position ->

                        val zoom = 2.5f

                        scale = zoom

                        // Κέντρο της εικόνας
                        val center = Offset(
                            x = size.width / 2f,
                            y = size.height / 2f
                        )

                        // Κάνουμε zoom ΑΚΡΙΒΩΣ στο σημείο
                        // που πάτησε ο χρήστης
                        offset = Offset(
                            x = (position.x - center.x) * (1f - zoom),
                            y = (position.y - center.y) * (1f - zoom)
                        )
                    },

                    onDrag = { change, dragAmount ->

                        change.consume()

                        offset += dragAmount
                    },

                    onDragEnd = {

                        scale = 1f
                        offset = Offset.Zero
                    },

                    onDragCancel = {

                        scale = 1f
                        offset = Offset.Zero
                    }
                )
            }
    )
}