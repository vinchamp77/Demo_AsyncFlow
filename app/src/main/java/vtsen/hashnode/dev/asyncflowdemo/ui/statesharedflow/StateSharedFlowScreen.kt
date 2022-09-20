package vtsen.hashnode.dev.asyncflowdemo.ui.statesharedflow

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import vtsen.hashnode.dev.asyncflowdemo.ui.common.TextWidget
import vtsen.hashnode.dev.asyncflowdemo.ui.common.tag
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@Composable
fun StateSharedFlowScreen() {
    val viewModel: StateSharedFlowViewModel = viewModel()

    val lifeCycleScope = LocalLifecycleOwner.current.lifecycleScope
    val lifeCycle = LocalLifecycleOwner.current.lifecycle

    /* collect flow (cold flow) as state - convert flow to state */
    var flowCollectAsState : State<Int?>? = null
    var startCollectingAsState by remember { mutableStateOf(false)}
    if(startCollectingAsState) {
        flowCollectAsState = viewModel.flow.collectAsStateWithLogging(initial = null)
    }

    /* collect state flow (hot flow) - convert state flow to state */
    var stateFlowInt: Int? by remember { mutableStateOf(null) }
    var startCollectingStateFlow by remember { mutableStateOf(false)}
    if(startCollectingStateFlow) {
            LaunchedEffect(true) {
            lifeCycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect {
                    Log.d(tag, "[CollectStateFlow]: Assigning $it to stateFlowInt")
                    stateFlowInt = it
                }
            }
        }
    }

    /* collect shared flow (hot flow) - convert shared flow to state */
    var sharedFlowInt: Int? by remember { mutableStateOf(null) }
    var startCollectingSharedFlow by remember { mutableStateOf(false)}
    if(startCollectingSharedFlow) {
        LaunchedEffect(true) {
            lifeCycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.sharedFlow.collect {
                    Log.d(tag, "[CollectSharedFlow]: Assigning $it to sharedFlowInt")
                    sharedFlowInt = it
                }
            }
        }
    }

    Column {
        TextWidget(
            title="[CollectAsState]",
            text = flowCollectAsState?.value.toString(),
            tag = tag,
        )

        TextWidget(
            title="[ViewModelState]",
            text = viewModel.state.value.toString(),
            tag = tag,
        )

        TextWidget(
            title="[CollectStateFlow]",
            text = stateFlowInt.toString(),
            tag = tag,
        )

        TextWidget(
            title="[CollectSharedFlow]",
            text = sharedFlowInt.toString(),
            tag = tag,
        )


        Divider()

        Button(onClick = {
            startCollectingAsState = true
        }) {
            Text(text = "[collectAsState] Start")
        }

        Button(onClick = {
            startCollectingAsState = false
        }) {
            Text(text = "[collectAsState] Stop")
        }

        Button(onClick = {
            viewModel.viewModelScopeCollectFlow()
        }) {
            Text(text = "[viewModelScope] Collect Flow")
        }

        Button(onClick = {
            viewModel.launchWhenStartedCollectFlow(lifeCycleScope)
        }) {
            Text(text = "[launchWhenStarted] Collect Flow")
        }

        Button(onClick = {
            viewModel.repeatOnCycleStartedCollectFlow(lifeCycleScope, lifeCycle)
        }) {
            Text(text = "[repeatOnCycleStarted] Collect Flow")
        }

        Divider()

        Button(onClick = {
            viewModel.viewModelScopeCollectFlowToStateFlow()
        }) {
            Text(text = "[viewModelScope] Collect Flow To State Flow")
        }

        Button(onClick = {
            startCollectingStateFlow = true
        }) {
            Text(text = "[repeatOnCycleStarted] Collect State Flow")
        }

        Button(onClick = {
            startCollectingStateFlow = false
        }) {
            Text(text = "[repeatOnCycleStarted] Collect State Flow - Stop")
        }

        Button(onClick = {
            viewModel.viewModelScopeCollectFlowToSharedFlow()
        }) {
            Text(text = "[viewModelScope] Collect Flow To Shared Flow")
        }

        Button(onClick = {
            startCollectingSharedFlow = true
        }) {
            Text(text = "[repeatOnCycleStarted] Collect Shared Flow")
        }

        Button(onClick = {
            startCollectingSharedFlow = false
        }) {
            Text(text = "[repeatOnCycleStarted] Collect Shared Flow - Stop")
        }

        Divider()

        Button(onClick = {
            viewModel.cancelCollectFlow()
        }) {
            Text(text = "Cancel Collect Flow")
        }
    }
}


@Composable
fun <T:R, R> Flow<T>.collectAsStateWithLogging(
    initial: R,
    context: CoroutineContext = EmptyCoroutineContext
): State<R> = produceState(initial, this, context) {
    if (context == EmptyCoroutineContext) {
        collect {
            Log.d(tag, "[CollectAsState]: Assigning $it to state.value")
            value = it
        }
    } else withContext(context) {
        collect {
            Log.d(tag, "[CollectAsState]: Assigning $it to state.value")
            value = it
        }
    }
}