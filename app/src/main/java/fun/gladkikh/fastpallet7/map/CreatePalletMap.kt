package `fun`.gladkikh.fastpallet7.map

import `fun`.gladkikh.fastpallet7.db.intity.createpallet.BoxCreatePalletDb
import `fun`.gladkikh.fastpallet7.db.intity.createpallet.CreatePalletDb
import `fun`.gladkikh.fastpallet7.db.intity.createpallet.PalletCreatePalletDb
import `fun`.gladkikh.fastpallet7.db.intity.createpallet.ProductCreatePalletDb
import `fun`.gladkikh.fastpallet7.model.Status
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.BoxCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.CreatePallet

import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.PalletCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.ProductCreatePallet

import java.util.*

fun CreatePalletDb.toObject(): CreatePallet {
    return CreatePallet(
        guid = guid,
        barcode = barcode,
        number = number,
        guidServer = guidServer,
        date = this.date?.let { Date(it) },
        description = description,
        status = Status.getStatusById(status),
        dateChanged = this.dateChanged?.let { Date(it) },
        isLastLoad = isLastLoad
    )
}


fun CreatePallet.toDb(): CreatePalletDb {
    return CreatePalletDb(
        guid = guid,
        barcode = barcode,
        number = number,
        guidServer = guidServer,
        date = date?.time,
        description = description,
        status = status?.id,
        dateChanged = dateChanged?.time,
        isLastLoad = isLastLoad
    )
}

fun ProductCreatePalletDb.toObject(): ProductCreatePallet {
    return ProductCreatePallet(
        guid = guid,
        isLastLoad = isLastLoad,
        dateChanged = this.dateChanged?.let { Date(it) },
        number = number,
        barcode = barcode,
        guidProductBack = guidProductBack,
        guidDoc = guidDoc,
        countBox = countBox,
        countPallet = countPallet,
        weightCoffProduct = weightCoffProduct,
        weightEndProduct = weightEndProduct,
        weightStartProduct = weightStartProduct,
        weightBarcode = weightBarcode,
        edCoff = edCoff,
        ed = ed,
        codeProduct = codeProduct,
        nameProduct = nameProduct,
        count = count,
        countBack = countBack,
        countBoxBack = countBoxBack,
        countRow = countRow
    )
}

fun ProductCreatePallet.toDb(): ProductCreatePalletDb {
    return ProductCreatePalletDb(
        guid = guid,
        isLastLoad = isLastLoad,
        dateChanged = dateChanged?.time,
        number = number,
        barcode = barcode,
        guidProductBack = guidProductBack,
        guidDoc = guidDoc,
        countBox = countBox,
        countPallet = countPallet,
        weightCoffProduct = weightCoffProduct,
        weightEndProduct = weightEndProduct,
        weightStartProduct = weightStartProduct,
        weightBarcode = weightBarcode,
        edCoff = edCoff,
        ed = ed,
        codeProduct = codeProduct,
        nameProduct = nameProduct,
        count = count,
        countBack = countBack,
        countBoxBack = countBoxBack,
        countRow = countRow
    )
}

fun PalletCreatePalletDb.toObject(): PalletCreatePallet {
    return PalletCreatePallet(
        guid = guid,
        countRow = countRow,
        count = count,
        nameProduct = nameProduct,
        countBox = countBox,
        guidProduct = guidProduct,
        barcode = barcode,
        number = number,
        dateChanged = this.dateChanged?.let { Date(it) },
        state = state,
        sclad = sclad
    )
}

fun PalletCreatePallet.toDb(): PalletCreatePalletDb {
    return PalletCreatePalletDb(
        guid = guid,
        countRow = countRow,
        count = count,
        nameProduct = nameProduct,
        countBox = countBox,
        guidProduct = guidProduct,
        barcode = barcode,
        number = number,
        dateChanged = dateChanged?.time,
        state = state,
        sclad = sclad
    )
}

fun BoxCreatePalletDb.toObject(): BoxCreatePallet {
    return BoxCreatePallet(
        guid = guid,
        dateChanged = this.dateChanged?.let { Date(it) },
        barcode = barcode,
        countBox = countBox,
        count = count,
        guidPallet = guidPallet
    )
}

fun BoxCreatePallet.toDb(): BoxCreatePalletDb {
    return BoxCreatePalletDb(
        guid = guid,
        dateChanged = dateChanged?.time,
        barcode = barcode,
        countBox = countBox,
        count = count,
        guidPallet = guidPallet
    )
}


