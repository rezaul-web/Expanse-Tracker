package com.example.expansetracker.di

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.expansetracker.data.model.ExpanseDatabase
import com.example.expansetracker.util.MIGRATION_1_2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "expanse_preferences")

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    @Singleton
    fun provideDatabase(app: Application): ExpanseDatabase {
        return Room.databaseBuilder(app, ExpanseDatabase::class.java, "expanse_database")
            .addMigrations(
                MIGRATION_1_2
            ).build()

    }

    @Provides
    @Singleton
    fun provideDao(db: ExpanseDatabase) = db.getExpanseDao()

    @Provides
    @Singleton
    fun provideIncomeDao(db: ExpanseDatabase)=db.getIncomeDao()


    @Provides
    @Singleton
    fun provideDataStorePref(@ApplicationContext context: Context) = context.dataStore
}