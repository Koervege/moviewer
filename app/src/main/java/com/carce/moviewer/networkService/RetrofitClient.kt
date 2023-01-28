package com.carce.moviewer.networkService

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val TIME_OUT: Long = 300

    private val gson = GsonBuilder().setLenient().create()

    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(object : Interceptor {

            override fun intercept(chain: Interceptor.Chain): Response {

                return try {
                    val resp = chain.proceed(chain.request())
                    if (resp.code() == 200) {
                        try {
                            resp.peekBody(2048)
                                .string()
                        } catch (e: Exception) {

                            println("Error parse json from intercept..............")
                        }
                    } else {
                        println(resp)
                    }
                    return resp

                } catch (e: IOException) {
                    e.printStackTrace()
                        Response.Builder()
                            .code(418)
                            .request(chain.request())
                            .body(object : ResponseBody() {
                                override fun contentLength() = 0L
                                override fun contentType(): MediaType? = null
                                override fun source(): BufferedSource = Buffer()
                            })
                            .message(e.message ?: e.toString())
                            .protocol(Protocol.HTTP_1_1)
                            .build()
//                    }

                }

            }

        })
        .build()

    val retrofit: RetrofitServices by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(ApiURL.BASE_URL)
            /*.client(okHttpClient)*/
            .build().create(RetrofitServices::class.java)
    }

}
