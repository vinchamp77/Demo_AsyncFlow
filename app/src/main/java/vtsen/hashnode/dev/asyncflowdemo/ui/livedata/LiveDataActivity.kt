package vtsen.hashnode.dev.asyncflowdemo.ui.livedata

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import vtsen.hashnode.dev.asyncflowdemo.ui.common.CommonScreen
import vtsen.hashnode.dev.asyncflowdemo.ui.common.tag
import vtsen.hashnode.dev.asyncflowdemo.ui.theme.AsyncFlowDemoAppTheme

class LiveDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AsyncFlowDemoAppTheme(useSystemUIController = true) {
                CommonScreen {
                    LiveDataScreen()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "Activity onDestroy() called, isFinishing: $isFinishing")
    }
}
