package vtsen.hashnode.dev.asyncflowdemo.ui.flow

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import vtsen.hashnode.dev.asyncflowdemo.ui.common.tag

class FlowViewModel: ViewModel() {

    private var collectFlowJob: Job? = null

    /* flow (cold flow) */
    val flow: Flow<Int> = flow {
        repeat(10000) { value ->
            delay(1000)
            Log.d(tag, "[Flow]: emitting $value")
            emit(value)
        }
    }

    /* data holder - compose state */
    private val _state: MutableState<Int?> = mutableStateOf(null)
    val state: State<Int?> = _state

    fun viewModelScopeCollectFlow() {
        cancelCollectFlow()
        collectFlowJob = viewModelScope.launch {
            flow.collect { value ->
                Log.d(tag, "[viewModelScope]: Assigning $value to _stateFlow.value")
                _state.value = value
            }
        }
    }

    fun launchWhenStartedCollectFlow(lifeCycleScope: LifecycleCoroutineScope) {
        cancelCollectFlow()
        collectFlowJob = lifeCycleScope.launchWhenStarted {
            flow.collect { value ->
                Log.d(tag, "[launchWhenStarted]: Assigning $value to _state.value")
                _state.value = value
            }
        }
    }

    fun repeatOnCycleStartedCollectFlow(lifeCycleScope: LifecycleCoroutineScope, lifeCycle: Lifecycle) {
        cancelCollectFlow()
        collectFlowJob = lifeCycleScope.launch {
            lifeCycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect { value ->
                    Log.d(tag, "[repeatOnCycleStarted]: Assigning $value to _state.value")
                    _state.value = value
                }
            }
        }
    }

    fun cancelCollectFlow() {
        collectFlowJob?.cancel()
    }

    /* data holder - state flow (hot flow) */
    private val _stateFlow = MutableStateFlow<Int?>(null)
    val stateFlow = _stateFlow.asStateFlow()

    fun viewModelScopeCollectFlowToStateFlow() {
        cancelCollectFlow()
        collectFlowJob = viewModelScope.launch {
            flow.collect { value ->
                Log.d(tag, "[viewModelScope]: Assigning $value to _stateFlow.value")
                _stateFlow.value = value
            }
        }
    }

    /* shared flow (hot flow) */
    private val _sharedFlow = MutableSharedFlow<Int>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun viewModelScopeCollectFlowToSharedFlow() {
        cancelCollectFlow()
        collectFlowJob = viewModelScope.launch {
            flow.collect { value ->
                Log.d(tag, "[viewModelScope]: Emitting $value to _sharedFlow")
                _sharedFlow.emit(value)
            }
        }
    }

}