package `fun`.gladkikh.fastpallet7.model.entity.document

import `fun`.gladkikh.fastpallet7.model.Status
import `fun`.gladkikh.fastpallet7.model.Type
import java.util.*

abstract class Document(val type: Type) {
    abstract val guid: String
    abstract val number: String?
    abstract val date: Date?
    abstract val status: Status?
    abstract var guidServer: String?
    abstract var dateChanged: Date?
    abstract var isLastLoad: Boolean?
    abstract var description: String?
    abstract var barcode: String?
}