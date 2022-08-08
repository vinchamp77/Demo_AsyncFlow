package vtsen.hashnode.dev.asyncflowdemo.ui.flow

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import vtsen.hashnode.dev.asyncflowdemo.ui.common.TextWidget
import vtsen.hashnode.dev.asyncflowdemo.ui.common.tag
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@Composable
fun FlowScreen() {
    val viewModel: FlowViewModel = viewModel()

    val lifeCycleScope = LocalLifecycleOwner.current.lifecycleScope
    val lifeCycle = LocalLifecycleOwner.current.lifecycle

    var flowCollectAsState : State<Int?>? = null
    var startCollectingAsState by remember { mutableStateOf(false)}

    if(startCollectingAsState) {
        flowCollectAsState = viewModel.flow.collectAsStateWithLogging(initial = null)
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

        Divider()

        Button(onClick = {
            startCollectingAsState = true
        }) {
            Text(text = "[collectAsState]")
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