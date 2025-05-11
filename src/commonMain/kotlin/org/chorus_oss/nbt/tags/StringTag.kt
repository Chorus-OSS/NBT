package org.chorus_oss.nbt.tags

import kotlinx.io.*
import org.chorus_oss.nbt.*

data class StringTag(val data: String = "") : Tag {
    override val type: TagType = TagType.String

    override fun toString(): String = "\"$data\""

    companion object : TagCodec<StringTag> {
        override fun serialize(value: StringTag, stream: Sink, type: TagSerialization) {
            TagHelper.serializeString(value.data, stream, type)
        }

        override fun deserialize(stream: Source, type: TagSerialization): StringTag {
            return StringTag(
                TagHelper.deserializeString(stream, type)
            )
        }
    }
}
