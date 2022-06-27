package com.example.api.clients

import com.google.gson.Gson
import okhttp3.*
import okio.Buffer
import okio.BufferedSource
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

class ApiClientConfig(
    val authenticator: Authenticator? = null,
    val interceptors: List<Interceptor> = listOf()
)

abstract class BaseApiClient(
    protected val config: ApiClientConfig,
    protected val gson: Gson
) {

    private val pool = ConnectionPool(1, 2, TimeUnit.MINUTES)

    protected fun <T> configRetrofit(service: Class<T>, baseUrl: String): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(configClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(service)
    }

    private fun configClient(): OkHttpClient =
        OkHttpClient.Builder().run {
            if (config.authenticator != null) {
                authenticator(config.authenticator)
            }

            if (config.interceptors.isNotEmpty()) {
                config.interceptors.forEach { interceptor ->
                    addInterceptor(interceptor)
                }
            }

            connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            readTimeout(TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            connectionPool(pool)
            addInterceptor(configLoggingInterceptor())

            build()
        }


    private fun configLoggingInterceptor(): Interceptor =
        Interceptor {
            val request: Request = it.request()
            val response: Response = it.proceed(request)
            if (response.code > 205 || response.code < 200) {
                val responseBody: ResponseBody? = response.body
                val source: BufferedSource? = responseBody?.source()
                source?.request(Long.MAX_VALUE)
                val buffer: Buffer? = source?.buffer
                val utf8: Charset = Charset.forName("UTF-8")

                val logMessage = buffer?.clone()?.readString(utf8)
                val exception = Exception("Error code: ${response.code} REQUEST_URL: $request")

                println(logMessage)
                println(exception)
            }
            response
        }

    companion object {
        private const val TIMEOUT = 60L
    }
}
