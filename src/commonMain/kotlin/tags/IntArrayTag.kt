package org.chorus_oss.nbt.tags

import kotlinx.io.Sink
import kotlinx.io.Source
import org.chorus_oss.nbt.*

data class IntArrayTag(private val data: List<Int> = listOf()) : Tag, List<Int> by data {
    override val type: TagType = TagType.IntArray

    override fun toString(): String = data.joinToString(
        prefix = "[I;",
        separator = ",",
        postfix = "]"
    )

    companion object : TagCodec<IntArrayTag> {
        override fun serialize(value: IntArrayTag, stream: Sink, type: TagSerialization) {
            TagHelper.serializeList(value, stream, type) { int, st ->
                TagHelper.serializeInt(int, st, type)
            }
        }

        override fun deserialize(stream: Source, type: TagSerialization): IntArrayTag {
            return IntArrayTag(
                TagHelper.deserializeList(stream, type) {
                    TagHelper.deserializeInt(it, type)
                }
            )
        }
    }
}
