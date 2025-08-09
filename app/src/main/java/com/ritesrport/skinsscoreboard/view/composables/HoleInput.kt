package com.ritesrport.skinsscoreboard.view.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ritesrport.skinsscoreboard.domain.HoleInputIntent
import com.ritesrport.skinsscoreboard.view.view_model.MainViewModel

@Composable
fun HoleInput(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val state by viewModel.holeInputState.collectAsState()
    var okButtonEnabled by remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf("") }
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(vertical = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Hole no. ${state.holeData.holeNumber}",
                fontSize = 24.sp,
                modifier = modifier.padding(8.dp)
            )
            Text("Par: ${state.holeData.par}")
            Text(state.player.name)
            OutlinedTextField(
                textFieldValue,
                {
                    textFieldValue = it
                    val intValue = it.toIntOrNull()
                    okButtonEnabled = intValue in 1..1000
                },
                placeholder = { Text("Enter the number of strokes")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
            OutlinedButton(enabled = okButtonEnabled, onClick = {
                viewModel.processIntent(HoleInputIntent.CompletePlayerInput(textFieldValue.toInt()))
                textFieldValue = ""
                okButtonEnabled = false
            }) {
                Text("OK")
            }
        }
    }
}