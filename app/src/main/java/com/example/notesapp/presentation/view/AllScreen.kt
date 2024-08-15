package com.example.noteapp.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import com.example.noteapp.presentation.viewModel.NoteViewModel
import com.example.noteapp.data.db.Note
import com.example.noteapp.ui.theme.NoteAppTheme

class MainScreen(val noteViewModel: NoteViewModel) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val selectedIndex = remember { mutableStateOf(0) }
        val showAddDialog = remember { mutableStateOf(false) }
        val navigator = LocalNavigator.currentOrThrow
        var isDarkMode by remember { mutableStateOf(true) }
        var notesEmpty by remember { mutableStateOf(true) }
        var hasNotes by remember { mutableStateOf(false) }
        val notes by noteViewModel.allNotes.collectAsState(emptyList())


        LaunchedEffect(notes) {
            hasNotes = notes?.isNotEmpty() ?:  false
        }
        NoteAppTheme (darkTheme = isDarkMode){
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "My Notes",
                                color = Color(0xfff2b741,),
                                fontWeight = FontWeight.Bold
                            )
                        },

                        actions = {
                            Row(
                                modifier = Modifier.fillMaxHeight(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = "https://static-00.iconduck.com/assets.00/mode-light-icon-512x512-yuubs6qt.png",
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(20.dp)

                                )
                                Switch(
                                    checked = isDarkMode,
                                    onCheckedChange = { isDarkMode = it },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color.White,
                                        uncheckedThumbColor = Color.White,
                                        checkedTrackColor = Color.DarkGray,
                                        uncheckedTrackColor = Color.LightGray
                                    ),
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .scale(0.75f)
                                )
                                AsyncImage(
                                    model = "https://cdn-icons-png.flaticon.com/512/5360/5360418.png",
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(24.dp)

                                )

                            }

                        }
                    )

                }
                , bottomBar = {
                    BottomAppBar(
                        containerColor = Color(0xfff2b741,),
                        modifier = Modifier.clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            IconButton(
                                modifier = Modifier.weight(1f),
                                onClick = { selectedIndex.value = 0 }) {
                                Icon(
                                    modifier = Modifier.size(50.dp),
                                    imageVector = Icons.Filled.Home,
                                    contentDescription = null,
                                    tint = if (selectedIndex.value == 0) Color.Black else Color.White
                                )
                            }
                            FloatingActionButton(
                                onClick = { showAddDialog.value = true },
                                shape = RoundedCornerShape(topEnd = 30.dp, bottomStart = 30.dp),
                                containerColor = Color.White
                            ) {
                                IconButton(modifier = Modifier.size(70.dp),
                                    colors = IconButtonDefaults.iconButtonColors(
                                        containerColor = Color.White,
                                        contentColor = Color.White

                                    ),
                                    onClick = {
                                        showAddDialog.value = true
                                    }) {
                                    Icon(
                                        modifier = Modifier.size(40.dp),
                                        imageVector = Icons.Default.Add,
                                        contentDescription = null,
                                        tint = Color.Black
                                    )
                                }
                            }
                            IconButton(
                                modifier = Modifier.weight(1f),
                                onClick = { selectedIndex.value = 1 }) {
                                Icon(
                                    modifier = Modifier.size(50.dp),
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = null,
                                    tint = if (selectedIndex.value == 1) Color.Red else Color.White
                                )
                            }
                        }


                    }


                }
                ,modifier = Modifier.fillMaxSize()) { innerPadding ->
                Column(
                    modifier = Modifier.padding(innerPadding)
                ) {

                    if (showAddDialog.value) {
                        AddNoteDialog(
                            onSaveClick = { title, description, color ->

                                val colorInt = color.toArgb()


                                noteViewModel.insert(Note(
                                    title = title,
                                    description = description,
                                    color = colorInt,
                                    isFavorite = false
                                ))
                                showAddDialog.value = false
                            },
                            onDismissRequest = {
                                showAddDialog.value = false
                            }
                        )
                    }
                    if (selectedIndex.value == 0) {
                        HomeScreenContent(noteViewModel = noteViewModel)
                    } else {
                        FavoriteScreenContent(noteViewModel = noteViewModel)
                    }
                    if (!hasNotes) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No notes available. Add some!",
                                modifier = Modifier
                                    .padding(16.dp),
                                color = Color.Gray,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                }
            }
        }
    }
}
