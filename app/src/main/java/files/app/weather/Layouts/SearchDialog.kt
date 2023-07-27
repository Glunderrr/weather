package files.app.weather.Layouts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import files.app.weather.logic.API
import files.app.weather.ui.theme.DarkPink
import files.app.weather.ui.theme.Purple

@Composable
fun SearchDialog(visible: MutableState<Boolean>, data: API) {
    var dialogState by remember { mutableStateOf("") }
    var saveCity by remember { mutableStateOf(false) }
    AlertDialog(
        onDismissRequest = {
            visible.value = false
        },
        confirmButton = {
            TextButton(onClick = {
                data.searchByResponse(dialogState, saveCity)
                visible.value = false
            }) {
                Text(text = "OK", color = DarkPink)
            }
        },
        dismissButton = {
            TextButton(onClick = { visible.value = false }) {
                Text(text = "Cancel", color = DarkPink)
            }
        },
        title = {
            Text(
                text = "Enter the name of the city",
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
        }, text = {
            Column {
                TextField(value = dialogState, onValueChange = {
                    dialogState = it
                }, label = { Text(text = "Type text") })
                Row(Modifier.padding(top = 10.dp)) {
                    Checkbox(
                        checked = saveCity,
                        onCheckedChange = { isChecked -> saveCity = isChecked },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Purple
                        ),
                    )
                    Text(text = "Make the typed city the default?", fontSize = 14.sp)
                }
            }
        }
    )
}