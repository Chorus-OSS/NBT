package org.chorus_oss.nbt.tags

import org.chorus_oss.nbt.Tag
import org.chorus_oss.nbt.TagType

data class ByteTag(val data: Byte = 0) : Tag {
    override val type: TagType
        get() = TagType.Byte

    override fun toString(): String {
        return "${data}b"
    }
}
