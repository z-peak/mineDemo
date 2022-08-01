package com.zfeng.sample.draw

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.util.*

class ClockView : View {
    private val mHandler = Handler(Looper.getMainLooper())
    private val calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"))

    private val RADIUS = Utils.dp2px(150)

    /** 表盘时间文案 半径 */
    private val TEXT_RADIUS = Utils.dp2px(125)
    private val STROKE_WIDTH = Utils.dp2px(2)

    /** 表盘时间文案 */
    private val textArray = arrayOf("3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "1", "2")

    /** 文字 颜色 */
    private val TEXT_COLOR = Color.parseColor("#040A23")

    /** 边框 颜色 */
    private val BORDER_COLOR = Color.parseColor("#040A23")

    /** 刻度 颜色 */
    private val CALIBRATION_COLOR = Color.parseColor("#CCCCCC")

    /** 秒针 长度 */
    private val SECOND_LENGTH = Utils.dp2px(120)

    /** 秒针 宽度 */
    private val SECOND_WIDTH = Utils.dp2px(1)

    /** 秒针 颜色 */
    private val SECOND_COLOR = Color.parseColor("#EC042A")

    /** 分针 长度 */
    private val MIN_LENGTH = Utils.dp2px(100)

    /** 分针 宽度 */
    private val MIN_WIDTH = Utils.dp2px(2)

    /** 分针 颜色 */
    private val MIN_COLOR = Color.parseColor("#040A23")

    /** 时针 长度 */
    private val HOUR_LENGTH = Utils.dp2px(80)

    /** 时针 宽度 */
    private val HOUR_WIDTH = Utils.dp2px(3)

    /** 时针 颜色 */
    private val HOUR_COLOR = Color.parseColor("#040A23")

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var effect: PathDashPathEffect? = null
    private var effectMin: PathDashPathEffect? = null
    private val path = Path()
    private val dash = Path()
    private val dashMin = Path()
    private lateinit var pathMeasure: PathMeasure

    private var hour: Int = 2
    private var min: Int = 5
    private var seconds: Int = 5

    private val bounds = Rect()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)


    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = STROKE_WIDTH
        paint.color = BORDER_COLOR
        dash.addRect(0f, 0f, Utils.dp2px(2), Utils.dp2px(12), Path.Direction.CCW)
        dashMin.addRect(0f, 0f, Utils.dp2px(1), Utils.dp2px(6), Path.Direction.CCW)

        calendar.timeInMillis = System.currentTimeMillis()
        hour = calendar.get(Calendar.HOUR_OF_DAY)
        min = calendar.get(Calendar.MINUTE)
        seconds = calendar.get(Calendar.SECOND)

        timer()

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path.reset()
        path.addArc(
            width / 2 - RADIUS,
            height / 2 - RADIUS,
            width / 2 + RADIUS,
            height / 2 + RADIUS,
            0f,
            360f
        )
        pathMeasure = PathMeasure(path, false)
        effect =
            PathDashPathEffect(dash, (pathMeasure.length) / 12, 0f, PathDashPathEffect.Style.ROTATE)
        effectMin = PathDashPathEffect(
            dashMin,
            (pathMeasure.length) / 60,
            0f,
            PathDashPathEffect.Style.ROTATE
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawText(canvas)
        drawCalibration(canvas)
        drawBorder(canvas)

        drawSecond(canvas)
        drawMin(canvas)
        drawHour(canvas)

        paint.strokeWidth = STROKE_WIDTH
        paint.color = BORDER_COLOR
    }

    /**
     * 绘制文字
     */
    private fun drawText(canvas: Canvas?) {
        // 边框
        paint.color = TEXT_COLOR
        paint.strokeWidth = 1f
        paint.textSize = Utils.sp2px(12)

        textArray.forEachIndexed { index, it ->
            paint.getTextBounds(it, 0, it.length, bounds)
            Log.e(
                "==========",
                "===>${it} ===>bounds = ${bounds} ,${bounds.right + bounds.left} ,${bounds.bottom + bounds.top}"
            )
            canvas?.drawText(
                it,
                (width / 2f + TEXT_RADIUS * Math.cos(
                    Math.toRadians(
                        30.0 * index
                    )
                ) - (bounds.right + bounds.left) / 2 ).toFloat(),
                (height / 2f + TEXT_RADIUS * Math.sin(
                    Math.toRadians(
                        30.0 * index
                    )
                ) - (bounds.bottom + bounds.top) / 2).toFloat(),
                paint
            )
        }
    }

    /**
     * 绘制边框
     */
    private fun drawBorder(canvas: Canvas?) {
        // 边框
        paint.color = BORDER_COLOR
        drawArc(canvas)
    }

    /**
     * 绘制刻度
     */
    private fun drawCalibration(canvas: Canvas?) {
        // 刻度 - 时
        paint.color = CALIBRATION_COLOR
        paint.pathEffect = effect
        drawArc(canvas)

        // 刻度 - 分
        paint.pathEffect = effectMin
        drawArc(canvas)

        paint.pathEffect = null
    }

    /**
     * 绘制 圆弧
     */
    private fun drawArc(canvas: Canvas?) {
        canvas?.drawArc(
            width / 2 - RADIUS,
            height / 2 - RADIUS,
            width / 2 + RADIUS,
            height / 2 + RADIUS,
            0f, 360f, false, paint
        )
    }

    /**
     * 绘制时针
     */
    private fun drawHour(canvas: Canvas?) {
        paint.strokeWidth = HOUR_WIDTH
        paint.color = HOUR_COLOR
        // 时针
        canvas?.drawLine(
            width / 2f, height / 2f,
            (width / 2f + HOUR_LENGTH * Math.cos(getAngleHour())).toFloat(),
            (height / 2f + HOUR_LENGTH * Math.sin(getAngleHour())).toFloat(),
            paint
        )
    }

    /**
     * 绘制分针
     */
    private fun drawMin(canvas: Canvas?) {
        paint.strokeWidth = MIN_WIDTH
        paint.color = MIN_COLOR
        // 分针
        canvas?.drawLine(
            width / 2f, height / 2f,
            (width / 2f + MIN_LENGTH * Math.cos(getAngleMin())).toFloat(),
            (height / 2f + MIN_LENGTH * Math.sin(getAngleMin())).toFloat(),
            paint
        )
    }

    /**
     * 绘制秒针
     */
    private fun drawSecond(canvas: Canvas?) {
        paint.strokeWidth = SECOND_WIDTH
        paint.color = SECOND_COLOR
        // 秒针
        canvas?.drawLine(
            width / 2f, height / 2f,
            (width / 2f + SECOND_LENGTH * Math.cos(getAngleSeconds())).toFloat(),
            (height / 2f + SECOND_LENGTH * Math.sin(getAngleSeconds())).toFloat(),
            paint
        )
    }

    /**
     * 秒针角度
     */
    private fun getAngleSeconds(): Double {
        if (seconds <= 0) return Math.toRadians(270.0)
        val angdeg = 6.0 * ((seconds % 60) - 15)
        return Math.toRadians(angdeg)
    }

    /**
     * 分针角度
     */
    private fun getAngleMin(): Double {
        if (min <= 0) return Math.toRadians(270.0)
        val angdeg = 6.0 * ((min % 60) - 15)
        return Math.toRadians(angdeg)
    }

    /**
     * 时针角度
     * @param 1,2,3,4,5,6,7,8,9,10,11,12
     * val interval = 360 / 12.0 = 30
     */
    private fun getAngleHour(): Double {
        if (hour <= 0) return Math.toRadians(270.0)
        val angdeg = 30.0 * ((hour % 12) - 3) + ((min % 60) / 60f) * 30
        return Math.toRadians(angdeg)
    }

    fun setTime(hour: Int, min: Int, seconds: Int) {
        this.hour = hour
        this.min = min
        this.seconds = seconds
        invalidate()
    }

    private fun timer() {
        mHandler.postDelayed({
            calendar.timeInMillis = System.currentTimeMillis()
            setTime(
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND)
            )
            timer()
        }, 1000)
    }

}