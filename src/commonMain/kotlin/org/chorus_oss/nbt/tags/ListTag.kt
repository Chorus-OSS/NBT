package org.chorus_oss.nbt.tags

import kotlinx.io.Sink
import kotlinx.io.Source
import org.chorus_oss.nbt.*

data class ListTag<out T : Tag>(private val list: List<T> = listOf()) : Tag, List<T> by list {
    override val type: TagType = TagType.List

    val entryType: TagType
        get() = this.firstOrNull()?.type ?: TagType.End

    override fun toString(): String = list.joinToString(
        prefix = "[",
        separator = ",",
        postfix = "]"
    )

    companion object : TagCodec<ListTag<*>> {
        override fun serialize(value: ListTag<*>, stream: Sink, type: TagSerialization) {
            stream.writeByte(value.entryType.id)

            TagHelper.serializeList(value, stream, type) { tag, st ->
                Tag.serialize(tag, st, type)
            }
        }

        override fun deserialize(stream: Source, type: TagSerialization): ListTag<*> {
            val entryType = TagType.from(stream.readByte())

            return ListTag(
                TagHelper.deserializeList(stream, type) {
                    Tag.deserialize(entryType, it, type)
                }
            )
        }
    }
}
