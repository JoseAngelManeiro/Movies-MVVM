package com.joseangelmaneiro.movies.domain.interactor

import com.joseangelmaneiro.movies.domain.executor.JobScheduler
import com.joseangelmaneiro.movies.domain.executor.UIScheduler
import io.reactivex.disposables.Disposable
import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver


abstract class UseCase<Observer, Params>(
    private val uiScheduler: UIScheduler,
    private val jobScheduler: JobScheduler) {

    abstract fun buildUseCaseObservable(params: Params): Single<Observer>

    fun execute(observer: DisposableSingleObserver<Observer>, params: Params): Disposable {
        val observable = this.buildUseCaseObservable(params)
            .observeOn(uiScheduler.getScheduler())
            .subscribeOn(jobScheduler.getScheduler())
        return observable.subscribeWith(observer)
    }

}