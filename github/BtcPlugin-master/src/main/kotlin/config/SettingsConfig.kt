package config

import kotlinx.serialization.Serializable
import net.mamoe.mirai.console.data.ReadOnlyPluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object SettingsConfig : ReadOnlyPluginConfig("Settings") {
    @ValueDescription(
        """
        插件权限控制设置
        0 为所有人都可以控制
        1 为只有插件主人可以进行配置
        2 为群管理员也可以配置
        3 为拥有权限（mirai-setu:admin）者可以配置
        """
    )
    val permitMode by value(1)

    @ValueDescription("设置此插件主人的id。")
    val masterId by value(mutableListOf<Long>(123456))

    @ValueDescription(
        """
        代理设置
        0   不使用代理
        1   使用http代理
        2   使用socks代理
        代理只对 LoliconApi 色图的获取生效
        """
    )
    val proxyConfig by value(0)
    val httpProxy by value(HttpProxy())
    val socksProxy by value(SocksProxy())

    @Serializable
    data class SocksProxy(
        val host: String = "127.0.0.1",
        val port: Int = 4001
    )

    @Serializable
    data class HttpProxy(
        val proxy: String = "http://127.0.0.1:80"
    )


}