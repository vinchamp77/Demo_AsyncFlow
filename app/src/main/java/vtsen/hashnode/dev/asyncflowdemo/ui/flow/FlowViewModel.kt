package vtsen.hashnode.dev.asyncflowdemo.ui.flow

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FlowViewModel: ViewModel() {

    val intFlow: Flow<Int> = flow {
        repeat(10000) { value ->
            delay(1000)
            emit(value)
        }
    }
}