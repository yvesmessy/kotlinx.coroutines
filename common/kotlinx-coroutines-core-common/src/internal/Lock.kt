/*
 * Copyright 2016-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
 */

package kotlinx.coroutines.internal

@Suppress("ABSENCE_OF_PRIMARY_CONSTRUCTOR_FOR_INLINE_CLASS")
internal expect inline class Lock

internal expect fun lockFor(owner: Any): Lock

internal expect fun Lock.dispose()

internal expect inline fun <T> Lock.withLock(block: () -> T): T