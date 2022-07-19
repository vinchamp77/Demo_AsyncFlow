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

    fun streamLiveDataValue() {
        cancelStreaming()
        job = viewModelScope.launch {
            repeat(10000) { value ->
                delay(1000)
                Log.d("LiveDataDebug", "setValue: $value")
                _liveData.value = value
            }
        }
    }

    fun streamLiveDataPostValue() {
        cancelStreaming()
        job = viewModelScope.launch(Dispatchers.IO) {
            repeat(10000) { value ->
                delay(1000)
                Log.d("LiveDataDebug", "postValue: $value")
                _liveData.postValue(value)
            }
        }
    }

    fun cancelStreaming() {
        job?.cancel()
    }
}