package vtsen.hashnode.dev.asyncflowdemo.ui.combinemergezipflow

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import vtsen.hashnode.dev.asyncflowdemo.ui.common.tag

@Composable
fun CombineMergeZipFlowScreen() {
    val viewModel: CombineMergeZipFlowViewModel = viewModel()

    var startCollectCombineFlow by remember { mutableStateOf(false)}
    if(startCollectCombineFlow) {
        LaunchedEffect(true) {
            viewModel.combineFlow.collect { value ->
                Log.d(tag, "[Combine Flow]: $value")
            }
        }
    }

    var startCollectMergeFlow by remember { mutableStateOf(false)}
    if(startCollectMergeFlow) {
        LaunchedEffect(true) {
            viewModel.mergeFlow.collect { value ->
                Log.d(tag, "[Merge Flow]: $value")
            }
        }
    }

    var startCollectZipFlow by remember { mutableStateOf(false)}
    if(startCollectZipFlow) {
        LaunchedEffect(true) {
            viewModel.zipFlow.collect { value ->
                Log.d(tag, "[Zip Flow]: $value")
            }
        }
    }

    Column {

        Button(onClick = {
            startCollectCombineFlow = true
        }) {
            Text(text = "[Combine Flow] Start")
        }

        Button(onClick = {
            startCollectCombineFlow = false
        }) {
            Text(text = "[Combine Flow] Stop")
        }

        Divider(thickness = 3.dp)

        Button(onClick = {
            startCollectMergeFlow = true
        }) {
            Text(text = "[Merge Flow] Start")
        }

        Button(onClick = {
            startCollectMergeFlow = false
        }) {
            Text(text = "[Merge Flow] Stop")
        }

        Divider(thickness = 3.dp)

        Button(onClick = {
            startCollectZipFlow = true
        }) {
            Text(text = "[Zip Flow] Start")
        }

        Button(onClick = {
            startCollectZipFlow = false
        }) {
            Text(text = "[Zip Flow] Stop")
        }
    }
}

