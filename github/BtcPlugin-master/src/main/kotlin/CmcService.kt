package com.subved


import com.google.gson.Gson
import config.CommandConfig
import okhttp3.*


class CmcService {
    fun getDetail(symbolsList: List<String>): ResultBean? {
        val client = OkHttpClient()
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
        val request = Request.Builder().url("https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?symbol=${symbols}").get()
            .addHeader("Accept","*/*")
            //.addHeader("Accept-Encoding","deflate, gzip")
            .addHeader("X-CMC_PRO_API_KEY","11953a77-965d-4696-804a-4be9054d5ebf")
            .build()
        val call = client.newCall(request)
        val string = call.execute().body?.string()
        return Gson().fromJson(string, ResultBean::class.java)
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



