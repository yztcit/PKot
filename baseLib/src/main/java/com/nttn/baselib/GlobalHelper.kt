package com.nttn.baselib

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ScreenUtils
import com.nttn.baselib.util.drawWatermarkText

object GlobalHelper {
    /* ↓↓↓ 水印 ↓↓↓ */
    private var text: String? = null
    private val markLiveData = MutableLiveData<Bitmap>()
    val watermark: LiveData<Bitmap> get() = markLiveData

    fun generateWatermark(text: String) {
        if (GlobalHelper.text == text && markLiveData.value != null) return
        markLiveData.value = drawWatermarkText(text, ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight())
    }

    fun recycleWatermark() {
        markLiveData.value?.recycle()
        markLiveData.value = null
        watermark.value?.recycle()
    }
    /* ↑↑↑ 水印 ↑↑↑ */
}