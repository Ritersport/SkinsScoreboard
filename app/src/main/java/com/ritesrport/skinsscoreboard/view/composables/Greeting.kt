package com.ritesrport.skinsscoreboard.view.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ritesrport.skinsscoreboard.R
import com.ritesrport.skinsscoreboard.view.view_model.MainViewModel

@Composable
fun Greeting(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    var isGreeting by rememberSaveable { mutableStateOf(true) }

    if (isGreeting) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painterResource(R.drawable.hole),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier.fillMaxHeight()
            )
            Surface(
                modifier = modifier
                    .wrapContentSize()
                    .padding(12.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.background)
            ) {
                Column(
                    modifier = modifier
                        .background(MaterialTheme.colorScheme.background)
                        .wrapContentHeight()
                        .padding(24.dp)
                ) {
                    Text(text = stringResource(R.string.app_name),
                        fontSize = 28.sp,
                        modifier = modifier.align(Alignment.CenterHorizontally))
                    Text(
                        text = stringResource(R.string.greeting),
                        textAlign = TextAlign.Justify,
                        modifier = modifier.padding(vertical = 12.dp)
                        )
                    OutlinedButton(onClick = { isGreeting = false }) {
                        Text("Start")
                    }
                }
            }
        }
    } else {
        Game(viewModel)
    }

}