package org.chorus_oss.nbt.tags

import org.chorus_oss.nbt.Tag
import org.chorus_oss.nbt.TagType

data class StringTag(val data: String = "") : Tag {
    override val type: TagType = TagType.String

    override fun toString(): String {
        return "\"$data\""
    }
}
