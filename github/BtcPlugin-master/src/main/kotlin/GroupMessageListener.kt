package com.subved

import com.subved.BtcPlugin.checkPermission
import config.CommandConfig
import config.MessageConfig
import data.CryptoCoinData
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.HashMap

class GroupMessageListener(event: GroupMessageEvent) {

    var event = event;

    var idMap = hashMapOf(1 to "BTC", 2 to "LTC", 3 to "NMC",4 to "TRC",
        5 to "PPC",1027 to "ETH",1321 to "ETC",5632 to "AR",9258 to "XCH",9891 to "BNX",12082 to "GOLD",
        5805 to "AVAX",10334 to "BABY",15158 to "AWOOL")



    suspend fun monitor(){
        val message = event.message;
        val plainText = getPlainText(message);
        var result = "";
        if (CommandConfig.get.contains(plainText)) {
            if (CryptoCoinData.groupPolicy[event.group.id] != null) {
                val hashMap:HashMap<String,Double?> = HashMap<String,Double?>() //define empty hashma
                var symbols = CommandConfig.symbols
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val formatted = current.format(formatter)
                //Date(f1)
                result = "$formatted 即时行情: \n"
//                for(symbols in CommandConfig.symbols){
//                    val detail = MexcService().getDetail(symbols + "_USDT")
//                    if (detail != null) {
//                        val data = detail.data
//                        if (detail.code == "200") {
//                            hashMap.put(symbols,data.c)
//                        }
//                    }
//                }

                val detail =CmcService().getDetail(CommandConfig.symbols);
                for (symbol in CommandConfig.symbols){
                    var price = detail?.data?.get(symbol)?.quote?.get("USD")?.price;
                    hashMap.put(symbol,price);
                }


                if (hashMap.size>0){
                    for(key in symbols){
                        if (hashMap[key]!! <= 1.0){
                            result +="${idMap[key.toInt()]} 当前币价 ${String.format("%.6f", hashMap[key])} 美元\n"
                        }
                        else if (hashMap[key]!! <= 10.0){
                            result +="${idMap[key.toInt()]} 当前币价 ${String.format("%.4f", hashMap[key])} 美元\n"
                        }
                        else{
                            result +="${idMap[key.toInt()]} 当前币价 ${String.format("%.2f", hashMap[key])} 美元\n"
                        }
                    }

                    event.group.sendMessage(
                        At(event.sender).plus("\n").plus(
                            result.trimIndent()
                        )
                    )
                }
            }
        }


    }

    suspend fun monitorSwitch() {
        val message = event.message;
        val plainText = getPlainText(message);
        // 关闭币价查询插件
        if (CommandConfig.off.contains(plainText)) {
            if (checkPermission(event.sender)) {
                if (CryptoCoinData.groupPolicy[event.group.id] == null) {
                    event.group.sendMessage(MessageConfig.cryptoSearchOffAlready)
                } else {
                    event.group.sendMessage(MessageConfig.cryptoSearchOff)
                    CryptoCoinData.groupPolicy.remove(event.group.id)
                }
            } else {
                event.group.sendMessage(MessageConfig.setuNoPermission)
            }
        }

        // 开启
        if (CommandConfig.setCryptoSearchOn.contains(message.contentToString())) {
            if (checkPermission(event.sender)) {
                if (CryptoCoinData.groupPolicy[event.group.id] == 1) {
                    event.group.sendMessage(MessageConfig.cryptoSearchOnAlready)
                } else {
                    event.group.sendMessage(MessageConfig.cryptoSearchOn)
                    CryptoCoinData.groupPolicy[event.group.id] = 1
                }
            } else {
                event.group.sendMessage(MessageConfig.setuNoPermission)
            }
        }
    }

    fun getPlainText(str:MessageChain):String{
        val content = str.content.toPlainText();
        return if(content.equals("")){
            "";
        }else{
            content.contentToString().trim();
        }
    }

    fun getUpDown(close: Double, open:Double): Double {
        return (close-open)/open
    }

}