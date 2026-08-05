package com.aris.greekcitizenshipquiz.data.util

import java.text.Normalizer

object AnswerNormalizer {

    fun normalize(text: String): String {
        return Normalizer.normalize(text, Normalizer.Form.NFD)
            .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
            .replace("[()]".toRegex(), "")
            .replace("[?'`]".toRegex(), "")
            .replace("\\s+".toRegex(), " ")
            .trim()
    }


    fun removeParentheses(text: String): String {
        return text
            .replace("(", "")
            .replace(")", "")
            .trim()
    }


    fun normalizeExact(text: String): String {
        return text
            .replace("[?'`?]".toRegex(), "")
            .trim()
    }
}