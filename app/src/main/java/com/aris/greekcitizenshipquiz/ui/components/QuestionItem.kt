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
import androidx.compose.material3.Icon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.aris.greekcitizenshipquiz.data.util.isValidText
import com.aris.greekcitizenshipquiz.domain.model.QuestionOption
import com.aris.greekcitizenshipquiz.ui.model.AnswerState
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun QuestionItem(
    question: Question,
    answerState: AnswerState,
    selectedOptionId: Int?,
    onOptionSelected: (QuestionOption) -> Unit,
    textAnswers: List<String>,
    onTextAnswerChanged: (Int, String) -> Unit,
    textAnswerResults: List<Boolean?>,
    onCheckTextAnswers: () -> Unit,
    correctAnswers: List<String?>,
    speakText: (String) -> Unit,
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
            Color.White.copy(alpha = 1.0f)

        AnswerState.CORRECT ->
            Color(0xFFC8E6C9)

        AnswerState.INCORRECT ->
            Color(0xFFFFCDD2)
    }

    val focusRequesters = remember(question.questionId,textAnswers.size) {
        List(textAnswers.size) {
            FocusRequester()
        }
    }
    val focusManager = LocalFocusManager.current

    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(question.questionId) {
        focusManager.clearFocus()
        delay(300.milliseconds)

        if (focusRequesters.isNotEmpty()) {
            focusRequesters[0].requestFocus()
            keyboardController?.show()
        }
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
                    modifier = Modifier.align(Alignment.Start),
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

                        Text(
                            text = "\uD83D\uDFE2 Νέα ερώτηση",
                            fontSize = 16.sp,
                            color = Color(0xFF1B5E20),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            if (question.testGroup != null) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {

                    Surface(
                        shape = RoundedCornerShape(50.dp),
                        color = Color(0xFFBBDEFB)
                    ) {

                        Text(
                            text = "Θέμα ${question.testGroup}",
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(
                                    horizontal = 20.dp,
                                    vertical = 8.dp
                                )
                        )
                    }
                }
            }


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
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = it,
                            fontSize = 27.sp,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.width(2.dp))

                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null
                        )
                    }

                }

            question.focusCompletion
                .takeIf {
                    it.isValidText()
                }
                ?.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = it,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        fontSize = 22.sp,
                    )
                }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 80.dp),

                shape = RoundedCornerShape(12.dp),

                onClick = {

                    val text = buildString {

                        question.mainText
                            .takeIf { it.isNotBlank() }
                            ?.let {
                                append(it).append(". ")
                            }

                        question.textCompletion
                            ?.takeIf { it.isNotBlank() }
                            ?.let {
                                append(it).append(". ")
                            }

                        question.focusCompletion
                            ?.takeIf { it.isNotBlank() }
                            ?.let {
                                append(it).append(". ")
                            }


                        if (
                            question.typeQuestionId != 7 &&
                            question.typeQuestionId != 8 &&
                            question.typeQuestionId != 10
                        ) {

                            val opt = question.options
                                .mapNotNull { it.optionText }
                                .joinToString(". ")

                            append(opt)
                        }
                    }
                    if (text.isNotBlank()) {
                        speakText(text)
                    }
                }
            ) {

                Text(
                    text = "🔊 Άκουσε όλη την ερώτηση",
                    fontSize = 18.sp
                )
            }






            val checked = textAnswerResults.any { it != null }
            if (question.isTextAnswer) {

                textAnswers.forEachIndexed { index, answer ->


                    Column {

                        val result = textAnswerResults.getOrNull(index)

                        val fieldColor = when(result) {
                            true -> Color(0xFFE8F5E9)
                            false -> Color(0xFFFFEBEE)
                            null -> Color(0xFFE3F2FD)
                        }

                        val borderColor = when(result) {
                            true -> Color(0xFF2E7D32)
                            false -> Color(0xFFC62828)
                            null -> MaterialTheme.colorScheme.outline
                        }

                        TextField(
                            value = answer,
                            singleLine = true,
                            onValueChange = { newValue ->
                                val filtered = newValue.filter {
                                    it in '\u0370'..'\u03FF' ||
                                            it in '\u1F00'..'\u1FFF' ||
                                            it.isWhitespace()
                                }

                                onTextAnswerChanged(index, filtered)
                            },
                            placeholder = {
                                Text("Απάντηση ${index + 1}")
                            },
                            readOnly = checked,

                            colors = TextFieldDefaults.colors(

                                focusedIndicatorColor = borderColor,

                                unfocusedIndicatorColor = borderColor,

                                cursorColor = borderColor,

                                focusedContainerColor = fieldColor ,

                                unfocusedContainerColor = fieldColor
                            ),

                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequesters[index]),

                            keyboardOptions = KeyboardOptions(
                                imeAction =
                                    if (index < textAnswers.size - 1)
                                        ImeAction.Next
                                    else
                                        ImeAction.Done
                            ),

                            keyboardActions = KeyboardActions(

                                onNext = {
                                    if (index < focusRequesters.lastIndex) {
                                        focusRequesters[index + 1].requestFocus()
                                    }
                                },

                                onDone = {
                                    keyboardController?.hide()
                                    focusRequesters[index].freeFocus()
                                    focusManager.clearFocus()
                                }
                            )
                        )


                        when(result) {

                            true -> {

                                correctAnswers.getOrNull(index)?.let { correctAnswer ->

                                    Text(
                                        text = "✅ Σχεδόν σωστή!",
                                        color = Color(0xFF2E7D32),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )


                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 4.dp, vertical = 12.dp),
                                        shape = RoundedCornerShape(16.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color.White.copy(alpha = 0.85f)
                                        ),
                                        elevation = CardDefaults.cardElevation(
                                            defaultElevation = 6.dp
                                        )
                                    ) {

                                        Column(
                                            modifier = Modifier
                                                .padding(16.dp)
                                        ) {


                                    Text(
                                        text = "Προσοχή στην ορθογραφία:",
                                        color = Color(0xFFE65100),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )

                                            Column(
                                                horizontalAlignment = Alignment.Start,
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Text(
                                                    text = correctAnswer,
                                                    color = Color(0xFF1565C0),
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 24.sp
                                                )

                                                Spacer(modifier = Modifier.height(8.dp))

                                                Button(
                                                    modifier = Modifier.defaultMinSize(minHeight = 80.dp),
                                                    shape = RoundedCornerShape(12.dp),
                                                    onClick = {
                                                        if (correctAnswer.isNotBlank()) {
                                                            speakText(correctAnswer)
                                                        }
                                                    }
                                                ) {
                                                    Text(
                                                        text = "🔊 Άκουσε",
                                                        fontSize = 18.sp
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }?: run {

                                    Text(
                                        text = "✅ Σωστή απάντηση",
                                        color = Color(0xFF2E7D32),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )


                                }
                            }


                            false -> {

                                Text(
                                    text = "❌ Λάθος",
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFC62828)
                                )
                            }

                            null -> {}
                        }

                    }
                }
                if (!checked) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 60.dp),

                    shape = RoundedCornerShape(12.dp),

                    contentPadding = PaddingValues(
                        horizontal = 20.dp,
                        vertical = 12.dp
                    ),
                    onClick = {

                        keyboardController?.hide()

                        focusRequesters.forEach {
                            it.freeFocus()
                        }

                        onCheckTextAnswers()
                    }
                ) {

                    Text(
                        text = "Έλεγχος",
                        fontSize = 18.sp
                    )
                }
                }

                if (checked) {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.85f)
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        )
                    ) {

                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {

                            Text(
                                text = "Σωστές απαντήσεις:",
                                fontSize = 22.sp,
                                color = Color(0xFF0021CC),
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )

                            question.options
                                .filter { it.isCorrect }
                                .forEach { option ->

                                    Text(
                                        text = "• ${option.optionText}",
                                        fontSize = 20.sp,
                                        color = Color(0xFF2E7D32),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(
                                        modifier = Modifier.height(5.dp)
                                    )
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Button(
                                        modifier = Modifier
                                            .defaultMinSize(minHeight = 80.dp),

                                        shape = RoundedCornerShape(12.dp),

                                        onClick = {

                                            val text = question.options
                                                .filter { it.isCorrect }
                                                .mapNotNull { it.optionText }
                                                .joinToString(". ")
                                            if (text.isNotBlank()) {
                                            speakText(text)
                                                }
                                        }
                                    ) {

                                        Text(
                                            text = "🔊 Άκουσε",
                                            fontSize = 18.sp
                                        )
                                    }
                                }
                        }
                    }
                }


            } else {


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
                            .defaultMinSize(minHeight = 80.dp),

                        shape = RoundedCornerShape(12.dp),

                        contentPadding = PaddingValues(
                            horizontal = 20.dp,
                            vertical = 12.dp
                        ),

                        enabled = answerState == AnswerState.NONE,

                        border = BorderStroke(
                            2.dp,
                            Color.Black.copy(alpha = 0.6f)
                        ),

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
                                    fontSize = 18.sp,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Start
                                )
                            }
                    }

                    if (
                        option.isCorrect &&
                        answerState != AnswerState.NONE &&
                        question.typeQuestionId != 2
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Button(
                                modifier = Modifier
                                    .defaultMinSize(minHeight = 80.dp),

                                shape = RoundedCornerShape(12.dp),

                                onClick = {

                                    option.optionText?.let {
                                        speakText(it)
                                    }

                                }
                            ) {

                                Text(
                                    text = "🔊 Άκουσε την απάντηση",
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}