/*
 * Copyright 2016-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
 */

package kotlinx.coroutines.internal

public actual inline class IsolatedRef<T : Any>(val value: T) {
    actual inline fun dispose() {}
}

public actual inline fun <T : Any> isolate(crossinline init: () -> T) =
    IsolatedRef(init())

public actual inline fun <T : Any, R> IsolatedRef<T>.withValue(block: T.() -> R): R =
    block(value)