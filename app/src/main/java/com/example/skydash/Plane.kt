package com.example.skydash

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import java.util.*

class Plane : BaseObject() {
    private var arrBms = ArrayList<Bitmap>()
    private var count = 0
    private var vFlap = 0
    private var idCurrentBitmap = 0
    private var drop = 0f
    fun draw(canvas: Canvas) {
        drop()
        canvas.drawBitmap(getCustomBm(), x, y, null)
    }
    private fun drop() {
        drop += 1.5f
        y += drop
    }
    fun getArrBms(): ArrayList<Bitmap> {
        return arrBms
    }
    fun setArrBms(arrBms: ArrayList<Bitmap>) {
        this.arrBms = arrBms
        for (i in arrBms.indices) {
            this.arrBms[i] =
                Bitmap.createScaledBitmap(this.arrBms[i], width, height, true)
        }
    }
    override fun getCustomBm(): Bitmap {
        count++
        if (count >= vFlap) {
            idCurrentBitmap = (idCurrentBitmap + 1) % arrBms.size
            count = 0
        }
        if (drop < 0) {
            val matrix = Matrix()
            matrix.postRotate(-25f)
            return Bitmap.createBitmap(
                arrBms[idCurrentBitmap], 0, 0,
                arrBms[idCurrentBitmap].width, arrBms[idCurrentBitmap].height, matrix, true
            )
        } else if (drop >= 0) {
            val matrix = Matrix()
            if (drop < 70) {
                matrix.postRotate(-25 + drop * 2)
            } else {
                matrix.postRotate(45f)
            }
            return Bitmap.createBitmap(
                arrBms[idCurrentBitmap], 0, 0,
                arrBms[idCurrentBitmap].width, arrBms[idCurrentBitmap].height, matrix, true
            )
        }
        return arrBms[idCurrentBitmap]
    }
    fun getDrop(): Float {
        return drop
    }
    fun setDrop(drop: Float) {
        this.drop = drop
    }
}