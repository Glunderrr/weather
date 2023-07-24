package files.app.weather.logic

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import kotlin.math.round


class API(responseCityName: String, private val context: Context) {
    lateinit var mainCard: MaxData
    val days = mutableListOf<MaxDataWithHours>()
    val hours = mutableListOf<MiniData>()

    fun searchByResponse(responseString: String) {
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
                val jsonDaysDATA = forecast.getJSONArray("forecastday")

                mainCard = MaxData(
                    "" + location.getString("name"),
                    " wind " + (round(current.getDouble("wind_kph") / 0.36) / 10).toString() + "ms",
                    " Feels like ${current.getInt("feelslike_c")}°",
                    MiniData(
                        "" + location.getString("localtime"),
                        "https:" + condition.getString("icon"),
                        "  " + current.getInt("temp_c").toString() + "°",
                        "" + condition.getString("text"),
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
                            "" + hourData.getString("time"),
                            "https:" + hourConditions.getString("icon"),
                            "${hourData.getInt("temp_c")}°",
                            "" + hourConditions.getString("text")
                        )
                        mutableListOfHours.add(hourCard)
                    }

                    val dayCard = MaxDataWithHours(
                        MaxData(
                            mainCard.actualCityName,
                            " max wind " + (round(day.getDouble("maxwind_mph") / 0.36) / 10).toString() + "ms",
                            "",
                            MiniData(
                                dayData.getString("date"),
                                "https:" + dayCondition.getString("icon"),
                                "${day.getInt("mintemp_c")}°/${day.getInt("maxtemp_c")}°",
                                dayCondition.getString("text")
                            )
                        ), mutableListOfHours
                    )
                    days.add(dayCard)
                    if (index == 0) {
                        hours.addAll(mutableListOfHours)
                    }
                }
                Log.d("MY_PAIN_IN_API", "hoursList is empty: ${hours.isEmpty()}")
            }, {
                Log.d("MY_API", "Name: $responseString; Error: $it")
            })
        queue.add(stringRequest)
    }

    private fun clear() {
        days.clear()
        hours.clear()
    }

    fun updateList(newDay: MaxDataWithHours) {
        hours.clear()
        Log.d("CHANGE_IN_UPDATE_LIST","Old hours: ${hours}")
        hours.addAll(newDay.mutableListOfHours)
        Log.d("CHANGE_IN_UPDATE_LIST","New hours: ${hours}")
        Log.d("CHANGE_IN_UPDATE_LIST","Old Card: ${mainCard}")
        mainCard = newDay.maxCardData
        Log.d("CHANGE_IN_UPDATE_LIST","New Card: ${mainCard}")
    }

    init {
        searchByResponse(responseCityName)
    }
}