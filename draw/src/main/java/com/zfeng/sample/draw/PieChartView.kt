package com.zfeng.sample.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class PieChartView : View {

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

    private val OFFSET_LENGTH = Utils.dp2px(20)
    private val OFFSET_INDEX = 2

    private val RADIUS = Utils.dp2px(150)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bounds = RectF()
    private val ANGLES = arrayOf(60f, 100f, 120f, 80f)
    private val COLORS = arrayOf(
        Color.parseColor("#448AFF"),
        Color.parseColor("#9575CD"),
        Color.parseColor("#FF8F00"),
        Color.parseColor("#00C853")
    )

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bounds.set(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2f + RADIUS
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var currentAngle = 0f
        ANGLES.forEachIndexed { i, it ->

            if (i == OFFSET_INDEX) {
                canvas?.save()
                canvas?.translate(
                    OFFSET_LENGTH * Math.cos(Math.toRadians(currentAngle + it / 2.0)).toFloat(),
                    OFFSET_LENGTH * Math.sin(Math.toRadians(currentAngle * it / 2.0)).toFloat()
                )
            }
            paint.color = COLORS[i]
            canvas?.drawArc(bounds, currentAngle, it, true, paint)
            currentAngle += it

            if (i == OFFSET_INDEX) {
                canvas?.restore()
            }
        }
    }

}