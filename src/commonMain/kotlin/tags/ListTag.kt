package org.chorus_oss.nbt.tags

import org.chorus_oss.nbt.Tag
import org.chorus_oss.nbt.TagType

data class ListTag<out T : Tag>(private val list: List<T> = listOf()) : Tag, List<T> by list {
    override val type: TagType = TagType.List

    override fun toString(): String {
        return list.joinToString(
            prefix = "[",
            separator = ",",
            postfix = "]"
        )
    }
}
