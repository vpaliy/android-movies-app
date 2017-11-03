package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.PersonalType
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.interactor.params.Consumer
import com.popularmovies.vpaliy.domain.interactor.params.PersonalArg
import com.popularmovies.vpaliy.domain.interactor.params.SimpleConsumer
import com.popularmovies.vpaliy.domain.repository.PersonalRepository
import javax.inject.Singleton

@Singleton
class PersonalInteractor<T>
constructor(private val repository: PersonalRepository<T>, baseScheduler: BaseScheduler)
    :Interactor(baseScheduler){

    fun insert(consumer: SimpleConsumer,arg: PersonalArg<T>){
        repository.insert(arg.arg,arg.type)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(consumer.success,consumer.error)
    }

    fun remove(consumer: SimpleConsumer,arg: PersonalArg<T>){
        repository.delete(arg.arg,arg.type)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(consumer.success,consumer.error)
    }

    fun clear(consumer: SimpleConsumer,type:PersonalType){
        repository.clear(type)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(consumer.success,consumer.error)
    }

    fun fetch(consumer: Consumer<PersonalType,T>, type:PersonalType){
        repository.fetch(type).single
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(consumer.success,consumer.error)
    }
}
