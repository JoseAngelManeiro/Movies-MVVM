package com.joseangelmaneiro.movies.platform.executor

import com.joseangelmaneiro.movies.domain.executor.UIScheduler
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * MainThread (UI Thread) implementation based on a Scheduler,
 * which will execute actions on the Android UI thread.
 */
class UIThread: UIScheduler {
    override fun getScheduler(): Scheduler = AndroidSchedulers.mainThread()
}