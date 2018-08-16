/*
 * Copyright 2016-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
 */

package kotlinx.coroutines.internal

import platform.posix.*
import kotlinx.cinterop.*

internal actual inline class Lock(val mutex: pthread_mutex_t)

internal actual fun lockFor(owner: Any): Lock {
    val mutex = nativeHeap.alloc<pthread_mutex_t>()
    pthread_mutex_init(mutex.ptr, null)
    return Lock(mutex)
}

internal actual fun Lock.dispose() {
    nativeHeap.free(mutex)
}

internal actual inline fun <T> Lock.withLock(block: () -> T): T {
    pthread_mutex_lock(mutex.ptr)
    try {
        return block()
    } finally {
        pthread_mutex_unlock(mutex.ptr)
    }
}
