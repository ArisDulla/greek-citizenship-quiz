package com.aris.greekcitizenshipquiz.data.util

import kotlin.math.abs

object AnswerMatcher {

    fun isCloseEnough(
        answer: String,
        correct: String
    ): Boolean {

        val answer = answer.trim().lowercase()
        val correct = correct.trim().lowercase()

        if (answer == correct) return true

        val maxErrors = when {
            correct.length >= 20 -> 4
            correct.length >= 10 -> 3
            correct.length >= 5 -> 2
            correct.length >= 3 -> 1
            else -> 0
        }

        val difference = abs(answer.length - correct.length)

        return difference <= maxErrors &&
                levenshteinDistance(answer, correct) <= maxErrors
    }

    fun containsWords(
        userAnswer: String,
        correctAnswer: String
    ): Boolean {

        val userWords = userAnswer.split(" ")
        val correctWords = correctAnswer.split(" ")

        if (correctWords.size == 1) {
            return userWords.any { userWord ->
                isCloseEnough(userWord, correctWords[0])
            }
        }

        val sumTrue = userWords.count { userWord ->
            correctWords.any { correctWord ->
                isCloseEnough(userWord, correctWord)
            }
        }

        if (correctWords.size == sumTrue) {
            return true
        }
        return userWords.all { userWord ->
            correctWords.any { correctWord ->
                isCloseEnough(userWord, correctWord)
            }
        }
    }


     fun levenshteinDistance(
        a: String,
        b: String
    ): Int {

        val dp = Array(a.length + 1) {
            IntArray(b.length + 1)
        }

        for (i in 0..a.length) {
            dp[i][0] = i
        }

        for (j in 0..b.length) {
            dp[0][j] = j
        }

        for (i in 1..a.length) {
            for (j in 1..b.length) {

                val cost =
                    if (a[i - 1] == b[j - 1]) 0 else 1

                dp[i][j] = minOf(
                    dp[i - 1][j] + 1,
                    dp[i][j - 1] + 1,
                    dp[i - 1][j - 1] + cost
                )
            }
        }

        return dp[a.length][b.length]
    }
}