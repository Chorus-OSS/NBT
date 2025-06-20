package org.chorus_oss.nbt.tags

import kotlinx.io.*
import org.chorus_oss.nbt.*

data class CompoundTag(private val data: Map<String, Tag> = mapOf()) : Tag, Map<String, Tag> by data {
    override val type: TagType = TagType.Compound

    override fun toString(): String = data.entries.joinToString(
        prefix = "{", separator = ",", postfix = "}"
    ) { "${StringTag(it.key)}:${it.value}" }

    companion object : TagCodec<CompoundTag> {
        override fun serialize(value: CompoundTag, stream: Sink, type: TagSerialization) {
            for ((k, v) in value.entries) {
                stream.writeByte(v.type.id)
                TagHelper.serializeString(k, stream, type)
                Tag.serialize(v, stream, type)
            }
            stream.writeByte(TagType.End.id)
        }

        override fun deserialize(stream: Source, type: TagSerialization): CompoundTag {
            val map = mutableMapOf<String, Tag>()

            var tagType: TagType
            while (TagType.from(stream.readByte()).also {
                    tagType = it
                } != TagType.End) {
                val k = TagHelper.deserializeString(stream, type)
                map[k] = Tag.deserialize(tagType, stream, type)
            }

            return CompoundTag(map)
        }
    }
}
