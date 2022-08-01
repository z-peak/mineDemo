package com.zfeng.sample.draw

import android.content.res.Resources

object Utils {

    fun dp2px(dp: Int): Float {
        return dp * Resources.getSystem().displayMetrics.density + 0.5f
    }

    fun sp2px(sp: Int): Float {
        return sp * Resources.getSystem().displayMetrics.scaledDensity + 0.5f
    }
}