package com.ritesrport.skinsscoreboard.view.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ritesrport.skinsscoreboard.view.intents.HoleInputIntent
import com.ritesrport.skinsscoreboard.view.view_model.MainViewModel

@Composable
fun HoleInput(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val state by viewModel.holeInputState.collectAsState()
    var okButtonEnabled by rememberSaveable { mutableStateOf(false) }
    var textFieldValue by rememberSaveable { mutableStateOf("") }

    Surface(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .wrapContentHeight()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Hole no. ${state.holeData.holeNumber}",
                fontSize = 24.sp,
            )
            Text("Par: ${state.holeData.par}")
            Text(
                text = state.player.name,
                modifier = modifier.padding(vertical = 8.dp)
            )
            OutlinedTextField(
                textFieldValue,
                {
                    textFieldValue = it
                    val intValue = it.toIntOrNull()
                    okButtonEnabled = intValue in 1..1000
                },
                placeholder = { Text("Enter the number of strokes") },
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