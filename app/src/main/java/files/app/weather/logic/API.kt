package files.app.weather.logic

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import kotlin.math.round

@SuppressLint("MutableCollectionMutableState")

class API(responseCityName: String, private val context: Context) {

    val card = MaxCardData()
    val days = mutableStateOf(mutableListOf<MiniCardData>())
    val hours = mutableStateOf(mutableListOf<MiniCardData>())

    init {
        update(responseCityName)
    }

    fun update(responseString: String) {
        clear()
        val apiKey = "0e615d406b1546639df111028232107"
        val url =
            "https://api.weatherapi.com/v1/forecast.json?key=$apiKey&q=$responseString&days=3&aqi=no&alerts=no"
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val obj = JSONObject(response)
                val location = obj.getJSONObject("location")
                val current = obj.getJSONObject("current")
                val condition = current.getJSONObject("condition")
                val forecast = obj.getJSONObject("forecast")
                val JSONDaysDATA = forecast.getJSONArray("forecastday")

                card.actualCityName.value = "" + location.getString("name")
                card.time.value = location.getString("localtime")
                card.temperature.value = "  " + current.getInt("temp_c").toString() + "°"
                card.temperatureFeelLike.value = " Feels like ${current.getInt("feelslike_c")}°"
                card.imageURL.value = "https:" + condition.getString("icon")
                card.weatherState.value = condition.getString("text")
                card.windSpeed.value =
                    " wind " + (round(current.getDouble("wind_kph") / 0.36) / 10).toString() + "ms"


                for (index in 0 until JSONDaysDATA.length()) {
                    val dayData = JSONDaysDATA.getJSONObject(index)
                    val day = dayData.getJSONObject("day")
                    val dayCondition = day.getJSONObject("condition")

                    val dayCard = MiniCardData(
                        mutableStateOf(dayData.getString("date")),
                        mutableStateOf("https:" + dayCondition.getString("icon")),
                        mutableStateOf("${day.getInt("mintemp_c")}°/${day.getInt("maxtemp_c")}°"),
                        mutableStateOf(dayCondition.getString("text"))
                    )
                    days.value.add(dayCard)

                    if (index == 0) {
                        val JSONHoursData = dayData.getJSONArray("hour")
                        for (i in 0..23 step 2) {
                            val hourData = JSONHoursData.getJSONObject(i)
                            val hourConditions = hourData.getJSONObject("condition")

                            val hourCard = MiniCardData(
                                mutableStateOf(hourData.getString("time")),
                                mutableStateOf("https:" + hourConditions.getString("icon")),
                                mutableStateOf("${hourData.getInt("temp_c")}°"),
                                mutableStateOf(hourConditions.getString("text"))
                            )
                            hours.value.add(hourCard)
                        }
                    }
                }
                Log.d("MY_PAIN_IN_API", "hours is empty: ${hours.value.isEmpty()}")
            }, {
                Log.d("MY_API", "Name: $responseString; Error: $it")
            })
        queue.add(stringRequest)
    }

    private fun clear() {
        days.value.clear()
        hours.value.clear()
    }
}