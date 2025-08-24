package com.ritesrport.skinsscoreboard.view.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ritesrport.skinsscoreboard.view.states.HoleInputState
import com.ritesrport.skinsscoreboard.view.states.HoleInputValidationState

@Composable
fun HoleInput(
    holeInputState: HoleInputState?,
    onHoleInputChanged: (String) -> Unit,
    onCompletePlayerInput: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {
        if (holeInputState != null) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .wrapContentHeight()
                    .padding(12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Hole no. ${holeInputState.holeData.holeNumber}",
                    fontSize = 24.sp,
                )
                Text("Par: ${holeInputState.holeData.par}")
                Text(
                    text = holeInputState.player.name,
                    modifier = modifier.padding(vertical = 8.dp)
                )
                OutlinedTextField(
                    holeInputState.holeInput,
                    onHoleInputChanged,
                    placeholder = { Text("Enter the number of strokes") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
                val enabled = holeInputState.inputValidation is HoleInputValidationState.Valid

                OutlinedButton(enabled = enabled, onClick = onCompletePlayerInput) {
                    Text("OK")
                }
            }
        } else {
            CircularProgressIndicator()
        }
    }
}