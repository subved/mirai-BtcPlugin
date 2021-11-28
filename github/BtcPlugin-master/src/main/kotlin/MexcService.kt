package com.dakuo


import com.google.gson.Gson
import okhttp3.*


class MexcService {
    fun getDetail(symbol: String): ResultBean? {
        val client = OkHttpClient()
        val request = Request.Builder().url("https://www.mexc.com/api/platform/spot/market/symbol?symbol=$symbol").get().build()
        val call = client.newCall(request)
        val string = call.execute().body?.string()
        return Gson().fromJson(string, ResultBean::class.java)
    }

    data class ResultBean(
        val code: String,
        val data: ticker
    )

    data class ticker(
        val market: String?,
        val currency: String?,
        val fullName: String?,
        val currencyDisplayName: String?,
        val priceScale: Int,
        val quantityScale: Int,
        val sort: Int,
        val iou: Int,
        val suggest: Int,
        val status: Int,
        val icon: String?,
        val type: String?,
        val beforeOrderSide: String?,
        val fly: Boolean,
        val buyFeeRate: Double,
        val sellFeeRate: Double,
        val rate: Double,
        val percentChangeVolume24h: Double,
        val etf: Boolean,
        val assessEndTime: Long,
        val countDownMark: Int,
        val etfMark: Int,
        val marketOrderEnabled: Boolean,
        val marketOrderWarnThreshold: Double,
        val c: Double,
        val o: Double,
        val h: Double,
        val l: Double,
        val q: Double,
        val a: Double

    )
}



