package config

import net.mamoe.mirai.console.data.ReadOnlyPluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object MessageConfig : ReadOnlyPluginConfig("Message") {
//    @ValueDescription(
//        """
//        发送信息的格式
//        pid             图片pid
//        p               图片号
//        uid             作者uid
//        title           题目
//        author          作者
//        url             原始url
//        r18             是否为r18
//        width           宽度
//        height          高度
//        tags            标签
//        """
//    )
//    val setuReply by value(
//        """
//            pid: %pid%
//            title: %title%
//            author: %author%
//            tags: %tags%
//            url: %url%
//        """.trimIndent()
//    )

//    @ValueDescription("来自 lolicon 的报错, code(错误代码), msg(错误信息)")
//    val setuFailureCode401 by value("Api 错误代码：%code%")
//    val setuFailureCode404 by value("没有搜索到指定的色图")
//    val setuFailureCode429 by value("ApiKey 超过调用上线")
//    val setuFailureCodeElse by value("Api 错误代码：%code%")
//
//    @ValueDescription("下载图片出现404自动回复")
//    val setuImage404 by value("图片获取失败, 可能图片已经被原作者删除")

    @ValueDescription("群友没有权限的时候的自动回复")
    val setuNoPermission by value("您没有权限操作")

    @ValueDescription("币价查询插件未启用自动回复")
    val setuModeOff by value("本群尚未开启插件,请联系管理员")

//    @ValueDescription("搜色图提醒关键词自动回复")
//    val setuSearchKeyNotSet by value("下一条消息请输入搜索的关键词")

    @ValueDescription("关闭币价查询的自动回复")
    val cryptoSearchOff by value("已经关闭了币价查询")
    val cryptoSearchOffAlready by value("本群尚未启用开启币价查询插件,无需再次禁用")

//    @ValueDescription("设置普通模式的自动回复")
//    val setuSafe by value("切换为普通模式")
//    val setuSafeAlready by value("本群色图已经为普通模式, 无需切换")
//
//    @ValueDescription("设置R-18模式的自动回复")
//    val setuNsfw by value("切换为R-18模式")
//    val setuNsfwAlready by value("本群色图已经为R-18模式, 无需切换")
//
    @ValueDescription("开启币价查询的自动回复")
    val cryptoSearchOn by value("开启币价查询")
    val cryptoSearchOnAlready by value("本群已经开启币价查询")
}