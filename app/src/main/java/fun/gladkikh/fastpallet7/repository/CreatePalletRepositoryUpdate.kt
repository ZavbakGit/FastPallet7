package `fun`.gladkikh.fastpallet7.repository

import `fun`.gladkikh.fastpallet7.db.dao.CreatePalletUpdateDao
import `fun`.gladkikh.fastpallet7.map.toDb
import `fun`.gladkikh.fastpallet7.map.toObject
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.BoxCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.CreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.PalletCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.ProductCreatePallet


class CreatePalletRepositoryUpdate(val createPalletUpdateDao: CreatePalletUpdateDao) {

    fun getListPalletByGuidProduct(guidProduct: String): List<PalletCreatePallet> {
        return createPalletUpdateDao.getListPalletCreatePalletByGuidProduct(guidProduct).map {
            it.toObject()
        }
    }

    fun getListProductCreatPalletByGuidDoc(guidDoc: String): List<ProductCreatePallet> {
        return createPalletUpdateDao.getProductListCreatePalletByGuidDoc(guidDoc).map {
            it.toObject()
        }
    }

    inline fun <reified T> getObjectCreatePalletByGuid(guid: String): Any? {
        return when (T::class.java) {
            BoxCreatePallet::class.java -> createPalletUpdateDao.getBoxCreatPalletByGuid(guid)?.toObject()
            PalletCreatePallet::class.java -> createPalletUpdateDao.getPalletCreatePalletByGuid(guid)?.toObject()
            ProductCreatePallet::class.java -> createPalletUpdateDao.getProductCreatePalletByGuid(
                guid
            )?.toObject()
            CreatePallet::class.java -> createPalletUpdateDao.getCreatePalletByGuid(guid)?.toObject()
            else -> throw Throwable("Объект не в базе!")
        }
    }

    inline fun <reified T> getObjectCreatePalletByGuidServer(guidServer: String): Any? {
        return when (T::class.java) {
            ProductCreatePallet::class.java -> createPalletUpdateDao.getProductCreatePalletByGuidServer(
                guidServer
            )?.toObject() as? T?
            CreatePallet::class.java -> createPalletUpdateDao.getCreatePalletByGuidServer(guidServer)?.toObject() as? T?
            else -> throw Throwable("Объект не в базе!")
        }
    }

    fun <T> save(intety: T) {
        when (intety) {
            is BoxCreatePallet -> createPalletUpdateDao.insertOrUpdate(intety.toDb())
            is PalletCreatePallet -> createPalletUpdateDao.insertOrUpdate(intety.toDb())
            is ProductCreatePallet -> createPalletUpdateDao.insertOrUpdate(intety.toDb())
            is CreatePallet -> createPalletUpdateDao.insertOrUpdate(intety.toDb())
            else -> throw Throwable("Этот объект нельзя сохранить! ${intety.toString()}")
        }
    }

    fun <T> delete(intety: T) {
        when (intety) {
            is BoxCreatePallet -> createPalletUpdateDao.delete(intety.toDb())
            is PalletCreatePallet -> createPalletUpdateDao.delete(intety.toDb())
            is ProductCreatePallet -> createPalletUpdateDao.delete(intety.toDb())
            is CreatePallet -> createPalletUpdateDao.delete(intety.toDb())
            else -> throw Throwable("Этот объект нельзя удалять! ${intety.toString()}")
        }
    }

    fun saveListBox(list: List<BoxCreatePallet>) {
        list.forEach {
            createPalletUpdateDao.insertIgnore(it.toDb())
        }
        //createPalletUpdateDao.insertListBox(list.map { it.toDb() })
    }
}