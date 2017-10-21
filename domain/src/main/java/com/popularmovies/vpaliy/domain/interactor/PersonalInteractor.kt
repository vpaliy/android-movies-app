package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.PersonalType
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.interactor.params.Consumer
import com.popularmovies.vpaliy.domain.interactor.params.PersonalArg
import com.popularmovies.vpaliy.domain.repository.PersonalRepository

class PersonalInteractor<T>
constructor(private val repository: PersonalRepository<T>, baseScheduler: BaseScheduler)
    :Interactor(baseScheduler){

    fun insert(success:()->Unit, error:(Throwable)->Unit,arg: PersonalArg<T>){
        repository.insert(arg.arg,arg.type)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(success,error)
    }

    fun remove(success:()->Unit,error:(Throwable)->Unit,arg: PersonalArg<T>){
        repository.delete(arg.arg,arg.type)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(success,error)
    }

    fun clear(success:()->Unit,error:(Throwable)->Unit,type:PersonalType){
        repository.clear(type)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(success,error)
    }

    fun fetch(consumer: Consumer<List<T>>, type:PersonalType){
        repository.fetch(type)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(consumer.success,consumer.error)
    }
}
