package files.app.weather

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


private fun getApiInfo(
    actualCityName: String,
    temperature: MutableState<String>,
    weatherImage: MutableState<String>,
    context: Context
) {
    val API_KEY = "bc6a2be105c94117a3b81433230707"
    val url = "https://api.weatherapi.com/v1/current.json?key=$API_KEY&q=$actualCityName&aqi=no"
    val queue = Volley.newRequestQueue(context)
    val stringRequest =
        StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val obj = JSONObject(response)
                val current = obj.getJSONObject("current")
                val img = current.getJSONObject("condition")
                temperature.value = current.getString("temp_c") + " C"
                weatherImage.value = img.getString("icon")
                Log.d("buttonFunction", "imgURL: ${weatherImage.value}")
                Log.d("buttonFunction", "Name: $actualCityName; Response: $response")
            }, {
                Log.d("buttonFunction", "Name: $actualCityName; Error: $it")
            })
    queue.add(stringRequest)
}

@Composable
fun GetLayout(context: Context) {
    val cities = arrayOf(
        "Vinnitsa",
        "Lutsk",
        "Dnipropetrovsk",
        "Donetsk",
        "Zhytomyr",
        "Uzhhorod",
        "Zaporizhzhia",
        "Ivanofrankivsk",
        "Kiev",
        "Kirovograd",
        "Luhansk",
        "Lviv",
        "Nikolaev",
        "Odesa",
        "Poltava",
        "Rivne",
        "Sumy",
        "Ternopil",
        "Kharkiv",
        "Kherson",
        "Khmelnytskyi",
        "Cherkasy",
        "Chernivtsi",
        "Chernihiv",
        "Simferopol"
    )
    val temperature = remember { mutableStateOf("Temperature") }
    val weatherImage = remember { mutableStateOf("") }
    var defaultName by remember { mutableStateOf("City") }
    var isOpen by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxSize(0.5f),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = temperature.value)
                Text(
                    text = defaultName,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .clickable { isOpen = true })
                DropdownMenu(
                    expanded = isOpen,
                    onDismissRequest = { isOpen = false },
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(0.3f)
                ) {
                    cities.forEach { city ->
                        DropdownMenuItem(
                            text = { Text(text = city) },
                            onClick = { isOpen = false;defaultName = city }
                        )
                    }
                }
                Image(
                    painter = rememberImagePainter("https:" + weatherImage.value),
                    contentDescription = "",
                    modifier = Modifier.size(64.dp)
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = { getApiInfo(defaultName, temperature, weatherImage, context) },
                modifier = Modifier.padding(bottom = 50.dp)
            ) {
                Text(text = "Get temperature in $defaultName")
            }
        }
    }
}