package io.tbib.composequill.components

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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.FormatAlignLeft
import androidx.compose.material.icons.automirrored.outlined.FormatAlignRight
import androidx.compose.material.icons.automirrored.outlined.FormatListBulleted
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.FontDownload
import androidx.compose.material.icons.outlined.FormatAlignCenter
import androidx.compose.material.icons.outlined.FormatBold
import androidx.compose.material.icons.outlined.FormatColorFill
import androidx.compose.material.icons.outlined.FormatItalic
import androidx.compose.material.icons.outlined.FormatListNumbered
import androidx.compose.material.icons.outlined.FormatSize
import androidx.compose.material.icons.outlined.FormatStrikethrough
import androidx.compose.material.icons.outlined.FormatUnderlined
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import io.tbib.composequill.components.styles.DialogStyle
import io.tbib.composequill.states.QuillStates

//
data class QuillEditorToolBarStyle(
    val modifier: Modifier = Modifier,
    val iconColor: Color? = null,
    val selectedIconBackgroundColor: Color? = null,
    val iconSelectedColor: Color? = Color.White

)

@SuppressLint("StateFlowValueCalledInComposition", "Recycle", "SuspiciousIndentation")
@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun QuillEditorToolBar(
    showImagePicker: Boolean,
    showVideoPicker: Boolean,
    state: QuillStates,
    onChange: () -> Unit,
    style: QuillEditorToolBarStyle = QuillEditorToolBarStyle(),
    dialogStyle: DialogStyle = DialogStyle()


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
                    state.addImage(path)
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
                    state.addVideo(path.value)
                    onChange()


                }
            }
        }
    }
//    LaunchedEffect(state.video ){
//        delay(1000)
//        state.addVideo(path.value)
//
//    }

    val controller = rememberColorPickerController()

    var textAlign by remember {
        mutableStateOf(state.textState.currentParagraphStyle.textAlign)
    }

    var textColor by remember {
        mutableStateOf(Color.Black)
    }

    var textBGColor by remember {
        mutableStateOf(Color.Black)
    }
    var showTextColorDialog by remember { mutableStateOf(false) }
    var showTextBGColorDialog by remember { mutableStateOf(false) }
    var showFontSizeDialog by remember { mutableStateOf(false) }

    if (showTextColorDialog) {
        ColorPickerDialog(
            style = dialogStyle,
            onDismissRequest = {
                showTextColorDialog = false
            },
            controller = controller,
            initColor = textColor,
            onChange = {
                state.changeTextColor(it)
                textColor = it
            }
        )
    }

    if (showTextBGColorDialog) {
        ColorPickerDialog(
            style = dialogStyle,
            onDismissRequest = {
                showTextBGColorDialog = false
            },
            controller = controller,
            initColor = textColor,
            onChange = {
                state.changeTextBackgroundColor(it)
                textBGColor = it
            }
        )
    }

    if (showFontSizeDialog) {
        FontSizeDialog(
            style = dialogStyle,
            onDismissRequest = {
                showFontSizeDialog = false
            },
            initSize = state.textState.currentSpanStyle.fontSize.toENUM(state.textState.currentSpanStyle.fontSize),
            onChange = {
                state.changeFontSize(it)
                showFontSizeDialog = false
            }
        )
    }
    FlowRow(modifier = style.modifier, horizontalArrangement = Arrangement.Center) {

        QuillEditorStyleButton(
            onClick = {
                state.textState.addParagraphStyle(
                    ParagraphStyle(
                        textAlign = TextAlign.Left,
                    )
                )
                textAlign = TextAlign.Left
            },
            iconColor = style.iconColor,
            selectedIconBackgroundColor = style.selectedIconBackgroundColor,
            iconSelectedColor = style.iconSelectedColor,
            isSelected = false,
            icon = Icons.AutoMirrored.Outlined.FormatAlignLeft
        )

        QuillEditorStyleButton(
            onClick = {
                state.textState.addParagraphStyle(
                    ParagraphStyle(
                        textAlign = TextAlign.Center
                    )
                )
                textAlign = TextAlign.Center
            },
            iconColor = style.iconColor,
            selectedIconBackgroundColor = style.selectedIconBackgroundColor,
            iconSelectedColor = style.iconSelectedColor,
            isSelected = false,
            icon = Icons.Outlined.FormatAlignCenter
        )
        QuillEditorStyleButton(
            onClick = {
                state.textState.addParagraphStyle(
                    ParagraphStyle(
                        textAlign = TextAlign.Right
                    )
                )
                textAlign = TextAlign.Right

            },
            iconColor = style.iconColor,
            selectedIconBackgroundColor = style.selectedIconBackgroundColor,
            iconSelectedColor = style.iconSelectedColor,
            isSelected = false,
            icon = Icons.AutoMirrored.Outlined.FormatAlignRight
        )

        QuillEditorStyleButton(
            onClick = {
                state.textState.toggleSpanStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold
                    )
                )
            },
            iconColor = style.iconColor,
            selectedIconBackgroundColor = style.selectedIconBackgroundColor,
            iconSelectedColor = style.iconSelectedColor,
            isSelected = state.textState.currentSpanStyle.fontWeight == FontWeight.Bold,
            icon = Icons.Outlined.FormatBold
        )

        QuillEditorStyleButton(
            onClick = {
                state.textState.toggleSpanStyle(
                    SpanStyle(
                        fontStyle = FontStyle.Italic
                    )
                )
            },
            iconColor = style.iconColor,
            selectedIconBackgroundColor = style.selectedIconBackgroundColor,
            iconSelectedColor = style.iconSelectedColor,
            isSelected = state.textState.currentSpanStyle.fontStyle == FontStyle.Italic,
            icon = Icons.Outlined.FormatItalic
        )

        QuillEditorStyleButton(
            onClick = {
                state.textState.toggleSpanStyle(
                    SpanStyle(
                        textDecoration = TextDecoration.Underline
                    )
                )
            },
            iconColor = style.iconColor,
            selectedIconBackgroundColor = style.selectedIconBackgroundColor,
            iconSelectedColor = style.iconSelectedColor,
            isSelected = state.textState.currentSpanStyle.textDecoration?.contains(TextDecoration.Underline) == true,
            icon = Icons.Outlined.FormatUnderlined
        )

        QuillEditorStyleButton(
            onClick = {
                state.textState.toggleSpanStyle(
                    SpanStyle(
                        textDecoration = TextDecoration.LineThrough
                    )
                )
            },
            iconColor = style.iconColor,
            selectedIconBackgroundColor = style.selectedIconBackgroundColor,
            iconSelectedColor = style.iconSelectedColor,
            isSelected = state.textState.currentSpanStyle.textDecoration?.contains(TextDecoration.LineThrough) == true,
            icon = Icons.Outlined.FormatStrikethrough
        )
        if (state.fonts.isNotEmpty())
            Row(modifier = Modifier.width(250.dp)) {
                QuillEditorStyleButton(
                    onClick = {
                    },
                    isSelected = false,
                    icon = Icons.Outlined.FontDownload,
                    iconColor = Color.Black,
                    selectedIconBackgroundColor = style.selectedIconBackgroundColor,
                    iconSelectedColor = style.iconSelectedColor,
                )
                Spacer(modifier = Modifier.width(10.dp))
                SelectFont(state, {

                }, {
                    state.changeFont(it)

                },
                    null
                )
            }


        QuillEditorStyleButton(
            onClick = {
                showFontSizeDialog = true
            },
            iconColor = style.iconColor,
            selectedIconBackgroundColor = style.selectedIconBackgroundColor,
            iconSelectedColor = style.iconSelectedColor,
            isSelected = false,
            icon = Icons.Outlined.FormatSize
        )
        QuillEditorStyleButton(

            onClick = {

                showTextColorDialog = true
            },
            isSelected = false,
            icon = Icons.Filled.ColorLens,
            iconColor = state.textState.currentSpanStyle.color,
            selectedIconBackgroundColor = style.selectedIconBackgroundColor,
            iconSelectedColor = style.iconSelectedColor,
        )


        QuillEditorStyleButton(
            onClick = {
                showTextBGColorDialog = true
            },
            isSelected = false,
            icon = Icons.Outlined.FormatColorFill,
            iconColor = state.textState.currentSpanStyle.background,
            selectedIconBackgroundColor = style.selectedIconBackgroundColor,
            iconSelectedColor = style.iconSelectedColor,
        )
        QuillEditorStyleButton(
            iconColor = style.iconColor,
            selectedIconBackgroundColor = style.selectedIconBackgroundColor,
            iconSelectedColor = style.iconSelectedColor,
            onClick = {
                state.textState.toggleUnorderedList()
            },
            isSelected = state.textState.isUnorderedList,
            icon = Icons.AutoMirrored.Outlined.FormatListBulleted,
        )

        QuillEditorStyleButton(
            iconColor = style.iconColor,
            selectedIconBackgroundColor = style.selectedIconBackgroundColor,
            iconSelectedColor = style.iconSelectedColor,
            onClick = {
                state.textState.toggleOrderedList()

            },
            isSelected = state.textState.isOrderedList,
            icon = Icons.Outlined.FormatListNumbered,
        )
        QuillEditorStyleButton(
            iconColor = style.iconColor,
            selectedIconBackgroundColor = style.selectedIconBackgroundColor,
            iconSelectedColor = style.iconSelectedColor,
            onClick = {
                state.textState.toggleCodeSpan()
            },
            isSelected = state.textState.isCodeSpan,
            icon = Icons.Outlined.Code,
        )
        if (showImagePicker)
            QuillEditorStyleButton(
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
        if (showVideoPicker)
            QuillEditorStyleButton(
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