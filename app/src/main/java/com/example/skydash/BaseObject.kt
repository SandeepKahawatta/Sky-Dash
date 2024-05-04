package com.example.skydash

import android.graphics.Bitmap
import android.graphics.Rect

open class BaseObject {
    protected var x = 0f
    protected var y = 0f
    protected var width = 0
    protected var height = 0
    protected lateinit var rect: Rect
    protected lateinit var bm: Bitmap

    constructor() {}

    constructor(x: Float, y: Float, width: Int, height: Int) {
        this.x = x
        this.y = y
        this.width = width
        this.height = height
    }

    fun getCustomX(): Float {
        return x
    }

    fun setCustomX(x: Float) {
        this.x = x
    }

    fun getCustomY(): Float {
        return y
    }

    fun setCustomY(y: Float) {
        this.y = y
    }

    fun getCustomWidth(): Int {
        return width
    }

    fun setCustomWidth(width: Int) {
        this.width = width
    }

    fun getCustomHeight(): Int {
        return height
    }

    fun setCustomHeight(height: Int) {
        this.height = height
    }

    open fun getCustomBm(): Bitmap {
        return bm
    }

    open fun setCustomBm(bm: Bitmap) {
        this.bm = bm
    }

    fun getCustomRect(): Rect {
        return Rect(
            x.toInt(),
            y.toInt(),
            (x + width).toInt(),
            (y + height).toInt()
        )
    }

    fun setCustomRect(rect: Rect) {
        this.rect = rect
    }
}
