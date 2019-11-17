package `fun`.gladkikh.fastpallet7.repository.createpallet

import `fun`.gladkikh.fastpallet7.db.dao.BoxCreatePalletScreenDao
import `fun`.gladkikh.fastpallet7.map.toObject
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.BoxCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.CreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.PalletCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.ProductCreatePallet
import `fun`.gladkikh.fastpallet7.repository.CreatePalletRepositoryUpdate
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

class BoxCreatePalletScreenRepository(
    private val boxCreatePalletScreenDao: BoxCreatePalletScreenDao,
    private val createPalletRepositoryUpdate: CreatePalletRepositoryUpdate
) {

    fun getDoc(guidBox: String): LiveData<CreatePallet> {
        return Transformations.map(
            //TODO Переделать
            boxCreatePalletScreenDao.getDoc("0")
        ) {
            it?.toObject()
        }
    }

    fun getProduct(guidBox: String): LiveData<ProductCreatePallet> {
        return Transformations.map(
            //TODO Переделать
            boxCreatePalletScreenDao.getProduct("0_0")
        ) {
            it?.toObject()
        }
    }

    fun getPallet(guidBox: String): LiveData<PalletCreatePallet> {
        return Transformations.map(
            boxCreatePalletScreenDao.getPallet(guidBox)
        ) {
            it?.toObject()
        }
    }

    fun getBox(guidBox: String): LiveData<BoxCreatePallet> = Transformations.map(
        boxCreatePalletScreenDao.getBox(guidBox)
    ) {
        it?.toObject()
    }

    fun saveBox(box: BoxCreatePallet) {
        createPalletRepositoryUpdate.save(box)
    }
}