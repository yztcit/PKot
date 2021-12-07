package com.nttn.pkot.util

import android.graphics.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blankj.utilcode.util.SizeUtils
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

inline fun <reified T: ViewModel> provideViewModel(
    context: FragmentActivity,
    factory: ViewModelProvider.Factory? = null
): T = ViewModelProviders.of(context, factory).get(T::class.java)

inline fun <reified T: ViewModel> provideViewModel(
    context: Fragment,
    factory: ViewModelProvider.Factory? = null
): T = ViewModelProviders.of(context, factory).get(T::class.java)

/**
 * 绘制水印图
 */
fun drawWatermarkText(text: String, screenWidth: Int, screenHeight: Int): Bitmap {
    val rect = Rect()
    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        //文本颜色
        color = Color.parseColor("#50CCCCCC")
        //文本大小
        textSize = SizeUtils.dp2px(12f).toFloat()
        //获取更清晰的图像采样
        isDither = true
        //过滤一些
        isFilterBitmap = true

        //计算文字所在矩形，可以得到宽高
        getTextBounds(text, 0, text.length, rect)
    }
    val w = rect.width()
    val h = rect.height()

    val spaceX = SizeUtils.dp2px(90f);//文本行间距
    val spaceY = SizeUtils.dp2px(70f);//文本列间距
    //屏幕对角线
    val diagonal = sqrt(screenWidth.toDouble().pow(2.0) + screenHeight.toDouble().pow(2.0))
    //一行countX个，一列countY个
    val countX = (diagonal / (w + spaceX)).roundToInt()
    val countY = (diagonal / (h + spaceY)).roundToInt()

    val markBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888)

    val canvas = Canvas(markBitmap).apply {
        //水印画布背景色
        drawColor(Color.parseColor("#00000000"))
        //画布旋转-30°
        rotate(-30f, (screenWidth / 2).toFloat(), (screenHeight / 2).toFloat())
    }

    //旋转之后对角出现空白，偏移一定距离来充分覆盖
    //绘制行，列
    for (y in -1..countY) {
        for (x in -1..countX) {
            //绘制行，以行中心点为中心
            if (y % 2 == 1) {
                canvas.drawText(
                    text,
                    (x * (w + spaceX)).toFloat(),
                    ((h + spaceY) * y + h).toFloat(),
                    paint
                )
            } else {
                canvas.drawText(
                    text,
                    (x * (w + spaceX) - (spaceX + w shr 1)).toFloat(),
                    ((h + spaceY) * y + h).toFloat(),
                    paint
                )
            }
        }
    }

    return markBitmap
}