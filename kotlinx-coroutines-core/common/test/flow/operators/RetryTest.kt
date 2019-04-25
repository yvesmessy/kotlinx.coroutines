/*
 * Copyright 2016-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
 */

package kotlinx.coroutines.flow

import kotlinx.coroutines.*
import kotlin.test.*

class RetryTest : TestBase() {
    @Test
    fun testRetry() = runTest {
        var counter = 0
        val flow = flow {
            emit(1)
            if (++counter < 4) throw TestException()
        }

        assertEquals(4, flow.retry(4).sum())
        counter = 0
        assertFailsWith<TestException>(flow)
        counter = 0
        assertFailsWith<TestException>(flow.retry(2))
    }

    @Test
    fun testRetryStatefulPredicate() = runTest {
        var counter = 0
        val flow = flow {
            emit(1);
            if (++counter == 1) throw TestException()
        }

        assertEquals(2, flow.retry(1) { it is TestException }.sum())
        counter = 0
        assertFailsWith<TestException>(flow.retry(1) { it !is TestException })
    }

    @Test
    fun testRetryExceptionFromDownstream() = runTest {
        var executed = 0
        val flow = flow {
            emit(1)
        }.retry(42).onEach {
            ++executed
            throw TestException()
        }

        assertFailsWith<TestException>(flow)
        assertEquals(1, executed)
    }

    @Test
    fun testCancellation() = runTest {
        testCancellation({ retry() })
    }

    @Test
    fun testCancellationWithPredicate() = runTest {
        testCancellation({ retry { it is TestException }})
    }

    @Test
    fun testWithTimeoutCancellation() = runTest {

        val flow = flow {
            expect(2)
            withTimeout(1L) {
                Long.MAX_VALUE
            }

            emit(1)
        }

        expect(1)
        flow.retry(1).collect {  }
        finish(3)
    }


    private suspend fun testCancellation(transformer: Flow<Int>.() -> Flow<Int>) = coroutineScope {
        val flow = flow {
            expect(3)
            while (true) {
                emit(1)
                yield()
            }
        }.transformer()

        val collector = launch {
            expect(2)
            flow.collect {
                // Do nothing
            }
            expectUnreached()
        }
        expect(1)
        yield()
        collector.cancelAndJoin()
        finish(4)
    }
}
