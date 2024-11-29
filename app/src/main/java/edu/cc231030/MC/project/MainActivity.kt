package edu.cc231030.MC.project

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import edu.cc231030.MC.project.ui.TrainingApp
import edu.cc231030.MC.project.ui.theme.style.MobileCodingProjectTheme

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileCodingProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TrainingApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
        Log.i(TAG, "App started")
    }
}
