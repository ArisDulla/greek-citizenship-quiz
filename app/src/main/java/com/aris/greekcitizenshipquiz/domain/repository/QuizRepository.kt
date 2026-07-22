package com.aris.greekcitizenshipquiz.domain.repository

//
// Χρειάζομαι έναν τρόπο να συγχρονίσω δεδομένα  ΚΑΙ ΟΧΙ Κατέβασε ZIP με Retrofit και βάλε τα σε Room.
//
interface QuizRepository {


    suspend fun syncQuizData(): Result<Unit>


}