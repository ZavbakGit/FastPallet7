package `fun`.gladkikh.fastpallet7.di

import `fun`.gladkikh.fastpallet7.db.AppDatabase
import `fun`.gladkikh.fastpallet7.db.dao.BoxCreatePalletScreenDao
import `fun`.gladkikh.fastpallet7.db.dao.CreatePalletUpdateDao
import `fun`.gladkikh.fastpallet7.db.dao.PalletCreatePalletScreenDao
import `fun`.gladkikh.fastpallet7.model.usecase.check.CheckDocumentUseCase
import `fun`.gladkikh.fastpallet7.model.usecase.creatpallet.BoxCreatePalletUseCase
import `fun`.gladkikh.fastpallet7.model.usecase.creatpallet.PalletCreatePalletUseCase
import `fun`.gladkikh.fastpallet7.model.usecase.recalcdb.RecalcDbUseCase
import `fun`.gladkikh.fastpallet7.model.usecase.testdata.AddTestDataUseCase
import `fun`.gladkikh.fastpallet7.repository.CreatePalletRepositoryUpdate
import `fun`.gladkikh.fastpallet7.repository.SettingsRepository
import `fun`.gladkikh.fastpallet7.repository.createpallet.BoxCreatePalletScreenRepository
import `fun`.gladkikh.fastpallet7.repository.createpallet.PalletCreatePalletScreenRepository
import `fun`.gladkikh.fastpallet7.ui.createpallet.box.BoxCreatePalletViewModel
import `fun`.gladkikh.fastpallet7.ui.createpallet.pallet.PalletCreatePalletViewModel
import `fun`.gladkikh.fastpallet7.ui.createpallet.productdialog.ProductDialogCreatePalletViewModel

import `fun`.gladkikh.fastpallet7.ui.test.TestViewModel
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object DependencyModule {

    val appModule = module {
        single { getDataBase(androidContext()) }

        //****************************************************************************************
        //DAO
        single { getCreatePalletUpdateDao(get()) }
        single { getBoxCreatePalletScreenDao(get()) }
        single { getPalletCreatePalletScreenDao(get()) }
        //****************************************************************************************
        //REPOSITORY
        single { SettingsRepository(get()) }
        single { CreatePalletRepositoryUpdate(get()) }
        single { BoxCreatePalletScreenRepository(get()) }
        single { PalletCreatePalletScreenRepository(get()) }
        //****************************************************************************************
        //USE CASE
        single { AddTestDataUseCase(get()) }
        single { RecalcDbUseCase(get()) }
        single { BoxCreatePalletUseCase(get(), get()) }
        single { PalletCreatePalletUseCase(get(), get()) }
        single { CheckDocumentUseCase() }




        viewModel {
            BoxCreatePalletViewModel(
                get(),
                get()
            )
        }

        viewModel {
            PalletCreatePalletViewModel(
                get(),
                get()
            )
        }

        viewModel {
            ProductDialogCreatePalletViewModel(
                get(),
                get()
            )
        }

        viewModel { TestViewModel(get(), get(), get()) }

    }

    private fun getDataBase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "mydatabase")
            .addCallback(
                object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

//                        getListTriggerCreatePallet().forEach {
//                            db.execSQL(it)
//                        }


                    }
                }

            )
            //.allowMainThreadQueries()
            .build()
    }


    private fun getCreatePalletUpdateDao(database: AppDatabase): CreatePalletUpdateDao {
        return database.getCreatePalletUpdateDao()
    }

    private fun getBoxCreatePalletScreenDao(database: AppDatabase): BoxCreatePalletScreenDao {
        return database.getBoxCreatePalletScreen()
    }

    private fun getPalletCreatePalletScreenDao(database: AppDatabase): PalletCreatePalletScreenDao {
        return database.getPalletCreatePalletScreenDao()
    }
}
