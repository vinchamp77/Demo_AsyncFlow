package vtsen.hashnode.dev.asyncflowdemo.ui.flow

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vtsen.hashnode.dev.asyncflowdemo.ui.common.tag

class FlowViewModel: ViewModel() {

    private var job: Job? = null

    val flow: Flow<Int> = flow {
        repeat(10000) { value ->
            delay(1000)
            emit(value)
        }
    }

    private val _state: MutableState<Int?> = mutableStateOf(null)
    val state: State<Int?> = _state

    fun collectFlow() {
        cancelCollectFlow()
        job = viewModelScope.launch {
            flow.collect {
                Log.d(tag, "[ViewModelCollect]: Assigning $it to _state.value")
                _state.value = it
            }
        }
    }

    fun cancelCollectFlow() {
        job?.cancel()
    }
}