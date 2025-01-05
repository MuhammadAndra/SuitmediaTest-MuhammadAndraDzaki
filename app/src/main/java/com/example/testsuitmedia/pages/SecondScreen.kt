package com.example.testsuitmedia.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.testsuitmedia.R

@Composable
fun SecondScreen(modifier: Modifier = Modifier, navController: NavController, name: String?) {
    val fontFamily = FontFamily(
        Font(R.font.poppins_regular, FontWeight.Normal),
        Font(R.font.poppins_bold, FontWeight.Bold),
        Font(R.font.poppins_semibold, FontWeight.SemiBold),
    )
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val selectedUserName = savedStateHandle?.getStateFlow<String>("selectedUserName", "")?.collectAsState("")?.value

    Scaffold(
        topBar = {
            SimpleCenteredAppbar(
                navController = navController, title = "Second Screen", fontFamily = fontFamily
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Welcome",
                    fontFamily = fontFamily,
                    fontSize = 12.sp,
                    color = Color(0xff04021D)
                )
                Text(
                    name.toString(),
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xff04021D)
                )
            }
            Text(
                text = if (selectedUserName!!.isEmpty()) "Selected User Name" else selectedUserName.toString()  ,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color(0xff04021D)
            )
            SimpleButton(
                text = "Choose a User",
                onClick = { navController.navigate("thirdScreen") })
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SecondScreenPreview() {
    SecondScreen(navController = rememberNavController(), name = "John Doe")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleCenteredAppbar(
    modifier: Modifier = Modifier,
    navController: NavController,
    fontFamily: FontFamily,
    title: String
) {
    Column {
        CenterAlignedTopAppBar(
            colors = topAppBarColors(titleContentColor = Color(0xff04021D)),
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Back",
                        tint = Color(0xff554AF0)
                    )
                }
            },
            title = {
                Text(
                    title, style = TextStyle(
                        fontFamily = fontFamily, fontWeight = FontWeight.SemiBold, fontSize = 18.sp
                    )
                )
            },
        )
        HorizontalDivider(color = Color(0xffE2E3E4))
    }
}

@Preview
@Composable
private fun SimpleCenteredAppbarPreview() {
    SimpleCenteredAppbar(
        navController = rememberNavController(),
        title = "Second Screen",
        fontFamily = FontFamily.Default
    )
}