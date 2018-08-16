/*
 * Copyright 2016-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
 */

package kotlinx.coroutines.internal

internal actual inline class Lock(val owner: Any)

internal actual fun lockFor(owner: Any): Lock = Lock(owner)

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun Lock.dispose() {}

internal actual inline fun <T> Lock.withLock(block: () -> T): T =
    synchronized(owner, block)