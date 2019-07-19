package com.sunhapper.x.spedit.gif.drawable

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.Log

/**
 * Created by sunha on 2018/1/23 0023.
 */

class ProxyDrawable
@JvmOverloads constructor(drawable: Drawable? = null, private val invalidateDelegate: InvalidateDelegate = InvalidateDelegate())
    : Drawable(), ResizeDrawable, Drawable.Callback by invalidateDelegate, InvalidateDrawable by invalidateDelegate {
    companion object {
        private val TAG = "ProxyDrawable"
    }

    var drawable: Drawable = drawable?.also {
        it.callback = invalidateDelegate
    } ?: this
        set(drawable) {
            Log.i(TAG, "init: $drawable")
            field.callback = null
            drawable.callback = invalidateDelegate
            field = drawable
            needResize = true
            invalidateDrawable(this)
        }


    private var needResize: Boolean = false

    override val width: Int
        get() = drawable.intrinsicWidth

    override val height: Int
        get() = drawable.intrinsicHeight

    override fun draw(canvas: Canvas) {
        drawable.draw(canvas)
    }

    override fun setAlpha(alpha: Int) {
        drawable.alpha = alpha
    }

    override fun setColorFilter(cf: ColorFilter?) {
        drawable.colorFilter = cf
    }

    override fun getOpacity(): Int {
        return drawable.opacity
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        needResize = false
    }

    override fun setBounds(bounds: Rect) {
        super.setBounds(bounds)
        drawable.bounds = bounds
    }

    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        super.setBounds(left, top, right, bottom)
        drawable.setBounds(left, top, right, bottom)
    }

    override fun needResize(): Boolean {
        return needResize
    }


}