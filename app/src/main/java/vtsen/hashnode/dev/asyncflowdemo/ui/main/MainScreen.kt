package vtsen.hashnode.dev.asyncflowdemo.ui.main

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import vtsen.hashnode.dev.asyncflowdemo.ui.channel.ChannelActivity
import vtsen.hashnode.dev.asyncflowdemo.ui.flow.FlowActivity
import vtsen.hashnode.dev.asyncflowdemo.ui.livedata.LiveDataActivity
import vtsen.hashnode.dev.asyncflowdemo.ui.sharedstateflow.SharedStateFlowActivity

@Composable
fun MainScreen() {
    val context = LocalContext.current
    Column {
        Button(onClick = {
            context.startActivity(
                Intent(context, LiveDataActivity::class.java)
            )
        }) {
            Text(text = "Live Data Experiment")
        }

        Button(onClick = {
            context.startActivity(
                Intent(context, FlowActivity::class.java)
            )
        }) {
            Text(text = "Flow Experiment")
        }

        Button(onClick = {
            context.startActivity(
                Intent(context, ChannelActivity::class.java)
            )
        }) {
            Text(text = "Channel Experiment")
        }

        Button(onClick = {
            context.startActivity(
                Intent(context, SharedStateFlowActivity::class.java)
            )
        }) {
            Text(text = "State & Shared Flow Experiment")
        }
    }
}