package com.aris.greekcitizenshipquiz.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aris.greekcitizenshipquiz.ui.screens.CategoryTypesScreen
import com.aris.greekcitizenshipquiz.ui.screens.QuizMenuScreen
import com.aris.greekcitizenshipquiz.ui.screens.QuizScreen

private const val HOME_SCREEN = "quiz_screen"
private const val QUIZ_MENU_SCREEN = "quiz_menu_screen"
private const val CATEGORY_TYPES_ROUTE = "category_types"
private const val CATEGORY_TYPES_SCREEN = "$CATEGORY_TYPES_ROUTE/{categoryId}"

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
            route = CATEGORY_TYPES_SCREEN
        ) { backStackEntry ->

            val categoryId =
                backStackEntry.arguments
                    ?.getString("categoryId")
                    ?.toInt() ?: 0

            CategoryTypesScreen(
                categoryId = categoryId,

                onMenu = {
                    navController.navigate(QUIZ_MENU_SCREEN) {

                        popUpTo(QUIZ_MENU_SCREEN) {
                            inclusive = false
                        }

                        launchSingleTop = true
                    }
                }

            )
        }


        composable(
            route = QUIZ_MENU_SCREEN
        ) {

            QuizMenuScreen(
                onHome = {
                    navController.navigate(HOME_SCREEN) {

                        popUpTo(HOME_SCREEN) {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                },

                onIncorrectAnswers = {
                    // navController.navigate(INCORRECT_ANSWERS_SCREEN)
                },

                onCategoryClick = { categoryId ->
                    navController.navigate("$CATEGORY_TYPES_ROUTE/$categoryId")
                }
            )
        }
    }
}