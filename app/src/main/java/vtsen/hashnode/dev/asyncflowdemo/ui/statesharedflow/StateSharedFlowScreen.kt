package vtsen.hashnode.dev.asyncflowdemo.ui.statesharedflow

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import vtsen.hashnode.dev.asyncflowdemo.ui.common.TextWidget
import vtsen.hashnode.dev.asyncflowdemo.ui.common.tag

@Composable
fun StateSharedFlowScreen() {
    val viewModel: StateSharedFlowViewModel = viewModel()
    val lifeCycle = LocalLifecycleOwner.current.lifecycle

    var composeStateValue by remember { mutableStateOf<Int?>(null) }

    /* collect flow (cold flow) */
    var startCollectFlow by remember { mutableStateOf(false)}
    if(startCollectFlow) {
        LaunchedEffect(true) {
            lifeCycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.flow.collect {
                    Log.d(tag, "[CollectFlow]: Assigning $it to composeStateValue")
                    composeStateValue = it
                }
            }
        }
    }

    /* collect shared flow (hot flow) */
    var startCollectSharedFlow by remember { mutableStateOf(false)}
    if(startCollectSharedFlow) {
        LaunchedEffect(true) {
            lifeCycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.sharedFlow.collect {
                    Log.d(tag, "[CollectSharedFlow]: Assigning $it to composeStateValue")
                    composeStateValue = it
                }
            }
        }
    }

    /* collect state flow (hot flow) */
    var startCollectStateFlow by remember { mutableStateOf(false)}
    if(startCollectStateFlow) {
        LaunchedEffect(true) {
            lifeCycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect {
                    Log.d(tag, "[CollectSharedFlow]: Assigning $it to composeStateValue")
                    composeStateValue = it
                }
            }
        }
    }

    Column {
        TextWidget(
            title="[ComposeState]",
            text = composeStateValue.toString(),
            tag = tag,
        )

        Divider(thickness = 3.dp)

        Button(onClick = {
            startCollectFlow = true
        }) {
            Text(text = "[CollectFlow] - Start")
        }

        Button(onClick = {
            startCollectFlow = false
        }) {
            Text(text = "[CollectFlow] - Stop")
        }

        Divider(thickness = 3.dp)

        Button(onClick = {
            viewModel.emitSharedFlow()
        }) {
            Text(text = "[EmitSharedFlow] - Start")
        }

        Button(onClick = {
            viewModel.stopEmitSharedFlow()
        }) {
            Text(text = "[EmitSharedFlow] - Stop")
        }

        Divider(thickness = 3.dp)

        Button(onClick = {
            startCollectSharedFlow = true
        }) {
            Text(text = "[CollectSharedFlow] - Start")
        }

        Button(onClick = {
            startCollectSharedFlow = false
        }) {
            Text(text = "[CollectSharedFlow] - Stop")
        }

        Divider(thickness = 3.dp)

        Button(onClick = {
            viewModel.collectFlowToStateFlow()
        }) {
            Text(text = "[CollectFlowToStateFlow] - Start")
        }

        Button(onClick = {
            viewModel.stopCollectFlowToStateFlow()
        }) {
            Text(text = "[CollectFlowToStateFlow] - Stop")
        }

        Button(onClick = {
            startCollectStateFlow = true
        }) {
            Text(text = "[CollectStateFlow] - Start")
        }

        Button(onClick = {
            startCollectStateFlow = false
        }) {
            Text(text = "[CollectStateFlow] - Stop")
        }
    }
}
