package com.aris.greekcitizenshipquiz.ui.components
import com.aris.greekcitizenshipquiz.data.util.toFile
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.aris.greekcitizenshipquiz.domain.model.Question
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.aris.greekcitizenshipquiz.data.util.isValidText

@Composable
fun QuestionItem(
    question: Question
) {

    val context = LocalContext.current

    // Εικόνες ερώτησης
    question.images
        .filter {
            !it.image.isNullOrBlank()
        }
        .forEach { image ->

            AsyncImage(
                model = image.toFile(context),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                contentScale = ContentScale.FillWidth
            )
        }


    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.50f)
        ),
        border = BorderStroke(
            1.dp,
            Color.White.copy(alpha = 0.5f)
        )
    ) {


        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )  {


            // Νέα ερώτηση

            if (question.isNew) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.Verified,
                        contentDescription = null,
                        tint = Color(0xFF2E7D32)
                    )

                    Spacer(
                        modifier = Modifier.width(6.dp)
                    )

                    Text(
                        text = "Νέα ερώτηση",
                        fontSize = 16.sp,
                        color = Color(0xFF2E7D32)
                    )
                }
            }

            // Κύριο κείμενο

            if (question.mainText.isValidText()) {

                Text(
                    text = question.mainText,
                    fontSize = 22.sp
                )
            }


            question.textCompletion
                .takeIf {
                    it.isValidText()
                }
                ?.let {
                    Text(
                        text = it,
                        fontSize = 22.sp
                    )
                }

            question.focusCompletion
                .takeIf {
                    it.isValidText()
                }
                ?.let {
                    Text(
                        text = it,
                        fontSize = 22.sp
                    )
                }

            // Επιλογές
            question.options.forEach { option ->


                Button(

                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 60.dp),


                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.DarkGray
                    ),

                    onClick = {
                        // επιλογή απάντησης
                    }
                ) {
                        option.optionText
                            ?.let {

                                Text(
                                    text = it,
                                    fontSize = 18.sp
                                )
                            }
                }
            }
        }
    }
}