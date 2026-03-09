package com.angelodev.helpapp.di

import android.content.Context
import com.angelodev.helpapp.data.local.dao.CircuitDao
import com.angelodev.helpapp.data.local.database.HelpDatabase
import com.angelodev.helpapp.data.repository.CircuitRepository
import com.angelodev.helpapp.util.PrefsManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): HelpDatabase =
        HelpDatabase.getInstance(context)

    @Provides
    fun provideCircuitDao(db: HelpDatabase): CircuitDao = db.circuitDao()

    @Provides
    @Singleton
    fun provideCircuitRepository(dao: CircuitDao): CircuitRepository =
        CircuitRepository(dao)

    @Provides
    @Singleton
    fun providePrefsManager(@ApplicationContext context: Context): PrefsManager =
        PrefsManager(context)
}
