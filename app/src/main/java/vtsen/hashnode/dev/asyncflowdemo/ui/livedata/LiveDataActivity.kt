package vtsen.hashnode.dev.asyncflowdemo.ui.livedata

import android.os.Bundle
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
import vtsen.hashnode.dev.asyncflowdemo.ui.theme.AsyncFlowDemoAppTheme

class LiveDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AsyncFlowDemoAppTheme(useSystemUIController = true) {
                var showLiveDataScreen by rememberSaveable { mutableStateOf(true)}

                Column(modifier = Modifier.verticalScroll(rememberScrollState())){
                    if (showLiveDataScreen) {
                        LiveDataScreen()
                    }

                    Button(onClick = {
                        showLiveDataScreen = true
                    }) {
                        Text("Show LiveDataScreen")
                    }

                    Button(onClick = {
                        showLiveDataScreen = false
                    }) {
                        Text("Hide LiveDataScreen")
                    }
                }
            }
        }
    }
}
