package com.dakuo

import config.CommandConfig
import config.MessageConfig
import config.SettingsConfig
import net.mamoe.mirai.console.permission.Permission
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.contact.isOperator
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.utils.info

object BtcPlugin : KotlinPlugin(
    JvmPluginDescription(
        id = "com.subved.BtcPlugin",
        name = "BtcPlugin",
        version = "1.0-SNAPSHOT",
    ) {
        author("subved")
    }
) {
    private lateinit var adminPermission: Permission

    override fun onEnable() {
        CommandConfig.reload()   //初始化插件指令
        MessageConfig.reload()   //初始化插件指令
        SettingsConfig.reload()   //初始化插件指令
        GlobalEventChannel.subscribeAlways<GroupMessageEvent> {event-> GroupMessageListener(event).monitorSwitch()}
        GlobalEventChannel.subscribeAlways<GroupMessageEvent> {event-> GroupMessageListener(event).monitor()}


        logger.info { "搜索币价插件加载成功!" }
    }

    // 权限判断（获取以后会搞上更好的方法？）
    fun checkPermission(sender: Member): Boolean {
        when (SettingsConfig.permitMode) {
            0 -> {
                return true
            }
            1 -> {
                if (SettingsConfig.masterId.contains(sender.id))
                    return true
            }
            2 -> {
                if (SettingsConfig.masterId.contains(sender.id))
                    return true
                return sender.isOperator()
            }
            else -> {
                BtcPlugin.logger.warning("权限设置信息错误, 请检查权限模式配置")
                return false
            }
        }
        return false
    }
}

