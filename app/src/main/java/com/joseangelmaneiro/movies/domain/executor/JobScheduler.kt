package com.joseangelmaneiro.movies.domain.executor

import io.reactivex.Scheduler

/**
 * Thread abstraction created to change the execution context from
 * UI thread(main) to job thread(data).
 */
interface JobScheduler {
    fun getScheduler(): Scheduler
}