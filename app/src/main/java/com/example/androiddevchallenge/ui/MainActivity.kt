package com.example.androiddevchallenge.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.androiddevchallenge.ui.theme.WoofTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var splashScreen: SplashScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            AppContent {
                splashScreen.setKeepOnScreenCondition(it)
            }
        }
    }

    @Composable
    fun AppContent(splashScreenVisibleCondition: (SplashScreen.KeepOnScreenCondition) -> Unit) {
        WoofTheme {
            Surface {
                AdoptyApp(splashScreenVisibleCondition)
            }
        }
    }
}
