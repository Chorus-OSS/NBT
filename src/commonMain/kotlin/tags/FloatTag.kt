package org.chorus_oss.nbt.tags

import org.chorus_oss.nbt.Tag
import org.chorus_oss.nbt.TagType

data class FloatTag(val data: Float = 0f) : Tag {
    override val type: TagType = TagType.Float

    override fun toString(): String {
        return "${data}f"
    }
}
