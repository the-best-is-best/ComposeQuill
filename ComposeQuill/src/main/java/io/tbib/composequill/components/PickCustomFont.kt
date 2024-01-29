package io.tbib.composequill.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.tbib.composequill.R
import com.tbib.composesearchabledropdown.SearchableDropDown
import io.tbib.composequill.components.styles.DialogStyle
import io.tbib.composequill.states.QuillStates

private val fontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

@Composable
internal fun SelectFont(
    state: QuillStates,
    style: DialogStyle = DialogStyle(),
    onDismissRequest: () -> Unit,
    onChange: (value: FontFamily) -> Unit,
    initFont: GoogleFont?
) {
    val fonts = state.fonts

    SearchableDropDown(
        listOfItems = fonts!!, // provide the list of items of any type you want to populated in the dropdown,
        modifier = Modifier.fillMaxWidth(),
        onDropDownItemSelected = { item -> // Returns the item selected in the dropdown
            onChange(
                FontFamily(
                    Font(
                        googleFont = GoogleFont(item.name),
                        fontProvider = fontProvider
                    )
                )
            )
            onDismissRequest()
        },

        enable = true,
        placeholder = { Text(text = "Select Font") },
        dropdownItem = { v ->
            Text(text = v.name)
        },
        selectedOptionTextDisplay = { v ->
            v.name
        },
        defaultItem = initFont,

        )
}


