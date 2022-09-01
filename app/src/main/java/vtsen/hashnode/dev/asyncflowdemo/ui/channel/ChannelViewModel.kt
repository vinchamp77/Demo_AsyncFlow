package vtsen.hashnode.dev.asyncflowdemo.ui.channel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vtsen.hashnode.dev.asyncflowdemo.ui.common.tag

class ChannelViewModel: ViewModel() {

    private var job: Job? = null

    val flow: Flow<Int> = flow {
        repeat(10000) { value ->
            delay(1000)
            Log.d(tag, "[Flow]: emitting $value")
            emit(value)
        }
    }

    private val _state: MutableState<Int?> = mutableStateOf(null)
    val state: State<Int?> = _state

    fun viewModelScopeCollectFlow() {
        cancelCollectFlow()
        job = viewModelScope.launch {
            flow.collect { value ->
                Log.d(tag, "[viewModelScope]: Assigning $value to _state.value")
                _state.value = value
            }
        }
    }

    fun launchWhenStartedCollectFlow(lifeCycleScope: LifecycleCoroutineScope) {
        cancelCollectFlow()
        job = lifeCycleScope.launchWhenStarted {
            flow.collect { value ->
                Log.d(tag, "[launchWhenStarted]: Assigning $value to _state.value")
                _state.value = value
            }
        }
    }

    fun repeatOnCycleStartedCollectFlow(lifeCycleScope: LifecycleCoroutineScope, lifeCycle: Lifecycle) {
        cancelCollectFlow()
        job = lifeCycleScope.launch {
            lifeCycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect { value ->
                    Log.d(tag, "[repeatOnCycleStarted]: Assigning $value to _state.value")
                    _state.value = value
                }
            }
        }
    }

    fun cancelCollectFlow() {
        job?.cancel()
    }
}