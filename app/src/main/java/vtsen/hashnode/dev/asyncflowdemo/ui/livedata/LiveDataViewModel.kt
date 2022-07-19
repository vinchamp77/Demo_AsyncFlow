package vtsen.hashnode.dev.asyncflowdemo.ui.livedata

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class LiveDataViewModel: ViewModel() {
    var job: Job? = null

    private val _liveData = MutableLiveData<Int>()
    val liveData: LiveData<Int> = _liveData

    init {
        Log.d(tag, "[ViewModel]: created!")
    }

    fun streamLiveDataValue(fast:Boolean = false) {
        cancelStreaming()
        job = viewModelScope.launch {
            repeat(10000) { value ->
                if (fast) {
                    delay(1)
                    Log.d(tag, "[ViewModel]: setValue with 0.001s: $value")

                } else {
                    delay(1000)
                    Log.d(tag, "[ViewModel]: setValue with 1s: $value")
                }
                _liveData.value = value
            }
        }
    }

    fun streamLiveDataPostValue(fast:  Boolean = false) {
        cancelStreaming()
        job = viewModelScope.launch(Dispatchers.IO) {
            repeat(10000) { value ->
                if (fast) {
                    delay(1)
                    Log.d(tag, "[ViewModel]: postValue with 0.001s: $value")

                } else {
                    delay(1000)
                    Log.d(tag, "[ViewModel]: postValue with 1s: $value")
                }
                _liveData.postValue(value)
            }
        }
    }

    fun cancelStreaming() {
        job?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(tag, "[ViewModel]: destroyed")
    }
}