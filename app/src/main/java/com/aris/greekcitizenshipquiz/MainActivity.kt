package com.aris.greekcitizenshipquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.aris.greekcitizenshipquiz.ui.theme.GreekCitizenshipQuizTheme
import dagger.hilt.android.AndroidEntryPoint
import com.aris.greekcitizenshipquiz.ui.navigation.NavGraph
@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()


        setContent {

            GreekCitizenshipQuizTheme {

                NavGraph()


            }
        }
    }
}
