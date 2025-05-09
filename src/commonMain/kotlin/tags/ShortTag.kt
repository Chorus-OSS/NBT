package org.chorus_oss.nbt.tags

import org.chorus_oss.nbt.Tag
import org.chorus_oss.nbt.TagType

data class ShortTag(val data: Short = 0) : Tag {
    override val type: TagType = TagType.Short

    override fun toString(): String {
        return "${data}s"
    }
}
