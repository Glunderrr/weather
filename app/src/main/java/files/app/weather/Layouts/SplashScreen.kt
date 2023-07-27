package files.app.weather.Layouts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import files.app.weather.R
import files.app.weather.logic.API
import files.app.weather.logic.InternetConnection
import files.app.weather.ui.theme.DarkPink
import files.app.weather.ui.theme.Pink
import kotlinx.coroutines.delay


@Composable
fun SplashScreen( navController: NavController, internetConnection: InternetConnection) {
    var visible by remember { mutableStateOf(true) }
    if (visible) SplashScreenContent(internetConnection)
    LaunchedEffect(key1 = internetConnection.isConnected) {
        if (internetConnection.isConnected) {
            delay(1000)
            visible = false
            navController.navigate("main_content")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenContent(internetConnection: InternetConnection) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Pink), horizontalAlignment = Alignment.End
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.55f),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Image(
                painter = painterResource(id = R.drawable.round_icon),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
            )
            Text(
                text = stringResource(R.string.app_name),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f), contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (internetConnection.isConnected) "" else "no internet connection",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
        Text(
            text = "created by Glunder",
            modifier = Modifier.padding(end = 25.dp),
            color = DarkPink
        )
    }
}
