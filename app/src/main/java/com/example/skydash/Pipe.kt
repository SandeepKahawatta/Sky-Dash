package com.example.skydash

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import java.util.Random

class Pipe(x: Float, y: Float, width: Int, height: Int) : BaseObject(x, y, width, height) {
    companion object {
        var speed: Int = 0
    }

    init {
        speed = 10 * Constants.SCREEN_WIDTH / 1000
    }

    fun draw(canvas: Canvas) {
        x -= speed
        canvas.drawBitmap(bm, x, y, null)
    }

    fun randomY() {
        val r = Random()
        y = (r.nextInt(0 + height / 4 + 1) - height / 4).toFloat()
    }

    override fun setCustomBm(bm: Bitmap) {
        this.bm = Bitmap.createScaledBitmap(bm, width, height, true)
    }
}
