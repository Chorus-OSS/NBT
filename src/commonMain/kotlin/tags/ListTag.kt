package org.chorus_oss.nbt.tags

import kotlinx.io.Buffer
import kotlinx.io.readIntLe
import kotlinx.io.writeIntLe
import org.chorus_oss.nbt.Tag
import org.chorus_oss.nbt.TagCodec
import org.chorus_oss.nbt.TagSerialization
import org.chorus_oss.nbt.TagType
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

            when (type) {
                TagSerialization.BE -> stream.writeInt(value.size)
                TagSerialization.LE -> stream.writeIntLe(value.size)
                TagSerialization.NetworkLE -> stream.writeIntVar(value.size)
            }

            for (tag in value) {
                Tag.serialize(tag, stream, type)
            }
        }

        override fun deserialize(stream: Buffer, type: TagSerialization): ListTag<*> {
            val entryType = TagType.fromByte(stream.readByte())

            val len = when (type) {
                TagSerialization.BE -> stream.readInt()
                TagSerialization.LE -> stream.readIntLe()
                TagSerialization.NetworkLE -> stream.readIntVar()
            }

            return ListTag(
                List(len) {
                    Tag.deserialize(entryType, stream, type)
                }
            )
        }
    }
}
