package com.example.testsuitmedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testsuitmedia.pages.FirstScreen
import com.example.testsuitmedia.pages.SecondScreen
import com.example.testsuitmedia.pages.ThirdScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "firstScreen") {
                composable(route = "firstScreen") { FirstScreen(navController = navController) }
                composable(route = "secondScreen/{name}") { navBackStackEntry ->
                    val name = navBackStackEntry.arguments?.getString("name")
                    SecondScreen(navController = navController, name = name)
                }
                composable(route = "thirdScreen") { ThirdScreen(navController = navController) }
            }
        }
    }
}

