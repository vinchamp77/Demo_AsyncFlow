package vtsen.hashnode.dev.asyncflowdemo.ui.flow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import vtsen.hashnode.dev.asyncflowdemo.ui.theme.AsyncFlowDemoAppTheme

class FlowActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AsyncFlowDemoAppTheme(useSystemUIController = true) {
                FlowScreen()
            }
        }
    }
}
