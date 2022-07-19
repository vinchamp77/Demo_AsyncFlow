package vtsen.hashnode.dev.asyncflowdemo.ui.livedata

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import vtsen.hashnode.dev.asyncflowdemo.ui.common.TextWidget
import vtsen.hashnode.dev.asyncflowdemo.ui.common.TransparentActivity

@Composable
fun LiveDataScreen() {
    val viewModel: LiveDataViewModel = viewModel()
    val manualObserveLiveDataState:MutableState<Int?> = remember { mutableStateOf(null) }
    val observeAsStateLiveData = viewModel.liveData.myObserveAsState(initial = null)

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val liveDataObserver = remember {
        Observer<Int> { value ->
            manualObserveLiveDataState.value = value
        }
    }

    Column {
        TextWidget(
            title="manual observe",
            text = manualObserveLiveDataState.value.toString(),
            tag = "LiveDataDebug",
        )

        TextWidget(
            title="observe as state",
            text = observeAsStateLiveData.value.toString(),
            tag = "LiveDataDebug",
        )

        Button(onClick = {
            viewModel.streamLiveDataValue()
        }) {
            Text(text = "Stream LiveData setValue")
        }

        Button(onClick = {
            viewModel.streamLiveDataPostValue()
        }) {
            Text(text = "Stream LiveData postValue")
        }

        Button(onClick = {
            viewModel.cancelStreaming()
        }) {
            Text(text = "Cancel Streaming")
        }

        Button(onClick = {
            Log.d("LiveDataDebug", "hasActiveObservers: ${viewModel.liveData.hasActiveObservers()}")
            Log.d("LiveDataDebug", "hasObservers: ${viewModel.liveData.hasObservers()}")

            Log.d("LiveDataDebug", "Start observing...")
            viewModel.liveData.observe(lifecycleOwner, liveDataObserver)
        }) {
            Text(text = "Manually Start Observe LiveData")
        }

        Button(onClick = {

            Log.d("LiveDataDebug", "hasActiveObservers: ${viewModel.liveData.hasActiveObservers()}")
            Log.d("LiveDataDebug", "hasObservers: ${viewModel.liveData.hasObservers()}")
            Log.d("LiveDataDebug", "Remove observer")
            viewModel.liveData.removeObserver(liveDataObserver)
        }) {
            Text(text = "Manually Remove Observer")
        }

        Button(onClick = {
            Thread.sleep(3000)
        }) {
            Text(text = "Simulate Busy UI")
        }

        Button(onClick = {
            context.startActivity(
                Intent(context, TransparentActivity::class.java)
            )
        }) {
            Text(text = "Pause Activity")
        }
    }
}

@Composable
fun <R, T: R> LiveData<T>.myObserveAsState(initial: R): State<R> {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state = remember { mutableStateOf(initial) }
    DisposableEffect(this, lifecycleOwner) {
        val observer = Observer<T> {
            state.value = it
        }
        Log.d("LiveDataDebug", "Start observing...")
        observe(lifecycleOwner, observer)
        onDispose {
            Log.d("LiveDataDebug", "Remove observer")
            removeObserver(observer)
        }
    }
    return state
}