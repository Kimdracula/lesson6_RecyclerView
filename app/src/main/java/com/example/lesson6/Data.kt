package com.example.lesson6

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Data(
    val type: Int = TYPE_USUAL,
    var header: String? = null,
    var description: String? = null,
    var date: Date? = null
): Parcelable {

    companion object {
        const val TYPE_USUAL = 0
        const val TYPE_IMPORTANT = 1
        const val TYPE_EXTRA = 2
    }
}
