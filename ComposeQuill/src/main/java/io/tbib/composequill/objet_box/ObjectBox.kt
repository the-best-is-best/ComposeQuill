package io.tbib.composequill.objet_box

import android.content.Context
import io.objectbox.BoxStore
import io.tbib.composequill.google_fonts.api.MyObjectBox

internal class SaveFont {
    companion object {

        private lateinit var boxStore: BoxStore

        fun init(context: Context) {
            boxStore = MyObjectBox.builder().androidContext(context).build()
        }

        internal fun get(): BoxStore {
            return boxStore
        }
    }
}