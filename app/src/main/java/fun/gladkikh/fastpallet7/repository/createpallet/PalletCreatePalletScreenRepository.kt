package `fun`.gladkikh.fastpallet7.repository.createpallet

import `fun`.gladkikh.fastpallet7.db.dao.PalletCreatePalletScreenDao
import `fun`.gladkikh.fastpallet7.map.toObject
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.BoxCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.CreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.PalletCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.ProductCreatePallet
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

class PalletCreatePalletScreenRepository(
    private val palletScreenRepository: PalletCreatePalletScreenDao
) {
    fun getDoc(guidPallet: String): LiveData<CreatePallet> {
        return Transformations.map(
            //TODO Переделать
            palletScreenRepository.getDoc("0")
        ) {
            it?.toObject()
        }
    }

    fun getProduct(guidBox: String): LiveData<ProductCreatePallet> {
        return Transformations.map(
            //TODO Переделать
            palletScreenRepository.getProduct("0_0")
        ) {
            it?.toObject()
        }
    }

    fun getPallet(guidPallet: String): LiveData<PalletCreatePallet> {
        return Transformations.map(
            palletScreenRepository.getPallet(guidPallet)
        ) {
            it?.toObject()
        }
    }

    fun getListBox(guidPallet: String): LiveData<List<BoxCreatePallet>> {
        return Transformations.map(
            palletScreenRepository.getListBox(guidPallet)
        ) {
            it.map {
                it.toObject()
            }
        }
    }

}