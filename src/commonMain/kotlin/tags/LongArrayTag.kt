package org.chorus_oss.nbt.tags

import org.chorus_oss.nbt.Tag
import org.chorus_oss.nbt.TagType

data class LongArrayTag(private val data: List<Long> = listOf()) : Tag, List<Long> by data {
    override val type: TagType = TagType.LongArray

    override fun toString(): String {
        return data.joinToString(
            prefix = "[L;",
            separator = ",",
            postfix = "]"
        )
    }
}
