package com.subved

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class RetryIntercepter(var maxRetry:Int = 3) :Interceptor  {
    var retryNum=0

    fun RetryIntercepter(maxRetry: Int) {
        this.maxRetry = maxRetry
    };

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        var response: Response? = null;
        for (retryNum in 1..maxRetry) {
            try {
                println("retryNum=$retryNum")
                response = chain.proceed(request)

            }
            catch (e:Exception){
                println(e)
            }
            if (response!=null && response.isSuccessful){
                return response
            }
        }
        return chain.proceed(request)
    }
}