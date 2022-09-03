package vtsen.hashnode.dev.asyncflowdemo.ui.channel

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import vtsen.hashnode.dev.asyncflowdemo.ui.common.TextWidget
import vtsen.hashnode.dev.asyncflowdemo.ui.common.tag

@Composable
fun ChannelScreen() {
    val viewModel: ChannelViewModel = viewModel()

    Column {
        TextWidget(
            title="[ViewModelState]",
            text = viewModel.state.value.toString(),
            tag = tag,
        )

        TextWidget(
            title="[ViewModelAnotherState]",
            text = viewModel.anotherState.value.toString(),
            tag = tag,
        )

        Divider()

        Button(onClick = {
            viewModel.channelSend()
        }) {
            Text(text = "Channel Send")
        }

        Button(onClick = {
            viewModel.cancelChannelSend()
        }) {
            Text(text = "Cancel Channel Send")
        }

        Divider()

        Button(onClick = {
            viewModel.channelReceive()
        }) {
            Text(text = "Channel Receive")
        }

        Button(onClick = {
            viewModel.cancelChannelReceive()
        }) {
            Text(text = "Cancel Channel Receive")
        }

        Divider()

        Button(onClick = {
            viewModel.channelAnotherReceive()
        }) {
            Text(text = "Channel Another Receive")
        }

        Button(onClick = {
            viewModel.cancelChannelAnotherReceive()
        }) {
            Text(text = "Cancel Channel Another Receive")
        }
    }
}

