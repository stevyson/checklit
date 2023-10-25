package com.stevyson.checklit

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.stevyson.checklit.presentation.Add
import com.stevyson.checklit.presentation.Budget
import com.stevyson.checklit.presentation.Categories
import com.stevyson.checklit.presentation.DashBoard
import com.stevyson.checklit.presentation.Report
import com.stevyson.checklit.presentation.Settings
import com.stevyson.checklit.presentation.Spendings
import com.stevyson.checklit.ui.theme.CheckLitTheme
import com.stevyson.checklit.ui.theme.Chrysler

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContent {

            var showBottomBar by rememberSaveable { mutableStateOf(true) }
            val navController = rememberNavController()
            val backStackEntry by navController.currentBackStackEntryAsState()

            showBottomBar = when (backStackEntry?.destination?.route) {
                "settings/categories" -> false
                "budget/add" -> false
                "dashboard/spending" -> false
                else -> true
            }


            CheckLitTheme(darkTheme = true) {
                Scaffold(

                    bottomBar = {
                        if (showBottomBar){
                            NavigationBar(containerColor = Chrysler) {
                                NavigationBarItem(
                                    selected =  backStackEntry?.destination?.route == "dashboard",
                                    onClick = {navController.navigate("dashboard") },
                                    label = {
                                        Text("Dashboard")
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Rounded.Home,
                                            contentDescription = "Dashboard"
                                        )
                                    }
                                )
                                NavigationBarItem(
                                    selected =  backStackEntry?.destination?.route == "budget",
                                    onClick = {navController.navigate("budget") },
                                    label = {
                                        Text("Budget")
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Rounded.Add,
                                            contentDescription = "Budget"
                                        )
                                    }
                                )
                                NavigationBarItem(
                                    selected =  backStackEntry?.destination?.route == "reports",
                                    onClick = {navController.navigate("reports") },
                                    label = {
                                        Text("Reports")
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Rounded.AccountBox,
                                            contentDescription = "Reports"
                                        )
                                    }
                                )
                                NavigationBarItem(
                                    selected =  backStackEntry?.destination?.route == "settings",
                                    onClick = {navController.navigate("settings") },
                                    label = {
                                        Text("Settings")
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Rounded.Settings,
                                            contentDescription = "Settings"
                                        )
                                    }
                                )
                            }
                        }
                    },
                    content = { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = "dashboard"
                        ) {
                            composable("dashboard") {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding),
                                ) {
                                    DashBoard(navController)
                                }
                            }
                            composable("dashboard/spending") {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding),
                                ) {
                                    Spendings(navController)
                                }
                            }
                            composable("budget") {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding),
                                ) {
                                    Budget(navController)
                                }
                            }

                            composable("reports") {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding),
                                ) {
                                    Report()
                                }
                            }
                            composable("budget/add") {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding),
                                ) {
                                    Add(navController)
                                }
                            }
                            composable("settings") {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding),
                                ) {
                                    Settings(navController)
                                }
                            }
                            composable("settings/categories") {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding),
                                ) {
                                    Categories(navController)
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CheckLitTheme {
        Greeting("Android")
    }
}