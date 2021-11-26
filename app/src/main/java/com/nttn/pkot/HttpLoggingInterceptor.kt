package com.nttn.pkot

import com.blankj.utilcode.util.LogUtils
import okhttp3.*
import okhttp3.internal.http.HttpHeaders
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit
import java.util.logging.Logger

class HttpLoggingInterceptor(tag: String) : Interceptor {
    private val utf8: Charset = Charset.forName("UTF-8")

    private var printLevel: Level = Level.NONE
    private var colorLevel: java.util.logging.Level = java.util.logging.Level.INFO

    sealed class Level {
        object NONE : Level()
        object BASIC : Level()
        object HEADERS : Level()
        object BODY : Level()
    }

    private var logger: Logger = Logger.getLogger(tag)

    fun setPrintLevel(level: Level) {
        printLevel = level
    }

    fun setColorLevel(level: java.util.logging.Level) {
        colorLevel = level
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (printLevel is Level.NONE) {
            return chain.proceed(request)
        }

        //请求日志拦截
        logForRequest(request, chain.connection())
        //执行请求，计算请求时间
        val startNs = System.nanoTime()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            log("<-- HTTP FAILED: $e")
            throw e
        }
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        return logForResponse(response, tookMs)
    }

    private fun log(message: String) {
        logger.log(colorLevel, message)
    }

    private fun logForRequest(request: Request, connection: Connection?) {
        val logBody: Boolean = printLevel == Level.BODY
        val logHeaders: Boolean = (printLevel == Level.BODY || printLevel == Level.HEADERS)
        val requestBody = request.body()
        val hasRequestBody: Boolean = requestBody != null
        val protocol = connection?.protocol() ?: Protocol.HTTP_1_1

        try {
            log( "--> ${request.method()} ${request.url()} $protocol")

            if (logHeaders) {
                if (hasRequestBody) {
                    // Request body headers are only present when installed as a network interceptor. Force
                    // them to be included (when available) so there values are known.
                    requestBody?.contentType()?.let {
                        log("\tContent-Type: $it")
                    }
                    if (requestBody?.contentLength()?.compareTo(-1L) != 0) {
                        log("\tContent-Length: ${requestBody?.contentLength()}")
                    }
                }
                val headers: Headers = request.headers()
                for (index in 0 until headers.size()) {
                    val name = headers.name(index)
                    // Skip headers from the request body as they are explicitly logged above.
                    if (!"Content-Type".equals(name, true) && !"Content-Length".equals(name, true)) {
                        log("\t$name: ${headers.value(index)}")
                    }
                }

                log(" ")
                if (logBody && hasRequestBody) {
                    if (isPlaintext(requestBody?.contentType())) {
                        bodyToString(request)
                    } else {
                        log("\tbody: maybe [binary body], omitted!")
                    }
                }
            }
        } catch (e: Exception) {
            LogUtils.e(e)
        } finally {
            log("--> END ${request.method()}")
        }
    }

    private fun logForResponse(response: Response, tookMs: Long): Response {
        val builder: Response.Builder = response.newBuilder()
        val clone: Response = builder.build()
        var responseBody: ResponseBody? = clone.body()
        val logBody: Boolean = printLevel == Level.BODY
        val logHeaders: Boolean = (printLevel == Level.BODY || printLevel == Level.HEADERS)
        try {
            log("<-- ${clone.code()} ${clone.message()} ${clone.request().url()} (${tookMs}ms)")
            if (logHeaders) {
                val headers:Headers = clone.headers()
                for (i in 0 until headers.size()) {
                    log("\t${headers.name(i)}: ${headers.value(i)}")
                }

                log(" ")
                if (logBody && HttpHeaders.hasBody(clone)) {
                    if (responseBody == null) return response

                    if (isPlaintext(responseBody.contentType())) {
                        val bytes:ByteArray = toByteArray(responseBody.byteStream())
                        log("\tbody:${String(bytes, getCharset(responseBody.contentType()))}")

                        responseBody = ResponseBody.create(responseBody.contentType(), bytes)
                        return response.newBuilder().body(responseBody).build()
                    } else {
                        log("\tbody: maybe [binary body], omitted!")
                    }
                }
            }
        } catch (e: Exception) {
            LogUtils.e(e)
        } finally {
            log("<-- END HTTP")
        }
        return response
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    private fun isPlaintext(mediaType: MediaType?) = mediaType?.run {
        return if ("text" != type()) {
            subtype().lowercase().run {
                contains("x-www-form-urlencoded") || contains("json") ||
                        contains("xml") || contains("html")
            }
        } else {
            true
        }
    } ?: false

    private fun getCharset(contentType: MediaType?) = contentType?.charset(utf8) ?: utf8

    private fun bodyToString(request: Request) {
        try {
            val copy: Request = request.newBuilder().build()
            val body: RequestBody? = copy.body()
            body?.let {
                val buffer = okio.Buffer()
                it.writeTo(buffer)
                val charset = getCharset(body.contentType())
                log("\tbody:${buffer.readString(charset)}")
            }
        } catch (e: Exception) {
            LogUtils.e(e)
        }
    }

    private fun toByteArray(input: InputStream): ByteArray {
        val output = ByteArrayOutputStream()
        write(input, output)
        output.close()
        return output.toByteArray()
    }

    private fun write(inputStream: InputStream, outputStream: OutputStream) {
        var len: Int
        val buffer = ByteArray(4096)
        while (((inputStream.read(buffer)).also { len = it }) != -1) {
            outputStream.write(buffer, 0, len)
        }
    }
}