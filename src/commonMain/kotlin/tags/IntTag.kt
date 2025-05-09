package org.chorus_oss.nbt.tags

import org.chorus_oss.nbt.Tag
import org.chorus_oss.nbt.TagType

data class IntTag(val data: Int = 0) : Tag {
    override val type: TagType = TagType.Int

    override fun toString(): String {
        return "$data"
    }
}
