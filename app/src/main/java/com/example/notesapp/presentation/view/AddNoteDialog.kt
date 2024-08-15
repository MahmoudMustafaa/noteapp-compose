package com.example.noteapp.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteDialog(
    onSaveClick: (String, String, Color) -> Unit,
    onDismissRequest: () -> Unit
) {
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val selectedColor = remember { mutableStateOf(Color.DarkGray) }

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        icon = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDismissRequest() }
            ) {
                Icon(
                    Icons.Default.Close, contentDescription = null, modifier = Modifier.align(
                        Alignment.CenterEnd
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSaveClick(
                        title.value,
                        description.value,
                        selectedColor.value
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xfff2b741),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Save Note")
            }
        },
        title = {
            Text(
                text = "Add New Note",
                color = Color(0xfff2b741),
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = title.value,
                    onValueChange = { title.value = it },
                    placeholder = { Text(text = " Add Title", color = Color(0xfff2b741),) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        cursorColor = Color.White,
                        focusedBorderColor = Color(0xfff2b741),
                        unfocusedBorderColor = Color.White,
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    value = description.value,
                    onValueChange = { description.value = it },
                    placeholder = { Text(text = " Add Description", color = Color(0xfff2b741)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        cursorColor = Color.White,
                        focusedBorderColor = Color(0xfff2b741),
                        unfocusedBorderColor = Color.White,
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                // Add your ColorPicker here
                ColorPicker(
                    selectedColor = selectedColor.value,
                    onColorSelected = { selectedColor.value = it }
                )
            }
        },
        containerColor = selectedColor.value
    )
}

@Composable
fun ColorPicker(selectedColor: Color, onColorSelected: (Color) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val colors = listOf(Color.Red.copy(alpha = 0.9f), Color.LightGray, Color.Blue, Color.Black, Color.DarkGray)
        colors.forEach { color ->
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(color, shape = CircleShape)
                    .clickable { onColorSelected(color) }
                    .border(
                        width = 2.dp,
                        color = if (color == selectedColor) Color.Black else Color.Transparent,
                        shape = CircleShape
                    )
            )
        }
    }
}

