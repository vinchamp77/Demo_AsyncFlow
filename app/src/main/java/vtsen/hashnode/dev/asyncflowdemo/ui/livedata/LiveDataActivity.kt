package vtsen.hashnode.dev.asyncflowdemo.ui.livedata

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import vtsen.hashnode.dev.asyncflowdemo.ui.common.TransparentActivity
import vtsen.hashnode.dev.asyncflowdemo.ui.theme.AsyncFlowDemoAppTheme

class LiveDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AsyncFlowDemoAppTheme(useSystemUIController = true) {
                var showLiveDataScreen by rememberSaveable { mutableStateOf(true)}
                val context = LocalContext.current

                Column(modifier = Modifier.verticalScroll(rememberScrollState())){
                    if (showLiveDataScreen) {
                        LiveDataScreen()

                        Button(onClick = {
                            Log.d(tag, "Simulate busy UI")
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

                        Button(onClick = {
                            showLiveDataScreen = false
                        }) {
                            Text("Hide LiveDataScreen (Simulate Leaving Composition)")
                        }
                    }
                    else {
                        Button(onClick = {
                            showLiveDataScreen = true
                        }) {
                            Text("Show LiveDataScreen")
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(tag, "Activity onDestroy() called")
        Thread.sleep(3000)
    }
}
