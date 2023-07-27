package files.app.weather.Layouts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import files.app.weather.R
import files.app.weather.logic.API
import files.app.weather.logic.InternetConnection
import files.app.weather.logic.SharedPref

@Composable
fun MainScreen(
    data: API,
    navController: NavController,
    internetConnection: InternetConnection,
    sharedPref: SharedPref
) {
    if (!internetConnection.isConnected)
        navController.navigate("${R.string.splash_screen}")
    BackGround()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        GetMainCard(data, sharedPref)
        TabLayout(data)
    }
}

@Preview(showBackground = true)
@Composable
fun BackGround() {
    Image(
        painter = painterResource(id = R.drawable.background),
        contentDescription = "Background",
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.7f),
        contentScale = ContentScale.Crop
    )
}