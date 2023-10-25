package com.stevyson.checklit.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.stevyson.checklit.components.OptionTab
import com.stevyson.checklit.db
import com.stevyson.checklit.model.Category
import com.stevyson.checklit.model.Expense
import com.stevyson.checklit.ui.theme.BackgroundElevated
import com.stevyson.checklit.ui.theme.Chrysler
import com.stevyson.checklit.ui.theme.Shapes
import com.stevyson.checklit.ui.theme.Typography
import io.realm.kotlin.ext.query
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(
    navController: NavController
){
    val coroutineScope = rememberCoroutineScope()
    var deleteConfirmationShowing by remember {
        mutableStateOf(false)
    }

    val eraseAllData: () -> Unit = {
        coroutineScope.launch {
            db.write {
                val expenses = this.query<Expense>().find()
                val categories = this.query<Category>().find()

                delete(expenses)
                delete(categories)

                deleteConfirmationShowing = false
            }
        }
    }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text(text = "Hello Stevyson")},
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Chrysler
                ),
                navigationIcon = {
                    Surface(
                        color = Color.Transparent
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 10.dp),
                            verticalArrangement = Arrangement.SpaceAround,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text("Settings", style = Typography.titleLarge)

                        }
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(modifier = Modifier
                .padding(innerPadding)
                .background(BackgroundElevated)
                .fillMaxHeight()) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(Shapes.medium)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        OptionTab(
                            icon = Icons.Rounded.List,
                            label = "Categories",
                            description = "Create and Manage categories",
                            modifier = Modifier.clickable {
                                navController.navigate("settings/categories")
                            }
                        )
                        OptionTab(
                            icon = Icons.Rounded.Star,
                            label = "Night Theme",
                            description = "Change theme",

                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        OptionTab(
                            icon = Icons.Rounded.Notifications,
                            label = "Reminders",
                            description = "Set up reminders"
                        )
                        OptionTab(
                            icon = Icons.Rounded.Delete,
                            label = "Reset Data",
                            description = "Delete all data",
                            isDestructive = true,
                            modifier = Modifier.clickable {
                                deleteConfirmationShowing = true
                            }
                        )
                        if (deleteConfirmationShowing) {
                            AlertDialog(
                                onDismissRequest = { deleteConfirmationShowing = false },
                                title = { Text("Are you sure?") },
                                text = { Text("This action cannot be undone.") },
                                confirmButton = {
                                    TextButton(onClick = eraseAllData) {
                                        Text("Delete everything")
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = { deleteConfirmationShowing = false }) {
                                        Text("Cancel")
                                    }
                                }
                            )
                        }
                        
                    }

                }
            }
        }
    )

}


