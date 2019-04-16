package kotlinx.coroutines.reactor

import reactor.util.context.Context
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

class ReactorContext(val context: Context) : AbstractCoroutineContextElement(ReactorContext) {
    companion object Key : CoroutineContext.Key<ReactorContext>
}

fun Context.asCoroutineContext(): CoroutineContext = ReactorContext(this)