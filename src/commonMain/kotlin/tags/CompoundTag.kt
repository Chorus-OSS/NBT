package org.chorus_oss.nbt.tags

import org.chorus_oss.nbt.Tag
import org.chorus_oss.nbt.TagType

data class CompoundTag(private val data: Map<String, Tag> = mapOf()) : Tag, Map<String, Tag> by data {
    override val type: TagType = TagType.Compound

    override fun toString(): String {
        return data.entries.joinToString(
            prefix = "{",
            separator = ",",
            postfix = "}"
        ) {
            "${StringTag(it.key)}:${it.value}"
        }
    }
}
