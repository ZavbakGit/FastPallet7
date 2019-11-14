package `fun`.gladkikh.fastpallet7.model.entity.creatpallet

import `fun`.gladkikh.fastpallet7.model.Status
import java.util.*


data class CreatePallet(
    val guid: String,
    val number: String?,
    val date: Date?,
    val status: Status?,
    var guidServer: String?,
    var dateChanged: Date?,
    var isLastLoad: Boolean?,
    var description: String?,
    var barcode: String?
)

data class ProductCreatePallet(
    val guid: String,
    val guidDoc: String,
    var number: String?,
    var barcode: String?,

    var guidProductBack: String?,
    var nameProduct: String?,
    var codeProduct: String?,
    var ed: String?,
    var edCoff: Float?,

    var weightBarcode: String?,
    var weightStartProduct: Int?,
    var weightEndProduct: Int?,
    var weightCoffProduct: Float?,

    var countBack: Float?,  //Количество из Back
    var countBoxBack: Int?, //Количество Мест Back

    var count:Float?, //Количество
    var countBox: Int?, //Количество мест
    var countRow:Int?,  //Количество строк
    var countPallet: Int?, //Количество паллет

    var dateChanged: Date?,
    var isLastLoad: Boolean?
)

data class PalletCreatePallet(
    val guid: String,
    var guidProduct: String,
    var number: String?,
    var barcode: String?,

    var dateChanged: Date?,
    var nameProduct: String?,
    var state: String?,
    var sclad: String?,

    var count:Float?, //Количество
    var countBox: Int?, //Количество мест
    var countRow:Int?  //Количество строк
)

data class BoxCreatePallet(
    val guid: String,
    var guidPallet: String,
    var barcode: String?,

    var count:Float?, //Количество
    var countBox: Int?, //Количество мест

    var dateChanged: Date?
)