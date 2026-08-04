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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.aris.greekcitizenshipquiz.data.util.isValidText
import com.aris.greekcitizenshipquiz.domain.model.QuestionOption
import com.aris.greekcitizenshipquiz.ui.model.AnswerState


@Composable
fun QuestionItem(
    question: Question,
    answerState: AnswerState,
    selectedOptionId: Int?,
    onOptionSelected: (QuestionOption) -> Unit,

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

    val cardColor = when (answerState) {

        AnswerState.NONE ->
            Color.White.copy(alpha = 0.50f)

        AnswerState.CORRECT ->
            Color(0xFFC8E6C9)

        AnswerState.INCORRECT ->
            Color(0xFFFFCDD2)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = cardColor
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

                Surface(
                    shape = RoundedCornerShape(50.dp),
                    color = Color(0xFFE8F5E9)
                ) {

                    Row(
                        modifier = Modifier
                            .padding(
                                horizontal = 12.dp,
                                vertical = 6.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = null,
                            tint = Color(0xFF1B5E20),
                            modifier = Modifier.size(22.dp)
                        )

                        Spacer(
                            modifier = Modifier.width(6.dp)
                        )

                        Text(
                            text = "Νέα ερώτηση",
                            fontSize = 16.sp,
                            color = Color(0xFF1B5E20),
                            fontWeight = FontWeight.Bold
                        )
                    }
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

                val optionColor = when {

                    option.optionId == selectedOptionId &&
                            answerState == AnswerState.INCORRECT ->
                        Color(0xFFE53935)

                    option.isCorrect &&
                            answerState != AnswerState.NONE ->
                        Color(0xFF4CAF50)

                    else ->
                        Color.White
                }

                Button(

                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 60.dp),

                    shape = RoundedCornerShape(12.dp),

                    contentPadding = PaddingValues(
                        horizontal = 20.dp,
                        vertical = 12.dp
                    ),

                    enabled = answerState == AnswerState.NONE,

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black,
                        disabledContainerColor = optionColor,
                        disabledContentColor = Color.Black
                    ),

                    onClick = {
                        onOptionSelected(option)
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