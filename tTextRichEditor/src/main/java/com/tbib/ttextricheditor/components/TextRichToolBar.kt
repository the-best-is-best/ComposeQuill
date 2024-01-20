package com.tbib.ttextricheditor.components

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.Circle
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
import androidx.compose.runtime.Composable
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


@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun TextRichToolBar(
    state: RichTextState,
    imageSelected: (path: String, name: String) -> Unit,
    videoSelected: (path: String, name: String) -> Unit,


    ) {
    val context = LocalContext.current
    val activity = context as Activity
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { it ->
            val cursor = context.contentResolver.query(it, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    val name = it.getString(nameIndex)
                    val pathIndex = it.getColumnIndex(MediaStore.Images.Media.DATA)
                    val path = it.getString(pathIndex)
                    imageSelected(path, name)
                }
            }
        }
    }
    FlowRow(horizontalArrangement = Arrangement.Center) {

        RichTextStyleButton(
            onClick = {
                state.addParagraphStyle(
                    ParagraphStyle(
                        textAlign = TextAlign.Left,
                    )
                )

            },
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
            isSelected = state.currentSpanStyle.fontSize == 28.sp,
            icon = Icons.Outlined.FormatSize
        )
        RichTextStyleButton(
            onClick = {
                state.toggleSpanStyle(
                    SpanStyle(
                        color = Color.Red
                    )
                )
            },
            isSelected = state.currentSpanStyle.color == Color.Red,
            icon = Icons.Filled.Circle,
            tint = Color.Red
        )

        RichTextStyleButton(
            onClick = {
                state.toggleSpanStyle(
                    SpanStyle(
                        background = Color.Yellow
                    )
                )
            },
            isSelected = state.currentSpanStyle.background == Color.Yellow,
            icon = Icons.Outlined.Circle,
            tint = Color.Yellow
        )
        RichTextStyleButton(
            onClick = {
                state.toggleUnorderedList()
            },
            isSelected = state.isUnorderedList,
            icon = Icons.Outlined.FormatListBulleted,
        )

        RichTextStyleButton(
            onClick = {
                state.toggleOrderedList()

            },
            isSelected = state.isOrderedList,
            icon = Icons.Outlined.FormatListNumbered,
        )
        RichTextStyleButton(
            onClick = {
                state.toggleCodeSpan()
            },
            isSelected = state.isCodeSpan,
            icon = Icons.Outlined.Code,
        )

        RichTextStyleButton(
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
                        launcher.launch("image/*")
                    }
                } else {
                    launcher.launch("image/*")
                }
            },
            icon = Icons.Outlined.Image
        )
    }
}