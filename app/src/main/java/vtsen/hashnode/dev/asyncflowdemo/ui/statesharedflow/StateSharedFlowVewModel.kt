package vtsen.hashnode.dev.asyncflowdemo.ui.statesharedflow

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import vtsen.hashnode.dev.asyncflowdemo.ui.common.tag

class StateSharedFlowViewModel: ViewModel() {

    private var job: Job? = null

    /* flow (cold flow) */
    val flow: Flow<Int> = flow {
        repeat(10000) { value ->
            delay(1000)
            Log.d(tag, "[Flow]: emitting $value")
            emit(value)
        }
    }

    /* shared flow (hot flow) */
    private val _sharedFlow = MutableSharedFlow<Int>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun emitSharedFlow() {
        stopEmitSharedFlow()
        job = viewModelScope.launch {
            repeat(10000) { value ->
                delay(1000)
                Log.d(tag, "[SharedFlow]: emitting $value")
                _sharedFlow.emit(value)
            }
        }
    }

    fun stopEmitSharedFlow() = job?.cancel()

    /* state flow (hot flow) - data holder */
    private val _stateFlow = MutableStateFlow<Int?>(null)
    val stateFlow = _stateFlow.asStateFlow()

    fun collectFlowToStateFlow() {
        stopCollectFlowToStateFlow()
        job = viewModelScope.launch {
            flow.collect { value ->
                Log.d(tag, "[StateFlow]: Assigning $value to _stateFlow.value")
                _stateFlow.value = value
            }
        }
    }

    fun stopCollectFlowToStateFlow() = job?.cancel()
}