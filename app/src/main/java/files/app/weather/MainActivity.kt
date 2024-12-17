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
import files.app.weather.logic.SharedPref

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = SharedPref(this)
        val internetConnection = InternetConnection(this)
        val data = API(this, internetConnection, sharedPref)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "${R.string.splash_screen}") {
                composable("${R.string.splash_screen}") {
                    if (data.isEmpty()) data.searchByDefCity()
                    SplashScreen(navController, internetConnection)
                }
                composable("${R.string.main_screen}") {
                    MainScreen(data, navController, internetConnection)
                }
            }
        }
    }

    @Deprecated("Deprecated in Java", ReplaceWith("finishAffinity()"))
    override fun onBackPressed() {
        finishAffinity()
    }
}