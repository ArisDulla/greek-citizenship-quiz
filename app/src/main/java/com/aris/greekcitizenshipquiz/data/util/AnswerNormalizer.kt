package com.aris.greekcitizenshipquiz.data.util

import java.text.Normalizer
import java.util.Locale

object AnswerNormalizer {

    fun normalize(text: String): String {

        return Normalizer.normalize(text, Normalizer.Form.NFD)
            .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "") // αφαιρεί τόνους
            .replace("\\(.*?\\)".toRegex(), "") // αφαιρεί παρενθέσεις
            .replace("[΄'`´]".toRegex(), "") // αφαιρεί σύμβολα τόνου/αποστρόφου
            .lowercase(Locale.getDefault())
            .trim()
    }
}