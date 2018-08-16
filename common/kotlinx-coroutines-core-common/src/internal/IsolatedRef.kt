/*
 * Copyright 2016-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
 */

package kotlinx.coroutines.internal

@Suppress("ABSENCE_OF_PRIMARY_CONSTRUCTOR_FOR_INLINE_CLASS")
public expect inline class IsolatedRef<T : Any> {
    fun dispose()
}

public expect inline fun <T : Any> isolate(crossinline init: () -> T) : IsolatedRef<T>

public expect inline fun <T : Any, R> IsolatedRef<T>.withValue(block: T.() -> R): R