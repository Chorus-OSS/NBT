package org.chorus_oss.nbt.tags

import kotlinx.io.*
import org.chorus_oss.nbt.*

data class ByteArrayTag(private val data: List<Byte> = listOf()) : Tag, List<Byte> by data {
    override val type: TagType = TagType.ByteArray

    override fun toString(): String = data.joinToString(
        prefix = "[B;",
        separator = ",",
        postfix = "]"
    )

    companion object : TagCodec<ByteArrayTag> {
        override fun serialize(value: ByteArrayTag, stream: Sink, type: TagSerialization) {
            TagHelper.serializeList(value, stream, type) { byte, st ->
                st.writeByte(byte)
            }
        }

        override fun deserialize(stream: Source, type: TagSerialization): ByteArrayTag {
            return ByteArrayTag(
                TagHelper.deserializeList(stream, type) {
                    it.readByte()
                }
            )
        }
    }
}
