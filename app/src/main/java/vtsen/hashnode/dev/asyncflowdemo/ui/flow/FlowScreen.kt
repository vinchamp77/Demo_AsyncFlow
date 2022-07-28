package vtsen.hashnode.dev.asyncflowdemo.ui.flow

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
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

    var flowCollectAsState : State<Int?>? = null
    var startCollectingAsState by remember { mutableStateOf(false)}

    if(startCollectingAsState) {
        flowCollectAsState = viewModel.flow.collectAsStateWithLogging(initial = null)
    }
    
    Column {
        TextWidget(
            title="[ViewModelCollect]",
            text = flowCollectAsState?.value.toString(),
            tag = tag,
        )

        TextWidget(
            title="[CollectAsState]",
            text = viewModel.state.value.toString(),
            tag = tag,
        )

        Divider()

        Button(onClick = {
            viewModel.collectFlow()
        }) {
            Text(text = "[ViewModel] Collect Flow")
        }

        Button(onClick = {
            viewModel.cancelCollectFlow()
        }) {
            Text(text = "[ViewModel] Cancel Collect Flow")
        }

        Button(onClick = {
            startCollectingAsState = true
        }) {
            Text(text = "Start collectAsState()")
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