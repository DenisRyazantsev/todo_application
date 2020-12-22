package com.example.todoapplication

import android.app.Application
import com.example.todoapplication.di.databaseModule
import com.example.todoapplication.di.repositoryModule
import com.example.todoapplication.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

// TODO Плохое название для класса.
//  Ты просто взял название нормального класса, например Transfer и сократил его до Tran.
//  Банальное MyApp дает больше информации о классе, указывая, что это во первых "моё приложение",
//  а во вторых по названию понятно, что это не библиотечный класс, потому что в библиотечных классов никто не пишет префикс My,
//  в отличии от абстрактного App.
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        // TODO Мне интересно, а ты сможешь объяснить зачем было нужно использовать Koin?
        //  Зачем вообще на пару сотен кода использовать абстракции для управления зависимостями?
        //  Если для учебных целей, то для них нужно выбирать проект где это "нужно" иначе это просто бессмыслено.
        startKoin {
            androidContext(this@App)
            androidLogger(Level.DEBUG)
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule,
                    databaseModule
                )
            )
        }
    }
}