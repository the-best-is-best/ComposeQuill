package io.tbib.composequill.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
internal fun QuillEditorStyleButton(
    onClick: () -> Unit,
    icon: ImageVector,
    iconColor: Color? = null,
    isSelected: Boolean = false,
    selectedIconBackgroundColor: Color? = null,
    iconSelectedColor: Color? = null
) {
    IconButton(
        modifier = Modifier
            // Workaround to prevent the rich editor
            // from losing focus when clicking on the button
            // (Happens only on Desktop)
            .focusProperties { canFocus = false },
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = if (isSelected) {
                iconSelectedColor ?: MaterialTheme.colorScheme.onPrimary
            } else {
                iconColor ?: MaterialTheme.colorScheme.onBackground
            },
        ),
    ) {
        Icon(
            icon,
            contentDescription = icon.name,
            //  tint = iconColor ?: LocalContentColor.current,
            modifier = Modifier
                .background(
                    color = if (isSelected) {
                        selectedIconBackgroundColor ?: MaterialTheme.colorScheme.primary
                    } else {
                        Color.Transparent
                    },
                    shape = CircleShape
                )
                .padding(10.dp)
        )
    }
}