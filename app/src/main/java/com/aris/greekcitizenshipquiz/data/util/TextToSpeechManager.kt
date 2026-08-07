package com.aris.greekcitizenshipquiz.data.util

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import java.util.Locale

@Singleton
class TextToSpeechManager @Inject constructor(
    @ApplicationContext context: Context
) {

    private var tts: TextToSpeech? = null
    private var initialized = false
    private var pendingText: String? = null

    private fun cleanText(text: String?): String {
        return text.orEmpty()
            .replace("(10)", "δέκα")
            .replace("(1)", "ένα")
            .replace("(2)", "δύο")
            .replace("(3)", "τρία")
            .replace("(4)", "τέσσερα")
            .replace("(5)", "πέντε")
            .replace("(6)", "έξι")
            .replace("(7)", "επτά")
            .replace("(8)", "οκτώ")
            .replace("(9)", "εννέα")
            .replace(Regex("(?i)\\bnone\\b"), "")
            .replace(Regex("[^\\p{L}\\p{N}\\s.,]"), " ")
            .replace(Regex("\\s+"), " ")
            .trim()
    }


    init {
        Log.d(
            "TTS_TEST",
            "CREATED hash=${hashCode()}"
        )

        tts = TextToSpeech(context) { status ->
            Log.d("TTS_TEST", "TextToSpeechManager CREATED")
            Log.d("TTS 22", "INIT STATUS = $status")

            if (status == TextToSpeech.SUCCESS) {

                val result = tts?.setLanguage(
                    Locale.forLanguageTag("el-GR")
                )

                tts?.setSpeechRate(0.6f)

                initialized =
                    result != TextToSpeech.LANG_MISSING_DATA &&
                            result != TextToSpeech.LANG_NOT_SUPPORTED


                Log.d(
                    "TTS 22",
                    "initialized=$initialized"
                )


                if (initialized) {

                    tts?.speak(
                        " ",
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "tts_warmup"
                    )

                    pendingText?.let { text ->

                        tts?.speak(
                            text,
                            TextToSpeech.QUEUE_FLUSH,
                            null,
                            "quiz_voice"
                        )

                        pendingText = null
                    }
                }
            }
        }
    }


    fun speak(text: String) {

        val cleanedText = cleanText(text)

        if (!initialized) {
            pendingText = cleanedText
            return
        }

        if (cleanedText.isNotBlank()) {

            tts?.speak(
                cleanedText,
                TextToSpeech.QUEUE_FLUSH,
                null,
                "quiz_voice"
            )
        }
    }


    fun stop() {
        tts?.stop()
    }


    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
        initialized = false
        pendingText = null
    }
}