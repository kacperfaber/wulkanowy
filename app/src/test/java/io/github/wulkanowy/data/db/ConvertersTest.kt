package io.github.wulkanowy.data.db

import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertersTest {

    @Test
    fun stringPairListToJson() {
        assertEquals(Converters().stringPairListToJson(listOf("aaa" to "bbb", "ccc" to "ddd")), "[{\"first\":\"aaa\",\"second\":\"bbb\"},{\"first\":\"ccc\",\"second\":\"ddd\"}]")
        assertEquals(Converters().stringPairListToJson(listOf()), "[]")
    }

    @Test
    fun jsonToStringPairList() {
        assertEquals(Converters().jsonToStringPairList("[{\"first\":\"aaa\",\"second\":\"bbb\"},{\"first\":\"ccc\",\"second\":\"ddd\"}]"), listOf("aaa" to "bbb", "ccc" to "ddd"))
        assertEquals(Converters().jsonToStringPairList("[]"), listOf<Pair<String, String>>())
    }

    @Test
    fun jsonToStringPairList_0210() {
        assertEquals(Converters().jsonToStringPairList("{\"aaa\":\"bbb\",\"ccc\":\"ddd\"}"), listOf("aaa" to "bbb", "ccc" to "ddd"))
        assertEquals(Converters().jsonToStringPairList("{}"), listOf<Pair<String, String>>())
    }
}