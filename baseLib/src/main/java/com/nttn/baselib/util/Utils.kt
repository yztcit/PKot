package com.nttn.baselib.util

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blankj.utilcode.util.SizeUtils
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
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



suspend fun Context.alert(title: String, message: String): Boolean =
    suspendCancellableCoroutine { continuation ->
        AlertDialog.Builder(this)
            .setNeutralButton("No") { dialog, _ ->
                dialog.dismiss()
                continuation.resume(false)
            }.setNegativeButton("Yes") { dialog, _ ->
                dialog.dismiss()
                continuation.resume(true)
            }.setTitle(title).setMessage(message)
            .show()
    }



suspend fun <T> Handler.run(block: () -> T) = suspendCoroutine<T> { continuation ->
    post {
        try {
            continuation.resume(block())
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }
}

suspend fun <T> Handler.runDelay(delay: Long, block: () -> T) = suspendCancellableCoroutine<T> { continuation ->
    val message = Message.obtain(this) {
        try {
            continuation.resume(block())
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }.also {
        it.obj = continuation
    }

    continuation.invokeOnCancellation {
        removeCallbacksAndMessages(continuation)
    }

    sendMessageDelayed(message, delay)
}



/**
 * ???????????????
 */
fun drawWatermarkText(text: String, screenWidth: Int, screenHeight: Int): Bitmap {
    val rect = Rect()
    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        //????????????
        color = Color.parseColor("#50CCCCCC")
        //????????????
        textSize = SizeUtils.dp2px(12f).toFloat()
        //??????????????????????????????
        isDither = true
        //????????????
        isFilterBitmap = true

        //?????????????????????????????????????????????
        getTextBounds(text, 0, text.length, rect)
    }
    val w = rect.width()
    val h = rect.height()

    val spaceX = SizeUtils.dp2px(90f);//???????????????
    val spaceY = SizeUtils.dp2px(70f);//???????????????
    //???????????????
    val diagonal = sqrt(screenWidth.toDouble().pow(2.0) + screenHeight.toDouble().pow(2.0))
    //??????countX????????????countY???
    val countX = (diagonal / (w + spaceX)).roundToInt()
    val countY = (diagonal / (h + spaceY)).roundToInt()

    val markBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888)

    val canvas = Canvas(markBitmap).apply {
        //?????????????????????
        drawColor(Color.parseColor("#00000000"))
        //????????????-30??
        rotate(-30f, (screenWidth / 2).toFloat(), (screenHeight / 2).toFloat())
    }

    //??????????????????????????????????????????????????????????????????
    //???????????????
    for (y in -1..countY) {
        for (x in -1..countX) {
            //????????????????????????????????????
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