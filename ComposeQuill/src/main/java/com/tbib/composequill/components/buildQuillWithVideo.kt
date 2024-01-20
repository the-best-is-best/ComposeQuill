package com.tbib.composequill.components

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.media.MediaMetadataRetriever
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import com.mohamedrejeb.richeditor.model.RichTextState
import com.tbib.composequill.QuillViewModel
import com.tbib.composequill.services.convertBase64ToVideo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import java.io.File


class ResolutionPolicy(val fitPolicy: FitPolicy, val aspectRatio: Float) {
    enum class FitPolicy {
        FIT_WIDTH,
        FIT_HEIGHT
    }

    fun apply(width: Int, height: Int): Pair<Int, Int> {
        return when (fitPolicy) {
            FitPolicy.FIT_WIDTH -> Pair(width, (width / aspectRatio).toInt())
            FitPolicy.FIT_HEIGHT -> Pair((height * aspectRatio).toInt(), height)
        }
    }
}


@SuppressLint("SuspiciousIndentation")
@Composable
internal fun BuildQuillWithVideo(
    viewModel: QuillViewModel,
    maxHeight: Dp,
    state: RichTextState,
    readOnly: Boolean,
) {
    val context = LocalContext.current
    var videoFile by remember {
        mutableStateOf<File?>(null)
    }
    var thumbnail by remember {
        mutableStateOf<Bitmap?>(null)
    }
    var resolution by remember { mutableStateOf(Pair(0, 0)) }
    val loading = viewModel.loading.observeAsState()
    val video = viewModel.video.observeAsState()



    LaunchedEffect(Dispatchers.IO) {
        convertBase64ToVideo(
            video.value!!,
            context.filesDir.absolutePath
        ) { file ->
            videoFile = file
            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(file.absolutePath)
            thumbnail = retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST)
            val width =
                retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)
                    ?.toInt() ?: 0
            val height =
                retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)
                    ?.toInt() ?: 0
            resolution = ResolutionPolicy(
                ResolutionPolicy.FitPolicy.FIT_WIDTH,
                width.toFloat() / height.toFloat()
            ).apply(
                width,
                height
            )
            delay(1000)
            viewModel.loading.value = false
        }
    }


    Box(
        modifier = Modifier
            .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
            .padding(10.dp)
    ) {


        LazyColumn(
            modifier = Modifier
                .heightIn(max = maxHeight),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                QuillEditorBuilder(state, readOnly)
                if (loading.value!! || videoFile == null) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .width(200.dp)
                            .height(10.dp),

                        )
                } else {
                    AndroidView(
                        factory = { context ->
                            VideoView(context).apply {
                                setVideoURI(videoFile!!.toUri())
                                setMediaController(MediaController(context))
                                background = BitmapDrawable(null, thumbnail!!)
                                layoutParams =
                                    ViewGroup.LayoutParams(resolution.first, resolution.second)

                                setOnPreparedListener {
                                    it.setOnVideoSizeChangedListener { _, _, _ ->
                                        it.start()
                                        background.alpha = 0

                                    }
                                }


                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

