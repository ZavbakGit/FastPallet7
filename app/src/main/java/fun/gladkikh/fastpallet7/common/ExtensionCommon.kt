package `fun`.gladkikh.fastpallet7.common

import java.text.SimpleDateFormat
import java.util.*

fun Date.toSimpleDateTime() : String {
    val format = SimpleDateFormat("dd.mm.yyyy hh:mm:ss")
    return format.format(this)
}

fun Date.toSimpleDate() : String {
    val format = SimpleDateFormat("dd.mm.yyyy")
    return format.format(this)
}

fun String.getDecimalStr():String{
    return "[^\\d,.]".toRegex().replace(this, "").replace(",",".")
}

fun String.getIntByParseStr():Int{
    return this.getDecimalStr().toIntOrNull()?:0
}

fun String.getFloatByParseStr():Float{
    return this.getDecimalStr().toFloatOrNull()?:0f
}