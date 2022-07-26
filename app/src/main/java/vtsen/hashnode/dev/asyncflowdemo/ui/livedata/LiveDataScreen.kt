package vtsen.hashnode.dev.asyncflowdemo.ui.livedata

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import vtsen.hashnode.dev.asyncflowdemo.ui.common.TextWidget
import vtsen.hashnode.dev.asyncflowdemo.ui.common.tag

@Composable
fun LiveDataScreen() {
    val viewModel: LiveDataViewModel = viewModel()
    val lifecycleOwner = LocalLifecycleOwner.current

    val manualObserveLiveDataState:MutableState<Int?> = remember { mutableStateOf(null) }
    val liveDataObserver = remember {
        Observer<Int> { value ->
            Log.d(tag, "[ManualObserver]: Assigning $value to manualObserveLiveDataState.value")
            manualObserveLiveDataState.value = value
        }
    }

    var observerAsState by remember { mutableStateOf(false)}
    var observeAsStateLiveData: MutableState<Int?>? = null

    if(observerAsState) {
        observeAsStateLiveData = viewModel.liveData.observeAsStateWithLogging(initial = null) as MutableState<Int?>
    }


    Column {
        TextWidget(
            title="[ManualObserver]",
            text = manualObserveLiveDataState.value.toString(),
            tag = tag,
        )

        TextWidget(
            title="[ObserveAsState]",
            text = observeAsStateLiveData?.value.toString(),
            tag = tag,
        )

        Divider()

        Button(onClick = {
            viewModel.streamLiveDataValue()
        }) {
            Text(text = "Stream LiveData setValue")
        }

        Button(onClick = {
            viewModel.streamLiveDataValueBackgroundFetch()
        }) {
            Text(text = "Stream LiveData setValue - background fetch")
        }

        Button(onClick = {
            viewModel.streamLiveDataPostValue()
        }) {
            Text(text = "Stream LiveData postValue - background")
        }

        Button(onClick = {
            viewModel.streamLiveDataPostValue()
        }) {
            Text(text = "Stream LiveData postValue - main")
        }

        Button(onClick = {
            viewModel.cancelStreaming()
        }) {
            Text(text = "Cancel Streaming")
        }

        Divider()

        Button(onClick = {
            Log.d(tag, "[Manual Observer]: Start observing...")
            viewModel.liveData.observe(lifecycleOwner, liveDataObserver)
        }) {
            Text(text = "Manually Start Observe LiveData")
        }

        Button(onClick = {
            Log.d(tag, "[Manual Observer]: Remove observer")
            viewModel.liveData.removeObserver(liveDataObserver)
        }) {
            Text(text = "Manually Remove Observer")
        }

        Button(onClick = {
            observerAsState = true
        }) {
            Text(text = "Observe As State")
        }

        Button(onClick = {
            Log.d(tag, "hasActiveObservers: ${viewModel.liveData.hasActiveObservers()}")
            Log.d(tag, "hasObservers: ${viewModel.liveData.hasObservers()}")
        }) {
            Text(text = "Print LiveData Observers Status")
        }
    }
}



@Composable
fun <R, T: R> LiveData<T>.observeAsStateWithLogging(initial: R): State<R> {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state = remember { mutableStateOf(initial) }
    DisposableEffect(this, lifecycleOwner) {
        val observer = Observer<T> {
            Log.d(tag, "[ObserveAsState]: Assigning $it to state.value")
            state.value = it
        }
        Log.d(tag, "[ObserveAsState]: Start observing...")
        observe(lifecycleOwner, observer)
        onDispose {
            Log.d(tag, "[ObserveAsState]: Remove observer")
            removeObserver(observer)
        }
    }
    return state
}