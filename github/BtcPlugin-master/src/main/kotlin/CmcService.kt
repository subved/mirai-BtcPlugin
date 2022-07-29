package com.subved


import com.google.gson.Gson
import config.CommandConfig
import config.SettingsConfig
import io.ktor.client.engine.*
import io.ktor.client.request.*
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit


class CmcService {


    fun getDetail(symbolsList: List<String>): ResultBean? {

        var proxy = when (SettingsConfig.proxyConfig) {
            0 -> null
            1 -> ProxyBuilder.http(SettingsConfig.httpProxy.proxy)
            2 -> ProxyBuilder.socks(host = SettingsConfig.socksProxy.host, port = SettingsConfig.socksProxy.port)
            else -> null
        }

        val client = OkHttpClient().newBuilder().connectTimeout(5, TimeUnit.SECONDS)
            .callTimeout(10, TimeUnit.SECONDS)
            .pingInterval(5, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            //.addInterceptor(RetryIntercepter(3))
            .proxy(proxy)
            .build()

        var symbols ="";
        for (symbol in CommandConfig.symbols){
            if (symbols!=""){
                symbols= "$symbols,$symbol";
            }
            else{
                symbols+=symbol
            }
        }
        println(symbols)
        for (index in 1..3 ){
            try {
                val request =
                    Request.Builder().url("https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?id=${symbols}")
                        .get()
                        .addHeader("Accept", "*/*")
                        //.addHeader("Accept-Encoding","deflate, gzip")
                        .addHeader("X-CMC_PRO_API_KEY", "11953a77-965d-4696-804a-4be9054d5ebf")
                        .build()
//        val response = client.newCall(request)
                val call = client.newCall(request)
//        //异步请求
//        call.enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                Log.d("UPDATE", "onFailure: $e")
//            }
//
//            @Throws(IOException::class)
//            override fun onResponse(call: Call, response: Response) {
//                Log.d("UPDATE", "OnResponse: " + response.body?.string())
//            }
//        })
                val response = call.execute().body?.string()
                return Gson().fromJson(response, ResultBean::class.java)
            }
            catch (e :Exception){
                println("retry: "+index +"    " +
                        "error : "+ e)
            }
        }
        val response ="";
        return Gson().fromJson(response, ResultBean::class.java)
    }

    data class ResultBean(
        val status: StatusBean,
        val data: HashMap<String,Ticker>
    )

    data class StatusBean(
        val timestamp: String,
        val error_code: Int,
        val error_message: String?,
        val elapsed: Int,
        val credit_count: Int,
        val notice: String?,
        )

    data class Ticker(
//        val id: Int?,
//        val name: String?,
//        val symbol: String?,
//        val slug: String?,
//        val num_market_pairs: Int?,
//        val date_added: String?,
//        val max_supply: Int,
//        val circulating_supply: Double?,
//        val total_supply: Double?,
//        val is_active: Int?,
//        val cmc_rank: Int?,
//        val is_fiat: Int?,
//        val last_updated: String?,
        val quote: HashMap<String,Price>,


    )

    data class Price(
        val price: Double,
//        val volume_24h: Double?,
//        val volume_change_24h: Double?,
//        val percent_change_1h: Double?,
//        val percent_change_24h: Double,
//        val percent_change_7d: Double?,
//        val percent_change_30d: Double,
//        val percent_change_60d: Double,
//        val percent_change_90d: Double,
//        val market_cap: Double,
//        val market_cap_dominance: Double?,
//        val fully_diluted_market_cap: Double?,
//        val last_updated: String?,
        )
}



