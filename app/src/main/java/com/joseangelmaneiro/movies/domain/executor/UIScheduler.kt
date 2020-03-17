package com.joseangelmaneiro.movies.domain.executor

import io.reactivex.Scheduler

/**
 * Thread abstraction created to change the execution context from
 * job thread(data) to UI thread(main).
 */
interface UIScheduler {
    fun getScheduler(): Scheduler
}