package com.aris.greekcitizenshipquiz.domain.usecase

import com.aris.greekcitizenshipquiz.domain.model.Question
import javax.inject.Inject

class GetRandomTestQuestionsUseCase @Inject constructor(
    private val getRandomQuestionsByCategoryUseCase: GetRandomQuestionsByCategoryUseCase
) {

    suspend operator fun invoke(): List<Question> {

        val distribution = listOf(
            1 to 4,
            2 to 4,
            3 to 6,
            4 to 6
        )

        val result = mutableListOf<Question>()

        var testNumber = 1


        distribution.forEach { (categoryId, limit) ->

            val questions =
                getRandomQuestionsByCategoryUseCase(
                    categoryId = categoryId,
                    limit = limit
                )


            val groups = questions.groupBy {
                it.questionNumber
            }


            groups.forEach { (_, groupQuestions) ->

                val firstQuestion = groupQuestions.first()

                val questionPoints =
                    if (groupQuestions.size > 1) {

                        2.0 / groupQuestions.size

                    } else if (firstQuestion.isTextAnswer) {

                        val maxCorrect = firstQuestion.maxCorrect
                            ?.takeIf { it > 0 }
                            ?: 1

                        2.0 / maxCorrect

                    } else {

                        2.0
                    }

                groupQuestions.forEach { question ->

                    val testQuestion =
                        question.copy(
                            testGroup = testNumber,
                            testNumber = testNumber,
                            points = questionPoints
                        )

                    result.add(testQuestion)
                }
                testNumber++
            }
        }
        return result
    }
}