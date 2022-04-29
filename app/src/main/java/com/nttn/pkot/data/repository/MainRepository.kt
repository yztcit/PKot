package com.nttn.pkot.data.repository

import com.alibaba.cloudapi.sdk.enums.HttpConnectionModel
import com.alibaba.cloudapi.sdk.enums.HttpMethod
import com.alibaba.cloudapi.sdk.enums.Scheme
import com.alibaba.cloudapi.sdk.model.ApiRequest
import com.alibaba.cloudapi.sdk.util.ApiRequestMaker
import com.alibaba.cloudapi.sdk.util.HttpCommonUtil
import com.nttn.pkot.data.api.ApiHelper
import com.nttn.pkot.view.feature.handwrite.HandwriteConst
import com.nttn.pkot.view.feature.handwrite.HandwriteResponse
import okhttp3.MediaType
import okhttp3.RequestBody

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun getUsers(pageNum: Int, pageSize: Int) = apiHelper.getUsers(pageNum, pageSize)

    suspend fun handwrite(data: ByteArray): HandwriteResponse {
        val request = ApiRequest(HttpMethod.POST_BODY, HandwriteConst.pathShouxieCn, data)
        return buildRequest(request)!!.let { apiHelper.handwrite(getHeadersMap(request.headers), it) }
    }

    private fun getHeadersMap(headers: Map<String, List<String>>): Map<String, String> {
        val map: HashMap<String, String> = HashMap()

        val valueString: StringBuilder = StringBuilder()

        for (stringListEntry in headers.entries) {
            val (key, value1) = stringListEntry
            val var5 = (value1 as List<*>).iterator()

            valueString.clear()

            while (var5.hasNext()) {
                val value = var5.next() as String

                valueString.append(value).append(",")
            }

            map[key] = valueString.substring(0, valueString.lastIndexOf(","))
        }

        return map
    }

    private fun buildRequest(request: ApiRequest): RequestBody? {
        if (request.httpConnectionMode == HttpConnectionModel.SINGER_CONNECTION) {
            request.host = HandwriteConst.HOST
            request.scheme = Scheme.HTTPS
        }
        ApiRequestMaker.make(request, HandwriteConst.app_key, HandwriteConst.app_secret)
        var requestBody: RequestBody? = null
        if (null != request.formParams && request.formParams.isNotEmpty()) {
            requestBody = RequestBody.create(
                MediaType.parse(request.method.requestContentType),
                HttpCommonUtil.buildParamString(request.formParams)
            )
        } else if (null != request.body && request.body.isNotEmpty()) {
            requestBody =
                RequestBody.create(MediaType.parse(request.method.requestContentType), request.body)
        }
        return requestBody
    }
}