package files.app.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import files.app.weather.Layouts.MainScreen
import files.app.weather.Layouts.SplashScreen
import files.app.weather.logic.API
import files.app.weather.logic.InternetConnection

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val responseCity = "Khmilnyk"
        val internetConnection = InternetConnection(this)
        val data = API( this, internetConnection)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "splash_screen") {
                composable("splash_screen") {
                    data.searchByResponse(responseCity)
                    SplashScreen(navController,internetConnection)
                }
                composable("main_content") {
                    MainScreen(data, navController,internetConnection)
                }
            }
        }
    }

    @Deprecated("Deprecated in Java", ReplaceWith("finishAffinity()"))
    override fun onBackPressed() {
        finishAffinity()
    }
}
