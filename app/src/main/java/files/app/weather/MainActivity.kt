package files.app.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import files.app.weather.Layouts.MainScreen
import files.app.weather.logic.API
import files.app.weather.Layouts.SplashScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = API("Khmilnyk", this)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "splash_screen") {
                composable("splash_screen") {
                    SplashScreen(navController)
                }
                composable("main_content") {
                    MainScreen(data)
                }
            }
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}
