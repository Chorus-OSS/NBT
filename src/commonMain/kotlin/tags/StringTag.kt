package org.chorus_oss.nbt.tags

import kotlinx.io.Buffer
import kotlinx.io.readShortLe
import kotlinx.io.readTo
import kotlinx.io.writeShortLe
import org.chorus_oss.nbt.*
import org.chorus_oss.varlen.types.readUIntVar
import org.chorus_oss.varlen.types.writeUIntVar

data class StringTag(val data: String = "") : Tag {
    override val type: TagType = TagType.String

    override fun toString(): String = "\"$data\""

    companion object : TagCodec<StringTag> {
        override fun serialize(value: StringTag, stream: Buffer, type: TagSerialization) {
            TagHelper.serializeString(value.data, stream, type)
        }

        override fun deserialize(stream: Buffer, type: TagSerialization): StringTag {
            return StringTag(
                TagHelper.deserializeString(stream, type)
            )
        }
    }
}
