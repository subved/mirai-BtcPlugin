package config

import net.mamoe.mirai.console.data.ReadOnlyPluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object CommandConfig : ReadOnlyPluginConfig("Command") {


    @ValueDescription("修改触发的指令")
    val get: MutableList<String> by value(mutableListOf("矿难了", "搜币价"))
    val off: MutableList<String> by value(mutableListOf("关闭插件", "封印"))
    val symbolmaps: MutableMap<String,String> by value(mutableMapOf("1" to "BTC", "2" to "LTC", "1027" to "ETH"))
    val setCryptoSearchOn: MutableList<String> by value(mutableListOf("开启币价查询","开启矿难了"))

}