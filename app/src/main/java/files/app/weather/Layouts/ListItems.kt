package files.app.weather.Layouts

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import files.app.weather.logic.API
import files.app.weather.logic.MaxDataWithHours
import files.app.weather.logic.MiniData
import files.app.weather.ui.theme.BlueLight

@Composable
internal fun ListItem(data: MiniData) {
    Card(
        colors = CardDefaults.cardColors(BlueLight),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp, bottom = 3.dp),
    ) {
        ListItemContent(miniCardData = data)
    }
}

@Composable
internal fun ListItem(maxDataWithHours: MaxDataWithHours, data: API) {
    Card(
        colors = CardDefaults.cardColors(BlueLight),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp, bottom = 3.dp)
            .clickable { data.updateList(maxDataWithHours) }
    ) {
        ListItemContent(maxDataWithHours.maxCardData.miniCardData)
    }
}

@Composable
private fun ListItemContent(miniCardData: MiniData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)) {
            Text(miniCardData.time)
            Text(
                text = miniCardData.weatherState,
                color = Color.White,
                modifier = Modifier.fillMaxWidth(0.45f)
            )
        }
        Text(text = miniCardData.temperature, color = Color.White, fontSize = 25.sp)
        Image(
            painter = rememberImagePainter(miniCardData.imageURL),
            contentDescription = "weatherURL", modifier = Modifier.size(35.dp)
        )
    }
}