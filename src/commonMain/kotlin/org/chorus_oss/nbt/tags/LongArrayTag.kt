package org.chorus_oss.nbt.tags

import kotlinx.io.*
import org.chorus_oss.nbt.*

data class LongArrayTag(private val data: List<Long> = listOf()) : Tag, List<Long> by data {
    override val type: TagType = TagType.LongArray

    override fun toString(): String = data.joinToString(
        prefix = "[L;",
        separator = ",",
        postfix = "]"
    )

    companion object : TagCodec<LongArrayTag> {
        override fun serialize(value: LongArrayTag, stream: Sink, type: TagSerialization) {
            TagHelper.serializeList(value, stream, type) { long, st ->
                TagHelper.serializeLong(long, st, type)
            }
        }

        override fun deserialize(stream: Source, type: TagSerialization): LongArrayTag {
            return LongArrayTag(
                TagHelper.deserializeList(stream, type) {
                    TagHelper.deserializeLong(it, type)
                }
            )
        }
    }
}
