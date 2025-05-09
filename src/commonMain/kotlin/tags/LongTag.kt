package org.chorus_oss.nbt.tags

import org.chorus_oss.nbt.Tag
import org.chorus_oss.nbt.TagType

data class LongTag(val data: Long = 0L) : Tag {
    override val type: TagType = TagType.Long

    override fun toString(): String {
        return "${data}L"
    }
}
