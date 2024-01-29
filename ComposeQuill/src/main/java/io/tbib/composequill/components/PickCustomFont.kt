package io.tbib.composequill.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.tbib.composequill.R
import io.tbib.composequill.states.QuillStates

private val fontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

@Composable
internal fun SelectFont(state: QuillStates) {
    val fonts = state.fonts

}