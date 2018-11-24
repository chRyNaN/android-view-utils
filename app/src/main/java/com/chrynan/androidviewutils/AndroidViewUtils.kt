@file:Suppress("unused")

package com.chrynan.androidviewutils

import android.support.v4.view.ViewCompat
import android.view.View

enum class ViewVisibilityState(val visibility: Int) {

    VISIBLE(View.VISIBLE),
    INVISIBLE(View.INVISIBLE),
    GONE(View.GONE);

    companion object {

        fun fromVisibility(visibility: Int) =
            ViewVisibilityState.values().firstOrNull { it.visibility == visibility } ?: VISIBLE
    }
}

val View.isVisible
    get() = visibility == android.view.View.VISIBLE

val View.isInvisible
    get() = visibility == android.view.View.INVISIBLE

val View.isGone
    get() = visibility == android.view.View.GONE

var View.visibilityState
    get() = ViewVisibilityState.fromVisibility(visibility)
    set(value) {
        visibility = when (value) {
            ViewVisibilityState.VISIBLE -> android.view.View.VISIBLE
            ViewVisibilityState.INVISIBLE -> android.view.View.INVISIBLE
            ViewVisibilityState.GONE -> android.view.View.GONE
        }
    }

fun View.setVisibleOrGone(visible: Boolean) {
    visibility = if (visible) android.view.View.VISIBLE else android.view.View.GONE
}

fun View.setVisibleOrInvisible(visible: Boolean) {
    visibility = if (visible) android.view.View.VISIBLE else android.view.View.GONE
}

fun View.setGoneOrInvisible(gone: Boolean) {
    visibility = if (gone) android.view.View.GONE else android.view.View.INVISIBLE
}

fun View.setVisibleOrGone(block: () -> Boolean) {
    visibility = if (block()) android.view.View.VISIBLE else android.view.View.GONE
}

fun View.setVisibleOrInvisible(block: () -> Boolean) {
    visibility = if (block()) android.view.View.VISIBLE else android.view.View.INVISIBLE
}

fun View.setGoneOrInvisible(block: () -> Boolean) {
    visibility = if (block()) android.view.View.GONE else android.view.View.INVISIBLE
}

inline fun View.doOnLayout(crossinline action: View.() -> Unit) {
    if (ViewCompat.isLaidOut(this) && !isLayoutRequested) {
        action(this)
    } else {
        addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(
                view: View,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {
                view.removeOnLayoutChangeListener(this)
                action(view)
            }
        })
    }
}