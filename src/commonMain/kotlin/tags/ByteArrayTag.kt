package org.chorus_oss.nbt.tags

import kotlinx.io.Buffer
import kotlinx.io.readIntLe
import kotlinx.io.readTo
import kotlinx.io.writeIntLe
import org.chorus_oss.nbt.Tag
import org.chorus_oss.nbt.TagCodec
import org.chorus_oss.nbt.TagSerialization
import org.chorus_oss.nbt.TagType
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
            when (type) {
                TagSerialization.BE -> stream.writeInt(value.size)
                TagSerialization.LE -> stream.writeIntLe(value.size)
                TagSerialization.NetworkLE -> stream.writeIntVar(value.size)
            }

            stream.write(value.data.toByteArray())
        }

        override fun deserialize(stream: Buffer, type: TagSerialization): ByteArrayTag {
            val len = when (type) {
                TagSerialization.BE -> stream.readInt()
                TagSerialization.LE -> stream.readIntLe()
                TagSerialization.NetworkLE -> stream.readIntVar()
            }

            val bytes = ByteArray(len)
            stream.readTo(bytes)

            return ByteArrayTag(bytes.toList())
        }
    }
}
