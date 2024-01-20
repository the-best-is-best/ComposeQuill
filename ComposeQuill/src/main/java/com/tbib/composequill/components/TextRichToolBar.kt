package com.tbib.composequill.components

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.FormatAlignCenter
import androidx.compose.material.icons.outlined.FormatAlignLeft
import androidx.compose.material.icons.outlined.FormatAlignRight
import androidx.compose.material.icons.outlined.FormatBold
import androidx.compose.material.icons.outlined.FormatItalic
import androidx.compose.material.icons.outlined.FormatListBulleted
import androidx.compose.material.icons.outlined.FormatListNumbered
import androidx.compose.material.icons.outlined.FormatSize
import androidx.compose.material.icons.outlined.FormatStrikethrough
import androidx.compose.material.icons.outlined.FormatUnderlined
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mohamedrejeb.richeditor.model.RichTextState
import com.tbib.composequill.QuillViewModel

data class TextRichToolBarStyle(
    val modifier: Modifier = Modifier,
    val iconColor: Color? = null,
    val selectedIconBackgroundColor: Color? = null,
    val iconSelectedColor: Color? = null

)

@SuppressLint("StateFlowValueCalledInComposition", "Recycle")
@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun TextRichToolBar(
    viewModel: QuillViewModel,
    state: RichTextState,
    onChange: () -> Unit,
    style: TextRichToolBarStyle = TextRichToolBarStyle()


) {
    val context = LocalContext.current
    val activity = context as Activity
    val myImagePicker = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { it ->
            val projection = arrayOf(MediaStore.Images.Media.DATA)

            val cursor = context.contentResolver.query(it, projection, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val pathIndex = it.getColumnIndex(MediaStore.Images.Media.DATA)
                    val path = it.getString(pathIndex)
                    viewModel.addImage(path)
                    onChange()
                }
            }
        }
    }
    val path = remember { mutableStateOf("") }
    val myVideoPicker = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { it ->
            val projection = arrayOf(MediaStore.Video.Media.DATA)

            val contentResolver = context.contentResolver
            val inputStream = contentResolver.openInputStream(uri)
            val size = inputStream!!.available()
            if (size > maxSize) {
                Toast.makeText(context, "File size should be less than 20 MB", Toast.LENGTH_SHORT)
                    .show()
                return@let
            }
            val cursor = context.contentResolver.query(it, projection, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val pathIndex = it.getColumnIndex(MediaStore.Video.Media.DATA)
                    path.value = it.getString(pathIndex)
                    viewModel.addVideo(path.value)
                    onChange()


                }
            }
        }
    }

    FlowRow(modifier = style.modifier, horizontalArrangement = Arrangement.Center) {

        RichTextStyleButton(
            onClick = {
                state.addParagraphStyle(
                    ParagraphStyle(
                        textAlign = TextAlign.Left,
                    )
                )

            },
            iconColor = style.iconColor,
            selectedIconBackgroundColor = style.selectedIconBackgroundColor,
            iconSelectedColor = style.iconSelectedColor,
            isSelected = state.currentParagraphStyle.textAlign == TextAlign.Left,
            icon = Icons.Outlined.FormatAlignLeft
        )

        RichTextStyleButton(
            onClick = {
                state.addParagraphStyle(
                    ParagraphStyle(
                        textAlign = TextAlign.Center
                    )
                )

            },
            iconColor = style.iconColor,
            selectedIconBackgroundColor = style.selectedIconBackgroundColor,
            iconSelectedColor = style.iconSelectedColor,
            isSelected = state.currentParagraphStyle.textAlign == TextAlign.Center,
            icon = Icons.Outlined.FormatAlignCenter
        )
        RichTextStyleButton(
            onClick = {
                state.addParagraphStyle(
                    ParagraphStyle(
                        textAlign = TextAlign.Right
                    )
                )
            },
            iconColor = style.iconColor,
            selectedIconBackgroundColor = style.selectedIconBackgroundColor,
            iconSelectedColor = style.iconSelectedColor,
            isSelected = state.currentParagraphStyle.textAlign == TextAlign.Right,
            icon = Icons.Outlined.FormatAlignRight
        )

        RichTextStyleButton(
            onClick = {
                state.toggleSpanStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold
                    )
                )
            },
            iconColor = style.iconColor,
            selectedIconBackgroundColor = style.selectedIconBackgroundColor,
            iconSelectedColor = style.iconSelectedColor,
            isSelected = state.currentSpanStyle.fontWeight == FontWeight.Bold,
            icon = Icons.Outlined.FormatBold
        )

        RichTextStyleButton(
            onClick = {
                state.toggleSpanStyle(
                    SpanStyle(
                        fontStyle = FontStyle.Italic
                    )
                )
            },
            iconColor = style.iconColor,
            selectedIconBackgroundColor = style.selectedIconBackgroundColor,
            iconSelectedColor = style.iconSelectedColor,
            isSelected = state.currentSpanStyle.fontStyle == FontStyle.Italic,
            icon = Icons.Outlined.FormatItalic
        )

        RichTextStyleButton(
            onClick = {
                state.toggleSpanStyle(
                    SpanStyle(
                        textDecoration = TextDecoration.Underline
                    )
                )
            },
            iconColor = style.iconColor,
            selectedIconBackgroundColor = style.selectedIconBackgroundColor,
            iconSelectedColor = style.iconSelectedColor,
            isSelected = state.currentSpanStyle.textDecoration?.contains(TextDecoration.Underline) == true,
            icon = Icons.Outlined.FormatUnderlined
        )

        RichTextStyleButton(
            onClick = {
                state.toggleSpanStyle(
                    SpanStyle(
                        textDecoration = TextDecoration.LineThrough
                    )
                )
            },
            iconColor = style.iconColor,
            selectedIconBackgroundColor = style.selectedIconBackgroundColor,
            iconSelectedColor = style.iconSelectedColor,
            isSelected = state.currentSpanStyle.textDecoration?.contains(TextDecoration.LineThrough) == true,
            icon = Icons.Outlined.FormatStrikethrough
        )

        RichTextStyleButton(
            onClick = {
                state.toggleSpanStyle(
                    SpanStyle(
                        fontSize = 28.sp
                    )
                )
            },
            iconColor = style.iconColor,
            selectedIconBackgroundColor = style.selectedIconBackgroundColor,
            iconSelectedColor = style.iconSelectedColor,
            isSelected = state.currentSpanStyle.fontSize == 28.sp,
            icon = Icons.Outlined.FormatSize
        )
//        RichTextStyleButton(
//            onClick = {
//                state.toggleSpanStyle(
//                    SpanStyle(
//                        color = Color.Red
//                    )
//                )
//            },
//            isSelected = state.currentSpanStyle.color == Color.Red,
//            icon = Icons.Filled.Circle,
//            iconColor = style.iconColor,
//            selectedIconBackgroundColor = style.selectedIconBackgroundColor,
//            iconSelectedColor = style.iconSelectedColor,
//        )

//        RichTextStyleButton(
//            onClick = {
//                state.toggleSpanStyle(
//                    SpanStyle(
//                        background = Color.Yellow
//                    )
//                )
//            },
//            isSelected = state.currentSpanStyle.background == Color.Yellow,
//            icon = Icons.Outlined.Circle,
//            iconColor = style.iconColor,
//            selectedIconBackgroundColor = style.selectedIconBackgroundColor,
//            iconSelectedColor = style.iconSelectedColor,
//            )
        RichTextStyleButton(
            iconColor = style.iconColor,
            selectedIconBackgroundColor = style.selectedIconBackgroundColor,
            iconSelectedColor = style.iconSelectedColor,
            onClick = {
                state.toggleUnorderedList()
            },
            isSelected = state.isUnorderedList,
            icon = Icons.Outlined.FormatListBulleted,
        )

        RichTextStyleButton(
            iconColor = style.iconColor,
            selectedIconBackgroundColor = style.selectedIconBackgroundColor,
            iconSelectedColor = style.iconSelectedColor,
            onClick = {
                state.toggleOrderedList()

            },
            isSelected = state.isOrderedList,
            icon = Icons.Outlined.FormatListNumbered,
        )
        RichTextStyleButton(
            iconColor = style.iconColor,
            selectedIconBackgroundColor = style.selectedIconBackgroundColor,
            iconSelectedColor = style.iconSelectedColor,
            onClick = {
                state.toggleCodeSpan()
            },
            isSelected = state.isCodeSpan,
            icon = Icons.Outlined.Code,
        )
        RichTextStyleButton(
            iconColor = style.iconColor,
            selectedIconBackgroundColor = style.selectedIconBackgroundColor,
            iconSelectedColor = style.iconSelectedColor,
            onClick = {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {

                    val permission = Manifest.permission.READ_EXTERNAL_STORAGE
                    if (ContextCompat.checkSelfPermission(
                            context,
                            permission
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(activity, arrayOf(permission), 123)
                    } else {
                        myImagePicker.launch("image/png")
                    }
                } else {
                    myImagePicker.launch("image/png")
                }
            },
            icon = Icons.Outlined.Image
        )

        RichTextStyleButton(
            iconColor = style.iconColor,
            selectedIconBackgroundColor = style.selectedIconBackgroundColor,
            iconSelectedColor = style.iconSelectedColor,
            onClick = {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {

                    val permission = Manifest.permission.READ_EXTERNAL_STORAGE
                    if (ContextCompat.checkSelfPermission(
                            context,
                            permission
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(activity, arrayOf(permission), 123)
                    } else {
                        myVideoPicker.launch("video/mp4")
                    }
                } else {
                    myVideoPicker.launch("video/mp4")
                }
            },
            icon = Icons.Outlined.VideoLibrary
        )
    }
}

private const val maxSize = 20 * 1024 * 1024