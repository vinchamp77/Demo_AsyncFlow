package vtsen.hashnode.dev.asyncflowdemo.ui.flow

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
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

    val flowCollectAsState = viewModel.flow.collectAsStateWithLogging(initial = null)
    
    Column {
        TextWidget(
            title="[ViewModelCollect]",
            text = flowCollectAsState.value.toString(),
            tag = tag,
        )

        TextWidget(
            title="[CollectAsState]",
            text = viewModel.state.value.toString(),
            tag = tag,
        )

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
            value = it
        }
    }
}