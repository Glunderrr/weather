package files.app.weather.Layouts

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import files.app.weather.R
import files.app.weather.logic.API
import files.app.weather.ui.theme.BlueLight


@Composable
fun GetMainCard(data: API) {
    val cardData = data.card
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(BlueLight)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = cardData.time.value, color = Color.White)
                Image(
                    painter = rememberImagePainter(cardData.imageURL.value),
                    contentDescription = "weatherIng", modifier = Modifier.size(35.dp)
                )
            }
            Text(
                text = cardData.actualCityName.value,
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
            Text(text = cardData.temperature.value, color = Color.White, fontSize = 75.sp)
            Text(
                text = cardData.temperatureFeelLike.value,
                color = Color.White, fontSize = 20.sp
            )
            Text(
                text = "${cardData.weatherState.value}/${cardData.windSpeed.value}",
                color = Color.White
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, bottom = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = "search",
                        tint = Color.White
                    )
                }
                IconButton(onClick = {
                    data.update(cardData.actualCityName.value)
                    Log.d("MY_API", "Data is updated")
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.cloud_sync),
                        contentDescription = "search",
                        tint = Color.White
                    )
                }
            }
        }
    }
}