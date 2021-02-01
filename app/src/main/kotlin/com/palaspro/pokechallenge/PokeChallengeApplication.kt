package com.palaspro.pokechallenge

import android.app.Application
import com.palaspro.pokechallenge.di.modulesPokeChallenge
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PokeChallengeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@PokeChallengeApplication)
            modules(modulesPokeChallenge)
        }
    }
}