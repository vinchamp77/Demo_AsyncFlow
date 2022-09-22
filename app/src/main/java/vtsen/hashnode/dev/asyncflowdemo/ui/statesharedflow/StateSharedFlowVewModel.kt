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
            flow.collect { value ->
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

    /* shared flow (hot flow) - from share in */
    private var _sharedFlowFromShareIn :SharedFlow<Int>? = null
    val sharedFlowFromShareIn
        get() = _sharedFlowFromShareIn

    fun convertToSharedFlowUsingShareIn() {

        if(_sharedFlowFromShareIn != null) return

        // calling this multiple times creates multiple cold flow streams, thus leaking
        _sharedFlowFromShareIn = flow.shareIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly
        )
    }

    /* state flow (hot flow) - data holder - from state in (started eagerly) */
    private var _stateFlowFromStateIn: StateFlow<Int>? = null
    val stateFlowFromStateIn
        get() = _stateFlowFromStateIn

    fun convertToStateFlowUsingStateIn() {

        if(_stateFlowFromStateIn != null) return

        // calling this multiple times creates multiple cold flow streams, thus leaking
        _stateFlowFromStateIn = flow.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = -1)
    }

    /* state flow (hot flow) - data holder - from state in (started while subscribed) */
    private var _stateFlowFromStateInWhileSubcribe: StateFlow<Int>? = null
    val stateFlowFromStateInWhileSubcribe
        get() = _stateFlowFromStateInWhileSubcribe

    fun convertToStateFlowUsingStateInWhileSubcribe() {

        if(_stateFlowFromStateInWhileSubcribe != null) return

        // calling this multiple times creates multiple cold flow streams, thus leaking
        _stateFlowFromStateInWhileSubcribe = flow.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = -1)
    }
}