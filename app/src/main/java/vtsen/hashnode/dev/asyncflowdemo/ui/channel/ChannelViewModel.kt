package vtsen.hashnode.dev.asyncflowdemo.ui.channel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import vtsen.hashnode.dev.asyncflowdemo.ui.common.tag

class ChannelViewModel: ViewModel() {

    private var sendJob: Job? = null
    private var receiveJob: Job? = null
    private var anotherReceiveJob: Job? = null

    private val channel = Channel<Int>()

    private val _state: MutableState<Int?> = mutableStateOf(null)
    val state: State<Int?> = _state

    private val _anotherState: MutableState<Int?> = mutableStateOf(null)
    val anotherState: State<Int?> = _anotherState


    fun channelSend() {
        cancelChannelSend()
        sendJob = viewModelScope.launch {
            repeat(10000) { value ->
                delay(1000)
                Log.d(tag, "[Channel]: send $value")
                channel.send(value)
            }
        }
    }

    fun cancelChannelSend() {
        sendJob?.cancel()
    }

    fun channelReceive() {
        cancelChannelReceive()
        receiveJob = viewModelScope.launch {
            while(true) {
                val value = channel.receive()
                Log.d(tag, "[Channel]: receive $value")
                _state.value = value
            }
        }
    }

    fun cancelChannelReceive() {
        receiveJob?.cancel()
    }

    fun channelAnotherReceive() {
        cancelChannelAnotherReceive()
        anotherReceiveJob = viewModelScope.launch {
            while(true) {
                val value = channel.receive()
                Log.d(tag, "[Channel]: another receive $value")
                _anotherState.value = value
            }
        }
    }

    fun cancelChannelAnotherReceive() {
        anotherReceiveJob?.cancel()
    }
}