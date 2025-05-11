package org.chorus_oss.nbt.tags

import kotlinx.io.*
import org.chorus_oss.nbt.Tag
import org.chorus_oss.nbt.TagCodec
import org.chorus_oss.nbt.TagSerialization
import org.chorus_oss.nbt.TagType

data class FloatTag(val data: Float = 0f) : Tag {
    override val type: TagType = TagType.Float

    override fun toString(): String = "${data}f"

    companion object : TagCodec<FloatTag> {
        override fun serialize(value: FloatTag, stream: Sink, type: TagSerialization) {
            when (type) {
                TagSerialization.BE -> stream.writeFloat(value.data)
                TagSerialization.LE,
                TagSerialization.NetLE -> stream.writeFloatLe(value.data)
            }
        }

        override fun deserialize(stream: Source, type: TagSerialization): FloatTag {
            return FloatTag(
                when (type) {
                    TagSerialization.BE -> stream.readFloat()
                    TagSerialization.LE,
                    TagSerialization.NetLE -> stream.readFloatLe()
                }
            )
        }
    }
}
