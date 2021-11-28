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


}