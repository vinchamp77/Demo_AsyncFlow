package vtsen.hashnode.dev.asyncflowdemo.ui.flow

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FlowScreen() {
    val viewModel: FlowViewModel = viewModel()
    //val intFloeState = viewModel.intFlow.collectAsState(initial = null)
    
    Column {
        var showText by remember { mutableStateOf(true) }

        if (showText) {
            //LiveDataTextWidget(text = intFloeState.value.toString())
        }

        Button(onClick = {showText = true}) {
            Text(text = "Show Text")
        }

        Button(onClick = {showText = false}) {
            Text(text = "Hide Text")
        }

        Button(onClick = {
            Thread.sleep(3000)
        }) {
            Text(text = "Simulate Busy UI")
        }
    }

}
