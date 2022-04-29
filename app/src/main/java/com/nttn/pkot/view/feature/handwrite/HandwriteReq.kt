package com.nttn.pkot.view.feature.handwrite

import com.squareup.moshi.Json

/*{
  //图像数据：base64编码，要求base64编码后大小不超过4M，最短边至少15px，最长边最大4096px，支持jpg/png/bmp格式，和url参数只能同时存在一个
  "img": "",
  //图像url地址：图片完整URL，URL长度不超过1024字节，URL对应的图片base64编码后大小不超过4M，最短边至少15px，最长边最大4096px，支持jpg/png/bmp格式，和img参数只能同时存在一个
  "url": "",
  //是否需要识别结果中每一行的置信度，默认不需要。 true：需要 false：不需要
  "prob": false,
  //是否需要单字识别功能，默认不需要。 true：需要 false：不需要
  "charInfo": false,
  //是否需要自动旋转功能，默认不需要。 true：需要 false：不需要
  "rotate": false,
  //是否需要表格识别功能，默认不需要。 true：需要 false：不需要
  "table": false,
  //字块返回顺序，false表示从左往右，从上到下的顺序，true表示从上到下，从左往右的顺序，默认false
  "sortPage": false
}*/
data class HandwriteReq(
    @Json(name = "img") val img: String?,
    @Json(name = "url") val url: String? = null,
    @Json(name = "prob") val prob: Boolean = false,
    @Json(name = "charInfo") val charInfo: Boolean = false,
    @Json(name = "rotate") val rotate: Boolean = false,
    @Json(name = "table") val table: Boolean = false,
    @Json(name = "sortPage") val sortPage: Boolean = false,
)
