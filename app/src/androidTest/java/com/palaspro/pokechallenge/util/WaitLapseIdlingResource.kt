package com.palaspro.pokechallenge.util

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.IdlingResource.ResourceCallback

class WaitLapseIdlingResource(private val timeLapse: Long) : IdlingResource {

    private val timeStart = System.currentTimeMillis()
    private var resourceCallback: ResourceCallback? = null
    private var isIdle = false

    override fun getName(): String = WaitLapseIdlingResource::class.java.name + timeStart

    override fun isIdleNow(): Boolean {
        if (isIdle) return true

        isIdle = (System.currentTimeMillis() - timeStart) > timeLapse
        if (isIdle) {
            resourceCallback?.onTransitionToIdle()
        }
        return isIdle
    }

    override fun registerIdleTransitionCallback(callback: ResourceCallback?) {
        this.resourceCallback = callback
    }
}