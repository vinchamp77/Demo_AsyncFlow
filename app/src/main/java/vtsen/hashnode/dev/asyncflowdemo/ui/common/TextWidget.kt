package vtsen.hashnode.dev.asyncflowdemo.ui.common

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun TextWidget(title: String, text: String, tag: String){
    Column {
        Text(text = title)
        Log.d(tag, "$title is recomposed with text $text")
        Text(text = text)
    }
}