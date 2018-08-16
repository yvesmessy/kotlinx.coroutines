/*
 * Copyright 2016-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package kotlinx.coroutines

import kotlin.coroutines.*

internal expect fun <T> isolateCoroutine(
    completion: Continuation<T>,
    builder: (completion: Continuation<T>) -> Continuation<Unit>
): RunnableContinuation<Unit>

/**
 * @suppress **This is unstable API and it is subject to change.**
 */
public interface RunnableContinuation<in T> : Runnable, Continuation<T> {
    public fun resumeCancellableWith(result: SuccessOrFailure<T>)
    public fun resumeUndispatchedWith(result: SuccessOrFailure<T>)
}

public inline fun <T> RunnableContinuation<T>.resumeCancellable(value: T) =
    resumeCancellableWith(SuccessOrFailure.success(value))

public inline fun RunnableContinuation<*>.resumeCancellableWithException(exception: Throwable) =
    resumeCancellableWith(SuccessOrFailure.failure(exception))

public inline fun <T> RunnableContinuation<T>.resumeUndispatched(value: T) =
    resumeUndispatchedWith(SuccessOrFailure.success(value))

public inline fun RunnableContinuation<*>.resumeUndispatchedWithException(exception: Throwable) =
    resumeUndispatchedWith(SuccessOrFailure.failure(exception))

