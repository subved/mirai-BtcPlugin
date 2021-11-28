package com.dakuo

import com.dakuo.BtcPlugin.checkPermission
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

    suspend fun monitor(){
        val message = event.message;
        val plainText = getPlainText(message);
        var result = "";
        if (CommandConfig.get.contains(plainText)) {
            if (CryptoCoinData.groupPolicy[event.group.id] != null) {
                val hashMap:HashMap<String,Double> = HashMap<String,Double>() //define empty hashma

                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val formatted = current.format(formatter)
                //Date(f1)
                result = "$formatted 即使行情: \n"
                for(symbols in CommandConfig.symbols){
                    val detail = MexcService().getDetail(symbols + "_USDT")
                    if (detail != null) {
                        val data = detail.data
                        if (detail.code == "200") {
                            hashMap.put(symbols,data.o)
                        }
                    }
                }

                if (hashMap.size>0){
                    for(key in hashMap.keys){
                        result +="$key 当前币价 ${hashMap[key]} 美元\n"
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