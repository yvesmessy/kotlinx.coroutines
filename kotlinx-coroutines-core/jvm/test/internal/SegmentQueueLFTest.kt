package kotlinx.coroutines.internal

import com.devexperts.dxlab.lincheck.LinChecker
import com.devexperts.dxlab.lincheck.annotations.Operation
import com.devexperts.dxlab.lincheck.annotations.Param
import com.devexperts.dxlab.lincheck.paramgen.IntGen
import com.devexperts.dxlab.lincheck.strategy.stress.StressCTest
import org.junit.Test

@StressCTest
class SegmentQueueLFTest {
    private companion object {
        var createFirstSegmentLazily: Boolean = false
    }

    private val q = SegmentBasedQueue<Int>(createFirstSegmentLazily)

    @Operation
    fun add(@Param(gen = IntGen::class) x: Int) {
        q.enqueue(x)
    }

    @Operation
    fun poll(): Int? = q.dequeue()

    @Test
    fun test() {
        createFirstSegmentLazily = false
        LinChecker.check(SegmentQueueLFTest::class.java)
    }

    @Test
    fun testWithLazyFirstSegment() {
        createFirstSegmentLazily = true
        LinChecker.check(SegmentQueueLFTest::class.java)
    }
}