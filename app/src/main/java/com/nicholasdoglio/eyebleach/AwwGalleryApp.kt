/*
 * MIT License
 *
 * Copyright (c) 2019 Nicholas Doglio
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.nicholasdoglio.eyebleach

import android.app.Application
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.nicholasdoglio.eyebleach.di.AppComponent
import com.nicholasdoglio.eyebleach.di.AppComponentProvider
import com.nicholasdoglio.eyebleach.di.DaggerAppComponent
import com.nicholasdoglio.eyebleach.worker.ClearDataWorker
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * @author Nicholas Doglio
 */
class AwwGalleryApp : Application(), AppComponentProvider {

    override val component: AppComponent by lazy {
        DaggerAppComponent
            .factory()
            .create(this)
    }

    override fun onCreate() {
        super.onCreate()
        WorkManager.initialize(
            this, Configuration.Builder()
                .setWorkerFactory(component.workerFactory)
                .build()
        )

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                CLEAR_DATABASE_WORK,
                ExistingPeriodicWorkPolicy.KEEP,
                PeriodicWorkRequestBuilder<ClearDataWorker>(INTERVAL_TWELVE_HOURS, TimeUnit.HOURS).build()
            )
    }

    companion object {
        private const val CLEAR_DATABASE_WORK = "CLEAR_DATABASE"
        private const val INTERVAL_TWELVE_HOURS: Long = 12
    }
}