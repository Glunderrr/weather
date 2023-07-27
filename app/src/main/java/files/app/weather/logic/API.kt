package files.app.weather.logic

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import kotlin.math.round


@SuppressLint("MutableCollectionMutableState")
class API(responseCityName: String, private val context: Context) {
    val mainCard: MutableState<MaxData> = mutableStateOf(MaxData())
    val days = mutableStateOf(mutableListOf<MaxDataWithHours>())
    val hours = mutableStateOf(mutableListOf<MiniData>())


    fun searchByResponse(responseString: String) {
        days.value.clear()
        hours.value.clear()
        val apiKey = "0e615d406b1546639df111028232107"
        val url =
            "https://api.weatherapi.com/v1/forecast.json?key=$apiKey&q=$responseString&days=10&aqi=no&alerts=no"
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
                val jsonDaysDATA = forecast.getJSONArray("forecastday")

                mainCard.value = MaxData(
                    actualCityName = location.getString("name"),
                    windSpeed = " wind " + (round(current.getDouble("wind_kph") / 0.36) / 10).toString() + "ms",
                    miniCardData = MiniData(
                        time = location.getString("localtime"),
                        imageURL = "https:" + condition.getString("icon"),
                        temperature = "  " + current.getInt("temp_c").toString() + "°",
                        weatherState = condition.getString("text"),
                    )
                )

                for (index in 0 until jsonDaysDATA.length()) {
                    val dayData = jsonDaysDATA.getJSONObject(index)
                    val day = dayData.getJSONObject("day")
                    val dayCondition = day.getJSONObject("condition")
                    val jsonHoursData = dayData.getJSONArray("hour")

                    val mutableListOfHours = mutableListOf<MiniData>()
                    for (i in 0 until jsonHoursData.length()) {
                        val hourData = jsonHoursData.getJSONObject(i)
                        val hourConditions = hourData.getJSONObject("condition")
                        val hourCard = MiniData(
                            time = hourData.getString("time"),
                            imageURL = "https:" + hourConditions.getString("icon"),
                            temperature = "${hourData.getInt("temp_c")}°",
                            weatherState = hourConditions.getString("text")
                        )
                        mutableListOfHours.add(hourCard)
                    }

                    days.value.add(
                        MaxDataWithHours(
                            listOfHours = mutableListOfHours,
                            maxCardData = MaxData(
                                actualCityName = mainCard.value.actualCityName,
                                windSpeed = " max wind " + (round(day.getDouble("maxwind_mph") / 0.36) / 10).toString() + "ms",
                                miniCardData = MiniData(
                                    time = dayData.getString("date"),
                                    imageURL = "https:" + dayCondition.getString("icon"),
                                    temperature = "${day.getInt("mintemp_c")}°/${day.getInt("maxtemp_c")}°",
                                    weatherState = dayCondition.getString("text")
                                )
                            )
                        )
                    )
                    if (index == 0) hours.value.addAll(mutableListOfHours)
                }
                Log.d("MY_PAIN_IN_API", "hoursList is empty: ${hours.value.isEmpty()}")
                Log.d("MY_API", "without errors")
            }, {
                responseErrorIndicator = true
                /*if(internetConnection)
                    Toast.makeText(
                        context,
                        "no internet connection",
                        Toast.LENGTH_SHORT
                    ).show()
                else*/ if (responseErrorIndicator && !internetConnection) Toast.makeText(
                    context,
                    "Incorrect city name",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("MY_API", "Name: $responseString; Error: $it")
            })
        queue.add(stringRequest)
    }

    fun updateList(newDay: MaxDataWithHours) {
        hours.value.clear()
        hours.value.addAll(newDay.listOfHours)
        mainCard.value = newDay.maxCardData
    }

    init {
        searchByResponse(responseCityName)
    }
}

