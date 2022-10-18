package vtsen.hashnode.dev.asyncflowdemo.ui.sharedstateflow

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
fun SharedStateFlowScreen() {
    val viewModel: SharedStateFlowViewModel = viewModel()
    val lifeCycle = LocalLifecycleOwner.current.lifecycle

    /* compose state - data holder */
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

        /* Using collectAsStateWithLifecycle() as an alternative
        val stateFlowValue by viewModel.stateFlow.collectAsStateWithLifecycle()
        composeStateValue  = stateFlowValue
       */
    }



    /* collect shared flow (hot flow) - converted from share in */
    var startCollectSharedFlowFromShareIn by remember { mutableStateOf(false)}
    if(startCollectSharedFlowFromShareIn) {
        LaunchedEffect(true) {
            lifeCycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.sharedFlowFromShareIn?.collect {
                    Log.d(tag, "[CollectSharedFlowFromShareIn]: Assigning $it to composeStateValue")
                    composeStateValue = it
                }
            }
        }
    }

    /* collect state flow (hot flow) - converted from state in */
    var startCollectStateFlowFromStateIn by remember { mutableStateOf(false)}
    if(startCollectStateFlowFromStateIn) {
        LaunchedEffect(true) {
            lifeCycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.stateFlowFromStateIn?.collect {
                    Log.d(tag, "[CollectStateFlowFromStateIn]: Assigning $it to composeStateValue")
                    composeStateValue = it
                }
            }
        }
    }

    /* collect state flow (hot flow) - converted from state in - while subscribed */
    var startCollectStateFlowFromStateInWhileSubcribe by remember { mutableStateOf(false)}
    if(startCollectStateFlowFromStateInWhileSubcribe) {
        LaunchedEffect(true) {
            lifeCycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.stateFlowFromStateInWhileSubcribe?.collect {
                    Log.d(tag, "[CollectStateFlowFromStateInWhileSubcribe]: Assigning $it to composeStateValue")
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
            Text(text = "[ConvertStateFlow] - Start")
        }

        Button(onClick = {
            viewModel.stopCollectFlowToStateFlow()
        }) {
            Text(text = "[ConvertStateFlow] - Stop")
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

        Divider(thickness = 3.dp)

        Button(onClick = {
            viewModel.convertToSharedFlowUsingShareIn()
        }) {
            Text(text = "[ConvertSharedFlow - ShareIn] - Start")
        }

        Button(onClick = {
            startCollectSharedFlowFromShareIn = true
        }) {
            Text(text = "[CollectSharedFlow - ShareIn] - Start")
        }

        Button(onClick = {
            startCollectSharedFlowFromShareIn = false
        }) {
            Text(text = "[CollectSharedFlow - ShareIn] - Stop")
        }

        Divider(thickness = 3.dp)

        Button(onClick = {
            viewModel.convertToStateFlowUsingStateIn()
        }) {
            Text(text = "[ConvertStateFlow - StateIn] - Start")
        }

        Button(onClick = {
            startCollectStateFlowFromStateIn = true
        }) {
            Text(text = "[CollectStateFlow - StateIn] - Start")
        }

        Button(onClick = {
            startCollectStateFlowFromStateIn = false
        }) {
            Text(text = "[CollectStateFlow - StateIn] - Stop")
        }

        Divider(thickness = 3.dp)

        Button(onClick = {
            viewModel.convertToStateFlowUsingStateInWhileSubcribe()
        }) {
            Text(text = "[ConvertStateFlow - StateInWhileSubcribe] - Start")
        }

        Button(onClick = {
            startCollectStateFlowFromStateInWhileSubcribe = true
        }) {
            Text(text = "[CollectSateFlow - StateInWhileSubcribe] - Start")
        }

        Button(onClick = {
            startCollectStateFlowFromStateInWhileSubcribe = false
        }) {
            Text(text = "[CollectStateFlow - StateInWhileSubcribe] - Stop")
        }
    }
}
