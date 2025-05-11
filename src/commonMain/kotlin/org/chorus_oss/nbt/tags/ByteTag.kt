package org.chorus_oss.nbt.tags

import kotlinx.io.Sink
import kotlinx.io.Source
import org.chorus_oss.nbt.Tag
import org.chorus_oss.nbt.TagCodec
import org.chorus_oss.nbt.TagSerialization
import org.chorus_oss.nbt.TagType

data class ByteTag(val data: Byte = 0) : Tag {
    override val type: TagType = TagType.Byte

    override fun toString(): String = "${data}b"

    companion object : TagCodec<ByteTag> {
        override fun serialize(value: ByteTag, stream: Sink, type: TagSerialization) {
            stream.writeByte(value.data)
        }

        override fun deserialize(stream: Source, type: TagSerialization): ByteTag {
            return ByteTag(stream.readByte())
        }
    }
}
