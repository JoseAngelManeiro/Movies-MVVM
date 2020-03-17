package com.joseangelmaneiro.movies.data.executor

import com.joseangelmaneiro.movies.domain.executor.JobScheduler
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 * JobScheduler implementation based on a Scheduler,
 * which will execute tasks in "a pool of single-threaded instances".
 */
class JobThread: JobScheduler {
    override fun getScheduler(): Scheduler = Schedulers.io()
}