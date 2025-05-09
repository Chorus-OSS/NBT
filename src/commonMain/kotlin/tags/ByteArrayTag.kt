package org.chorus_oss.nbt.tags

import kotlinx.io.Buffer
import kotlinx.io.readIntLe
import kotlinx.io.readTo
import kotlinx.io.writeIntLe
import org.chorus_oss.nbt.*
import org.chorus_oss.varlen.types.readIntVar
import org.chorus_oss.varlen.types.writeIntVar

data class ByteArrayTag(private val data: List<Byte> = listOf()) : Tag, List<Byte> by data {
    override val type: TagType = TagType.ByteArray

    override fun toString(): String = data.joinToString(
        prefix = "[B;",
        separator = ",",
        postfix = "]"
    )

    companion object : TagCodec<ByteArrayTag> {
        override fun serialize(value: ByteArrayTag, stream: Buffer, type: TagSerialization) {
            TagHelper.serializeList(value, stream, type) { byte, st ->
                st.writeByte(byte)
            }
        }

        override fun deserialize(stream: Buffer, type: TagSerialization): ByteArrayTag {
            return ByteArrayTag(
                TagHelper.deserializeList(stream, type) {
                    it.readByte()
                }
            )
        }
    }
}
