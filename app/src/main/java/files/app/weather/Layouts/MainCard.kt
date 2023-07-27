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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    val cardData = data.mainCard
    val searchDialogVisible = remember { mutableStateOf(false) }
    if (searchDialogVisible.value)
        SearchDialog(searchDialogVisible, data)

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
                Text(text = cardData.value.miniCardData.time, color = Color.White)
                Image(
                    painter = rememberImagePainter(cardData.value.miniCardData.imageURL),
                    contentDescription = "weatherIng", modifier = Modifier.size(35.dp)
                )
            }
            Text(
                text = cardData.value.actualCityName,
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
            Text(
                text = cardData.value.miniCardData.temperature,
                color = Color.White,
                fontSize = 75.sp
            )
            Text(
                text = cardData.value.miniCardData.weatherState,
                color = Color.White, fontSize = 20.sp
            )
            Text(
                text = cardData.value.windSpeed,
                color = Color.White
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, bottom = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { searchDialogVisible.value = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = "search",
                        tint = Color.White
                    )
                }
                IconButton(onClick = {
                    data.searchByResponse("Khmilnyk")
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