package com.aris.greekcitizenshipquiz.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aris.greekcitizenshipquiz.ui.screens.QuizMenuScreen
import com.aris.greekcitizenshipquiz.ui.screens.QuizScreen

private const val HOME_SCREEN = "quiz_screen"
private const val QUIZ_MENU_SCREEN = "quiz_menu_screen"


@Composable
fun NavGraph() {

    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = HOME_SCREEN
    ) {


        composable(
            route = HOME_SCREEN
        ) {

            QuizScreen(
                onOpenMenu = {
                    navController.navigate(QUIZ_MENU_SCREEN)
                }
            )
        }


        composable(
            route = QUIZ_MENU_SCREEN
        ) {

            QuizMenuScreen(
//                onBack = {
//                    navController.popBackStack()
//                },
                onHome = {

                    navController.navigate(HOME_SCREEN) {

                        popUpTo(HOME_SCREEN)

                        launchSingleTop = true

                    }

                },

                onIncorrectAnswers = {
                    // αργότερα:
                    // navController.navigate(INCORRECT_ANSWERS_SCREEN)
                }
            )
        }
    }
}