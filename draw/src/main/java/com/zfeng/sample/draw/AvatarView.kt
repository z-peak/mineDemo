package com.zfeng.sample.draw

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class AvatarView : View {

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


    private val WIDTH = Utils.dp2px(100)
    private val OFFSET = Utils.dp2px(50)
    private lateinit var bitmap: Bitmap
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    private val bounds = RectF()

    init {
        bitmap = getAvatar(WIDTH.toInt())
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bounds.set(OFFSET, OFFSET, OFFSET + WIDTH, OFFSET + WIDTH)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val radius = WIDTH / 2f
        canvas?.drawCircle(OFFSET + radius, OFFSET + radius, radius + Utils.dp2px(10), paint)
        //
        val saved = canvas?.saveLayer(bounds, paint)
        canvas?.drawCircle(OFFSET + radius, OFFSET + radius, radius, paint)
        paint.xfermode = xfermode
        canvas?.drawBitmap(bitmap, OFFSET, OFFSET, paint)
        paint.xfermode = null
        if (saved != null) canvas.restoreToCount(saved)
    }

    private fun getAvatar(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.mipmap.test_image, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.mipmap.test_image, options)
    }
}