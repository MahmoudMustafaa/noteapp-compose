package com.example.noteapp.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.noteapp.presentation.viewModel.NoteViewModel
import com.example.noteapp.data.db.Note


@OptIn(ExperimentalMaterial3Api::class)
class NoteDetailsScreen(val note: Note, val noteViewModel: NoteViewModel) : Screen{
    @Composable
    override fun Content() {
        val title = remember { mutableStateOf(note.title) }
        val description = remember { mutableStateOf(note.description) }
        val navigator = LocalNavigator.currentOrThrow
        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,)
        {
            TextField(
                value = title.value,
                onValueChange = { title.value = it },
                placeholder = { Text(text = "Add Title") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color(0xfff2b741)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .background(Color(0xFFF2F2F2), shape = MaterialTheme.shapes.small)
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = description.value,
                onValueChange = { description.value = it },
                placeholder = { Text(text = "Add Description") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color(0xfff2b741)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .background(Color(0xFFF2F2F2), shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row {
                Button(
                    onClick = {
                        noteViewModel.update(note.copy(title = title.value, description = description.value))
                        navigator.pop()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xfff2b741,),

                    )
                ) {
                    Text(text = "Update", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.width(10.dp))

                Button(
                    onClick = {
                        noteViewModel.delete(note)
                        navigator.pop()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xfff2b741,),

                    )
                ) {
                    Text(text = "Delete", fontWeight = FontWeight.Bold)
                }
            }
        }

    }

}