package files.app.weather.logic

import android.annotation.SuppressLint

data class MiniData(
    val time: String,
    val imageURL: String,
    val temperature: String,
    val weatherState: String
)

data class MaxData(
    val actualCityName: String,
    val windSpeed: String,
    val temperatureFeelLike: String,
    val miniCardData: MiniData
)

@SuppressLint("MutableCollectionMutableState")
data class MaxDataWithHours(
    val maxCardData: MaxData,
    val mutableListOfHours: MutableList<MiniData> = mutableListOf()
)