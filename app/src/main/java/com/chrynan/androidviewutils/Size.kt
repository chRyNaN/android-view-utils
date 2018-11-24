@file:Suppress("unused")

package com.chrynan.androidviewutils

import android.view.ViewGroup
import com.chrynan.inlinepixel.Pixels

sealed class Size(val value: Int) {

    object MatchParent : Size(ViewGroup.LayoutParams.MATCH_PARENT)

    object WrapContent : Size(ViewGroup.LayoutParams.WRAP_CONTENT)

    data class Exact(val pixels: Pixels) : Size(pixels.value)
}