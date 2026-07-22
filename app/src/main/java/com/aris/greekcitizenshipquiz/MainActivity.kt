package com.aris.greekcitizenshipquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.aris.greekcitizenshipquiz.ui.theme.GreekCitizenshipQuizTheme
import dagger.hilt.android.AndroidEntryPoint
import com.aris.greekcitizenshipquiz.ui.screens.QuizScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()


        setContent {

            GreekCitizenshipQuizTheme {

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    QuizScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
