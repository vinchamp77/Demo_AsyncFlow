package vtsen.hashnode.dev.asyncflowdemo.ui.combinemergezipflow

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.zip
import vtsen.hashnode.dev.asyncflowdemo.ui.common.tag

class CombineMergeZipFlowViewModel: ViewModel() {

    private val flow1: Flow<Int> = flow {
        repeat(10000) { value ->
            delay(1000)
            Log.d(tag, "[Flow1]: emitting $value")
            emit(value)
        }
    }


    private val flow2: Flow<Char> = flow {
        var value = 'A'
        while(true) {
            delay(2000)
            Log.d(tag, "[Flow2]: emitting $value")
            emit(value)
            if (value == 'Z') {
                value = 'A'
            } else {
                value += 1
            }
        }
    }

    val combineFlow = flow1.combine(flow2) { flow1value,flow2value  ->
        val newValue = "${flow1value}_${flow2value}"
        newValue
    }

    val mergeFlow = merge(flow1, flow2)

    val zipFlow = flow1.zip(flow2) { flow1value,flow2value  ->
        val newValue = "${flow1value}_${flow2value}"
        newValue
    }
}