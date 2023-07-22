package files.app.weather.logic

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf


data class MainCardData(
    val actualCityName: MutableState<String> = mutableStateOf(""),
    val date: MutableState<String> = mutableStateOf(""),
    val temperature: MutableState<String> = mutableStateOf(""),
    val temperatureFeelLike: MutableState<String> = mutableStateOf(""),
    val imageURL: MutableState<String> = mutableStateOf(""),
    val weatherState: MutableState<String> = mutableStateOf(""),
    val windSpeed: MutableState<String> = mutableStateOf("")
)

data class CardData(
    val time: String,
    val imageURL: String,
    val temperature: String,
    val weatherState: String,
)