package com.example.skydash

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Handler
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class GameView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var plane: Plane = Plane()
    private var handler: Handler? = null
    private var r: Runnable? = null
    private var arrPipes: ArrayList<Pipe> = ArrayList()
    private var sumpipe: Int = 0
    private var distance: Int = 0
    private var score: Int = 0
    private var bestscore: Int = 0
    private var start: Boolean = false
    private var soundPool: SoundPool? = null
    private var soundJump: Int = 0
    private var loadedSound: Boolean = false

    init {
        val sp = context.getSharedPreferences("gameSetting", Context.MODE_PRIVATE)
        bestscore = sp?.getInt("bestScore", 0) ?: 0
        score = 0
        start = false
        initPlane()
        initPipe()
        handler = Handler()
        r = Runnable {
            invalidate()
        }
        initializeSound()
    }
    private fun initializeSound() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
            soundPool = SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(audioAttributes)
                .build()
        } else {
            soundPool = SoundPool(5, AudioManager.STREAM_MUSIC, 0)
        }

        soundPool?.setOnLoadCompleteListener { _, _, _ ->
            loadedSound = true
        }

        soundJump = soundPool?.load(context, R.raw.jump_02, 1) ?: 0
    }

    private fun initPipe() {
        sumpipe = 6
        distance = 300 * Constants.SCREEN_HEIGHT / 1920
        arrPipes = ArrayList()
        for (i in 0 until sumpipe) {
            if (i < sumpipe / 2) {
                arrPipes.add(
                    Pipe(
                        (Constants.SCREEN_WIDTH + i * ((Constants.SCREEN_WIDTH + 200 * Constants.SCREEN_WIDTH / 1000) / (sumpipe / 2))).toFloat(),
                        0f,
                        200 * Constants.SCREEN_WIDTH / 1000,
                        Constants.SCREEN_HEIGHT / 2
                    )
                )
                arrPipes[arrPipes.size - 1].setCustomBm(
                    BitmapFactory.decodeResource(
                        resources,
                        R.drawable.plane_b2
                    )
                )
                arrPipes[arrPipes.size - 1].randomY()
            } else {
                arrPipes.add(
                    Pipe(
                        arrPipes[i - sumpipe / 2].getCustomX(),
                        arrPipes[i - sumpipe / 2].getCustomY() + arrPipes[i - sumpipe / 2].getCustomHeight() + distance,
                        200 * Constants.SCREEN_WIDTH / 1000,
                        Constants.SCREEN_HEIGHT / 2
                    )
                )
                arrPipes[arrPipes.size - 1].setCustomBm(
                    BitmapFactory.decodeResource(
                        resources,
                        R.drawable.plane_b
                    )
                )
            }
        }
    }

    private fun initPlane() {
        plane = Plane()
        plane.setCustomWidth(100 * Constants.SCREEN_WIDTH / 1000)
        plane.setCustomHeight((100 * Constants.SCREEN_HEIGHT / 1920))
        plane.setCustomX(((100 * Constants.SCREEN_WIDTH / 1000).toInt()).toFloat())
        plane.setCustomY(((Constants.SCREEN_HEIGHT / 2 - plane.getCustomHeight() / 2).toInt()).toFloat())
        val arrBms: ArrayList<Bitmap> = ArrayList()
        arrBms.add(BitmapFactory.decodeResource(resources, R.drawable.fly1))
        arrBms.add(BitmapFactory.decodeResource(resources, R.drawable.fly2))
        plane.setArrBms(arrBms)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas?.let {
            if (start) {
                plane.draw(it)
                for (i in 0 until sumpipe) {
                    if (plane.getCustomRect().intersect(arrPipes[i].getCustomRect()) || plane.getCustomY() - plane.getCustomHeight() < 0 || plane.getCustomY() > Constants.SCREEN_HEIGHT) {
                        Pipe.speed = 0
                        MainActivity.txt_score_over.text = MainActivity.txt_score.text
                        MainActivity.txt_best_score.text = "Best : $bestscore"
                        MainActivity.txt_score.visibility = INVISIBLE
                        MainActivity.rl_game_over.visibility = VISIBLE
                    }
                    if (plane.getCustomX() + plane.getCustomWidth() > arrPipes[i].getCustomX() + arrPipes[i].getCustomWidth() / 2
                        && plane.getCustomX() + plane.getCustomWidth() <= arrPipes[i].getCustomX() + arrPipes[i].getCustomWidth() / 2 + Pipe.speed
                        && i < sumpipe / 2
                    ) {
                        score++
                        val sp = context.getSharedPreferences("gameSetting", Context.MODE_PRIVATE)
                        if (score > bestscore) {
                            bestscore = score
                            sp.edit().putInt("bestScore", bestscore).apply()
                        }
                        sp.edit().putInt("lastScore", score).apply()
                        MainActivity.txt_score.text = score.toString()
                    }
                    if (arrPipes[i].getCustomX() < -arrPipes[i].getCustomWidth()) {
                        arrPipes[i].setCustomX((Constants.SCREEN_WIDTH).toFloat())
                        if (i < sumpipe / 2) {
                            arrPipes[i].randomY()
                        } else {
                            arrPipes[i].setCustomY(
                                arrPipes[i - sumpipe / 2].getCustomY() + arrPipes[i - sumpipe / 2].getCustomHeight() + distance
                            )
                        }
                    }
                    arrPipes[i].draw(it)
                }
            } else {
                if (plane.getCustomY() > Constants.SCREEN_HEIGHT / 2) {
                    plane.setDrop((-15 * Constants.SCREEN_HEIGHT / 1920).toFloat())
                }
                plane.draw(it)
            }
        }
        handler?.postDelayed(r!!, 10)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            if (it.action == MotionEvent.ACTION_DOWN) {
                plane.setDrop((-15).toFloat())
                if (loadedSound) {
                    soundPool?.play(soundJump, 0.5f, 0.5f, 1, 0, 1f)
                }
            }
        }
        return true
    }

    fun isStart(): Boolean {
        return start
    }

    fun setStart(start: Boolean) {
        this.start = start
        if (!start) {
            r?.let { handler?.removeCallbacks(it) }
        } else {
            r?.let { handler?.post(it) }
        }
    }

    fun reset() {
        MainActivity.txt_score.text = "0"
        score = 0
        initPlane()
        initPipe()
    }

}