package vtsen.hashnode.dev.asyncflowdemo.ui.common

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun CommonScreen(content: @Composable () -> Unit) {
    var showContent by rememberSaveable { mutableStateOf(true) }
    val context = LocalContext.current

    Column(modifier = Modifier.verticalScroll(rememberScrollState())){
        if (showContent) {
            content()

            Divider(thickness = 3.dp)

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
                showContent = false
            }) {
                Text("Hide Content (Simulate Leaving Composition)")
            }
        }
        else {
            Button(onClick = {
                showContent = true
            }) {
                Text("Show Content")
            }
        }
    }
}