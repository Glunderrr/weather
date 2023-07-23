package files.app.weather.logic

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf


data class MaxCardData(
    val actualCityName: MutableState<String> = mutableStateOf(""),
    val time: MutableState<String> = mutableStateOf(""),
    val temperature: MutableState<String> = mutableStateOf(""),
    val temperatureFeelLike: MutableState<String> = mutableStateOf(""),
    val imageURL: MutableState<String> = mutableStateOf(""),
    val weatherState: MutableState<String> = mutableStateOf(""),
    val windSpeed: MutableState<String> = mutableStateOf("")
)

data class MiniCardData(
    val time: MutableState<String>,
    val imageURL: MutableState<String>,
    val temperature: MutableState<String>,
    val weatherState: MutableState<String>,
)