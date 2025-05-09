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

data class CompoundTag(private val data: Map<String, Tag> = mapOf()) : Tag, Map<String, Tag> by data {
    override val type: TagType = TagType.Compound

    override fun toString(): String = data.entries.joinToString(
        prefix = "{", separator = ",", postfix = "}"
    ) { "${StringTag(it.key)}:${it.value}" }

    companion object : TagCodec<CompoundTag> {
        override fun serialize(value: CompoundTag, stream: Buffer, type: TagSerialization) {
            for ((k, v) in value.entries) {
                stream.writeByte(TagType.toByte(v.type))

                when (type) {
                    TagSerialization.BE -> stream.writeShort(k.length.toShort())
                    TagSerialization.LE -> stream.writeShortLe(k.length.toShort())
                    TagSerialization.NetworkLE -> stream.writeUIntVar(k.length.toUInt())
                }
                stream.write(k.encodeToByteArray())

                Tag.serialize(v, stream, type)
            }
            stream.writeByte(TagType.toByte(TagType.End))
        }

        override fun deserialize(stream: Buffer, type: TagSerialization): CompoundTag {
            val map = mutableMapOf<String, Tag>()

            var tagType: TagType
            while (TagType.fromByte(stream.readByte()).also {
                    tagType = it
                } != TagType.End) {
                val bytes = ByteArray(
                    when (type) {
                        TagSerialization.BE -> stream.readShort().toInt()
                        TagSerialization.LE -> stream.readShortLe().toInt()
                        TagSerialization.NetworkLE -> stream.readUIntVar().toInt()
                    }
                )
                stream.readTo(bytes)
                val k = bytes.decodeToString()

                map[k] = Tag.deserialize(tagType, stream, type)
            }

            return CompoundTag(map)
        }
    }
}
