package `fun`.gladkikh.fastpallet7.model.usecase.creatpallet

import `fun`.gladkikh.fastpallet7.common.getWeightByBarcode
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.BoxCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.CreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.PalletCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.ProductCreatePallet
import `fun`.gladkikh.fastpallet7.model.usecase.check.CheckDocumentUseCase
import `fun`.gladkikh.fastpallet7.repository.CreatePalletRepositoryUpdate
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.util.*


class BoxCreatePalletUseCase(
    private val createPalletRepositoryUpdate: CreatePalletRepositoryUpdate,
    private val checkDocumentUseCase: CheckDocumentUseCase
) {


    fun saveBoxByBarcodeFlowable(barcode: String, guidPallet: String): Flowable<BoxCreatePallet> {
        return Flowable.just(barcode)
            .observeOn(Schedulers.io())
            .map {
                val data = getDataBoxByGuidPallet(guidPallet)
                data.box = getBoxByBarcode(
                    barcode = barcode,
                    guidPallet = data.pallet.guid
                )
                return@map data

            }
            .flatMap {
                return@flatMap it.box?.let { it1 -> saveBoxFlowable(it1) }
            }
    }

    fun saveBoxByCountFlowable(count: Float, guidPallet: String): Flowable<BoxCreatePallet> {
        return Flowable.just(count)
            .observeOn(Schedulers.io())
            .map {
                val data = getDataBoxByGuidPallet(guidPallet)
                val box = BoxCreatePallet(
                    guid = UUID.randomUUID().toString(),
                    guidPallet = guidPallet,
                    barcode = "",
                    countBox = 1,
                    count = count,
                    dateChanged = Date()
                )
                data.box = box
                return@map data
            }
            .flatMap {
                return@flatMap it.box?.let { it1 -> saveBoxFlowable(it1) }
            }
    }

    fun saveBoxFlowable(box: BoxCreatePallet): Flowable<BoxCreatePallet> {
        return Flowable.just(box)
            .observeOn(Schedulers.io())
            .map {
                val data = getDataBoxByGuidPallet(box.guidPallet)
                data.box = box
                return@map data
            }
            .flatMap {
                if (checkDocumentUseCase.checkEditDocByStatus(it.doc.status)) {
                    return@flatMap Flowable.just(it)
                } else {
                    return@flatMap Flowable.error<Throwable>(Throwable("Нелья изменять документ"))
                }
            }
            .map {
                it as DataBox
                it.box?.dateChanged = Date()
                createPalletRepositoryUpdate.save(it.box)
                return@map it.box
            }
    }

    fun deleteBox(box: BoxCreatePallet?): Completable {
        return Flowable.just(box)
            .observeOn(Schedulers.io())
            .map {
                val data = getDataBoxByGuidPallet(box!!.guidPallet)
                data.box = box
                return@map data
            }
            .flatMap {
                if (checkDocumentUseCase.checkEditDocByStatus(it.doc.status)) {
                    return@flatMap Flowable.just(it)
                } else {
                    return@flatMap Flowable.error<Throwable>(Throwable("Нелья изменять документ"))
                }
            }
            .map {
                it as DataBox
                createPalletRepositoryUpdate.delete(it.box)
                return@map it.box
            }
            .ignoreElements()
    }

    private fun getBoxByBarcode(barcode: String, guidPallet: String): BoxCreatePallet {
        val dataBox = getDataBoxByGuidPallet(guidPallet)

        val weight = getWeightByBarcode(
            barcode = barcode,
            start = dataBox.product.weightStartProduct ?: 0,
            finish = dataBox.product.weightEndProduct ?: 0,
            coff = dataBox.product.weightCoffProduct ?: 0f
        )

        if (weight == 0f) {
            throw Throwable("Ошибка считывания веса!")
        }

        val box = BoxCreatePallet(
            guid = UUID.randomUUID().toString(),
            guidPallet = guidPallet,
            barcode = barcode,
            countBox = 1,
            count = weight,
            dateChanged = Date()
        )

        return box
    }

    private fun getDataBoxByGuidPallet(guidPallet: String): DataBox {
        val pallet = createPalletRepositoryUpdate
            .getObjectCreatePalletByGuid<PalletCreatePallet>(guidPallet) as PalletCreatePallet

        val product = createPalletRepositoryUpdate
            .getObjectCreatePalletByGuid<ProductCreatePallet>(pallet.guidProduct) as ProductCreatePallet

        val doc = createPalletRepositoryUpdate
            .getObjectCreatePalletByGuid<CreatePallet>(product.guidDoc) as CreatePallet


        return DataBox(
            doc = doc,
            product = product,
            pallet = pallet
        )
    }

    private data class DataBox(
        val doc: CreatePallet,
        val product: ProductCreatePallet,
        val pallet: PalletCreatePallet,
        var box: BoxCreatePallet? = null
    )

}



