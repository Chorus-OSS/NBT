package org.chorus_oss.nbt.tags

import org.chorus_oss.nbt.Tag
import org.chorus_oss.nbt.TagType

data class DoubleTag(val data: Double = 0.0) : Tag {
    override val type: TagType = TagType.Double

    override fun toString(): String {
        return "${data}d"
    }
}
