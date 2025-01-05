package com.example.testsuitmedia.pages

import android.media.Image
import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.testsuitmedia.R

@Composable
fun FirstScreen(modifier: Modifier = Modifier, navController: NavController) {
    var name by remember { mutableStateOf("") }
    var palindrome by remember { mutableStateOf("") }
    var openAlertDialog by remember { mutableStateOf(false) }
    var dialogText:String
    var dialogIcon:ImageVector
    val context = LocalContext.current

    fun isPalindrome(value: String): Boolean {
        val cleanedValue = value.replace(" ", "")
        return cleanedValue == cleanedValue.reversed()
    }

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.matchParentSize(),
            painter = painterResource(id = R.drawable.background_1),
            contentDescription = "Background Image",
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.size(116.dp),
                painter = painterResource(id = R.drawable.ic_photo),
                contentDescription = "photo",
            )
            Spacer(modifier = Modifier.height(50.dp))
            Column {
                SimpleTextField(
                    value = name, onValueChange = { name = it }, placeholder = "Name"
                )
                Spacer(modifier = Modifier.height(15.dp))
                SimpleTextField(
                    value = palindrome,
                    onValueChange = { palindrome = it },
                    placeholder = "Palindrome"
                )
            }
            Spacer(modifier = Modifier.height(45.dp))
            Column {
                SimpleButton(
                    text = "CHECK",
                    onClick = {
                        if (palindrome.isEmpty()) {
                            Toast.makeText(
                                context, "Palindrome field cannot be empty", Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            openAlertDialog = true
                        }
                    }
                )
                if (openAlertDialog) {
                    if (isPalindrome(palindrome)) {
                        dialogText = "$palindrome isPalindrome"
                        dialogIcon = Icons.Default.Check
                    } else {
                        dialogText = "$palindrome is not palindrome"
                        dialogIcon = Icons.Default.Close
                    }
                    PalindromeDialog(
                        onDismissRequest = { openAlertDialog = false },
                        text = dialogText,
                        icon = dialogIcon,
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                SimpleButton(text = "NEXT", onClick = {
                    if (name.isEmpty()) {
                        Toast.makeText(
                            context, "Name field cannot be empty", Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        navController.navigate("secondScreen/$name")
                    }
                })

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FirstScreenPreview() {
    FirstScreen(navController = rememberNavController())
}

@Composable
fun SimpleTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    val fontFamily = FontFamily(
        Font(R.font.poppins_regular, FontWeight.Normal),
    )
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                modifier = Modifier.alpha(0.36f),
                text = placeholder,
                color = Color(0xff686777),
                fontSize = 16.sp,
                style = TextStyle(fontFamily = fontFamily)
            )
        },
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Color(0xff2B637B)
        ),
    )
}

@Preview
@Composable
private fun SimpleTextFieldPreview() {
    var value by remember { mutableStateOf("") }
    SimpleTextField(value = value, onValueChange = { value = it }, placeholder = "Name")
}

@Composable
fun SimpleButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    val fontFamily = FontFamily(
        Font(R.font.poppins_regular, FontWeight.Normal),
        Font(R.font.poppins_medium, FontWeight.Medium),
        )
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 40.dp),
        onClick = onClick, colors = ButtonDefaults.buttonColors(Color(0xff2B637B)),
        shape = RoundedCornerShape(12.dp),
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            style = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Medium)
        )
    }
}

@Preview
@Composable
private fun SimpleButtonPreview() {
    SimpleButton(text = "CHECK", onClick = {})
}

@Composable
fun PalindromeDialog(onDismissRequest: () -> Unit, text: String, icon: ImageVector) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Dialog Icon",
                    Modifier.size(40.dp),
                    tint = Color(0xff2B637B)
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = text, textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PalindromeDialogPreview() {
    PalindromeDialog(
        onDismissRequest = {}, text = "suitmedia isn't a palindrome", icon = Icons.Default.Close
    )
}