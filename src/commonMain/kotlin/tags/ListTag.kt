package org.chorus_oss.nbt.tags

import kotlinx.io.Buffer
import kotlinx.io.readIntLe
import kotlinx.io.writeIntLe
import org.chorus_oss.nbt.*
import org.chorus_oss.varlen.types.readIntVar
import org.chorus_oss.varlen.types.writeIntVar

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
        override fun serialize(value: ListTag<*>, stream: Buffer, type: TagSerialization) {
            stream.writeByte(TagType.toByte(value.entryType))

            TagHelper.serializeList(value, stream, type) { tag, st ->
                Tag.serialize(tag, st, type)
            }
        }

        override fun deserialize(stream: Buffer, type: TagSerialization): ListTag<*> {
            val entryType = TagType.fromByte(stream.readByte())

            return ListTag(
                TagHelper.deserializeList(stream, type) {
                    Tag.deserialize(entryType, it, type)
                }
            )
        }
    }
}
