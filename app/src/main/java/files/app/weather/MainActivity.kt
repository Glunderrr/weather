package files.app.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import files.app.weather.Layouts.MainScreen
import files.app.weather.logic.API


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val data = API("Khmilnyk", this)
            MainScreen(data)
        }
    }
}
