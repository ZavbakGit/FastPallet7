package `fun`.gladkikh.fastpallet7.di

import `fun`.gladkikh.fastpallet7.db.AppDatabase
import `fun`.gladkikh.fastpallet7.db.dao.CreatePalletUpdateDao
import `fun`.gladkikh.fastpallet7.db.intity.createpallet.getListTriggerCreatePallet
import `fun`.gladkikh.fastpallet7.model.usecase.testdata.AddTestDataUseCase
import `fun`.gladkikh.fastpallet7.repository.CreatePalletRepositoryUpdate
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object DependencyModule {

    val appModule = module {
        single { getDataBase(androidContext()) }

        //****************************************************************************************
        //DAO
        single { getCreatePalletUpdateDao(get()) }
        //****************************************************************************************
        //REPOSITORY
        single { CreatePalletRepositoryUpdate(get()) }
        //****************************************************************************************
        //USE CASE
        single { AddTestDataUseCase(get()) }

    }

    private fun getDataBase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "mydatabase")
            .addCallback(
                object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        getListTriggerCreatePallet().forEach {
                            db.execSQL(it)
                        }


                    }
                }

            )
            //.allowMainThreadQueries()
            .build()
    }


    private fun getCreatePalletUpdateDao(database: AppDatabase): CreatePalletUpdateDao {
        return database.getCreatePalletUpdateDao()
    }
}
