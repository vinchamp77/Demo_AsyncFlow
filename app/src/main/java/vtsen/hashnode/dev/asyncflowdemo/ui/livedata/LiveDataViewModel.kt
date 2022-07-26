package vtsen.hashnode.dev.asyncflowdemo.ui.livedata

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import vtsen.hashnode.dev.asyncflowdemo.ui.common.tag
import kotlin.coroutines.CoroutineContext

class LiveDataViewModel: ViewModel() {
    private var job: Job? = null

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

                } else {
                    delay(1000)
                }
                Log.d(tag, "[ViewModel]: setValue to $value")
                _liveData.value = value
            }
        }
    }

    fun streamLiveDataValueBackgroundFetch(fast:Boolean = false) {
        cancelStreaming()
        job = viewModelScope.launch {
            repeat(10000) { value ->
                val data  = fetchDataAtBackground(value, fast)
                Log.d(tag, "[ViewModel]: setValue with $data")
                _liveData.value = data
            }
        }
    }

    private suspend fun fetchDataAtBackground(value: Int, fast: Boolean = false): Int {
        return withContext(Dispatchers.IO) {
            if (fast) {
                delay(1)
            } else {
                delay(1000)
            }
            return@withContext value
        }
    }

    fun streamLiveDataPostValue(
        context: CoroutineContext = Dispatchers.IO,
        fast:  Boolean = false) {

        cancelStreaming()
        job = viewModelScope.launch(context) {
            repeat(10000) { value ->
                if (fast) {
                    delay(1)

                } else {
                    delay(1000)
                }
                Log.d(tag, "[ViewModel]: postValue with $value")
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