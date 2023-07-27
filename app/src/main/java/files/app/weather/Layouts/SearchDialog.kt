package files.app.weather.Layouts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import files.app.weather.logic.API
import files.app.weather.logic.responseErrorIndicator


@Composable
fun SearchDialog(visible: MutableState<Boolean>, data: API) {
    var dialogState by remember { mutableStateOf("") }
    if (visible.value) responseErrorIndicator.value = false
        AlertDialog(
            onDismissRequest = {
                visible.value = false
            },
            confirmButton = {
                TextButton(onClick = {
                    data.searchByResponse(dialogState)
                    visible.value = false
                }) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { visible.value = false }) {
                    Text(text = "Cancel")
                }
            },
            title = {
                Text(
                    text = "Enter the name of the city",
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .fillMaxWidth()
                )
            }, text = {
                Column {
                    TextField(value = dialogState, onValueChange = {
                        dialogState = it
                    }, label = { Text(text = "Typed text") })
                }
            }
        )
}

