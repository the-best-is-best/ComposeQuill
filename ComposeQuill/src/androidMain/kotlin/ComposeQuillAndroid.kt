import androidx.activity.ComponentActivity
import io.github.vinceglb.filekit.core.FileKit

class ComposeQuillAndroid {
    companion object {
        internal lateinit var appContext: ComponentActivity

        fun init(context: ComponentActivity) {
            appContext = context
            FileKit.init(context)
        }
    }
}