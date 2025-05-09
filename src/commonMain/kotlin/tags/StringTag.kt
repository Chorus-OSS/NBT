package org.chorus_oss.nbt.tags

import kotlinx.io.Buffer
import kotlinx.io.readShortLe
import kotlinx.io.readTo
import kotlinx.io.writeShortLe
import org.chorus_oss.nbt.Tag
import org.chorus_oss.nbt.TagCodec
import org.chorus_oss.nbt.TagSerialization
import org.chorus_oss.nbt.TagType
import org.chorus_oss.varlen.types.readUIntVar
import org.chorus_oss.varlen.types.writeUIntVar

data class StringTag(val data: String = "") : Tag {
    override val type: TagType = TagType.String

    override fun toString(): String = "\"$data\""

    companion object : TagCodec<StringTag> {
        override fun serialize(value: StringTag, stream: Buffer, type: TagSerialization) {
            when (type) {
                TagSerialization.BE -> stream.writeShort(value.data.length.toShort())
                TagSerialization.LE -> stream.writeShortLe(value.data.length.toShort())
                TagSerialization.NetworkLE -> stream.writeUIntVar(value.data.length.toUInt())
            }
            stream.write(value.data.encodeToByteArray())
        }

        override fun deserialize(stream: Buffer, type: TagSerialization): StringTag {
            val len = when (type) {
                TagSerialization.BE -> stream.readShort().toInt()
                TagSerialization.LE -> stream.readShortLe().toInt()
                TagSerialization.NetworkLE -> stream.readUIntVar().toInt()
            }

            val bytes = ByteArray(len)

            stream.readTo(bytes)

            return StringTag(bytes.decodeToString())
        }
    }
}
