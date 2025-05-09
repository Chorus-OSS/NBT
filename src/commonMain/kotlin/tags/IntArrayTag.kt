package org.chorus_oss.nbt.tags

import org.chorus_oss.nbt.Tag
import org.chorus_oss.nbt.TagType

data class IntArrayTag(private val data: List<Int> = listOf()) : Tag, List<Int> by data {
    override val type: TagType = TagType.IntArray

    override fun toString(): String {
        return data.joinToString(
            prefix = "[I;",
            separator = ",",
            postfix = "]"
        )
    }
}
