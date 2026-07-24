package com.aris.greekcitizenshipquiz.domain.repository
import com.aris.greekcitizenshipquiz.domain.model.SyncResult
import kotlinx.coroutines.flow.Flow
//
// Χρειάζομαι έναν τρόπο να συγχρονίσω δεδομένα  ΚΑΙ ΟΧΙ Κατέβασε ZIP με Retrofit και βάλε τα σε Room.
//
interface QuizRepository {


    suspend fun syncQuizData(): SyncResult

    fun observeLatestExamPeriodTitle(): Flow<String?>

    suspend fun addIncorrectAnswer(questionId: Int)

    fun observeIncorrectAnswers(): Flow<List<Int>>

    suspend fun removeIncorrectAnswer(questionId: Int)

    suspend fun clearIncorrectAnswers()
}