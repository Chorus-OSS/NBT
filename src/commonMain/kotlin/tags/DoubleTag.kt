package org.chorus_oss.nbt.tags

import kotlinx.io.*
import org.chorus_oss.nbt.Tag
import org.chorus_oss.nbt.TagCodec
import org.chorus_oss.nbt.TagSerialization
import org.chorus_oss.nbt.TagType

data class DoubleTag(val data: Double = 0.0) : Tag {
    override val type: TagType = TagType.Double

    override fun toString(): String = "${data}d"

    companion object : TagCodec<DoubleTag> {
        override fun serialize(value: DoubleTag, stream: Buffer, type: TagSerialization) {
            when (type) {
                TagSerialization.BE -> stream.writeDouble(value.data)
                TagSerialization.LE,
                TagSerialization.NetLE -> stream.writeDoubleLe(value.data)
            }
        }

        override fun deserialize(stream: Buffer, type: TagSerialization): DoubleTag {
            return DoubleTag(
                when (type) {
                    TagSerialization.BE -> stream.readDouble()
                    TagSerialization.LE,
                    TagSerialization.NetLE -> stream.readDoubleLe()
                }
            )
        }
    }
}
