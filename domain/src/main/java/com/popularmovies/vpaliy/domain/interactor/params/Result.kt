package com.popularmovies.vpaliy.domain.interactor.params

import io.reactivex.Single

data class Result<out Request,Response>(val request:Request, val single:Single<Response>)

abstract class Handler<Request,Response>{
  abstract fun buildResult(request:Request):Result<Request,Response>

  private fun execute(success:(Response)->Unit, error:(Throwable)->Unit, request:Request){
    val result=buildResult(request)
    result.single.subscribe(success,error)
    execute(request){
      onSuccess={

      }
      onError=error
    }
  }

  private fun execute(success:(Request, Response)->Unit, error:(Throwable)->Unit, request:Request){
    val onSuccess={ response:Response->
      success.invoke(request,response)
    }
    buildResult(request).single
            .subscribe(onSuccess,error)
  }


  private fun execute(request:Request, client:Client<Request,Response>.()->Unit){
    Client<Request,Response>().apply(client)
  }
}


class Client<Request, Response>{
  lateinit var onSuccess:(Response)->Unit
  lateinit var onError:(Throwable)->Unit
}