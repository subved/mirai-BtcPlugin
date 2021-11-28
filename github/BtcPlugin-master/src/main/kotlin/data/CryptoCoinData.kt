package data

import com.dakuo.BtcPlugin
import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.value

object CryptoCoinData : AutoSavePluginData("BtcPlugin-data") {
    // 记录群的币价查询策略
    var groupPolicy: MutableMap<Long, Int> by value(mutableMapOf())
}