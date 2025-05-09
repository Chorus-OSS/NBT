package org.chorus_oss.nbt.tags

import kotlinx.io.*
import org.chorus_oss.nbt.Tag
import org.chorus_oss.nbt.TagCodec
import org.chorus_oss.nbt.TagSerialization
import org.chorus_oss.nbt.TagType
import org.chorus_oss.varlen.types.readIntVar
import org.chorus_oss.varlen.types.readLongVar
import org.chorus_oss.varlen.types.writeIntVar
import org.chorus_oss.varlen.types.writeLongVar

data class LongArrayTag(private val data: List<Long> = listOf()) : Tag, List<Long> by data {
    override val type: TagType = TagType.LongArray

    override fun toString(): String = data.joinToString(
        prefix = "[L;",
        separator = ",",
        postfix = "]"
    )

    companion object : TagCodec<LongArrayTag> {
        override fun serialize(value: LongArrayTag, stream: Buffer, type: TagSerialization) {
            when (type) {
                TagSerialization.BE -> stream.writeInt(value.size)
                TagSerialization.LE -> stream.writeIntLe(value.size)
                TagSerialization.NetworkLE -> stream.writeIntVar(value.size)
            }

            for (long in value) {
                when (type) {
                    TagSerialization.BE -> stream.writeLong(long)
                    TagSerialization.LE -> stream.writeLongLe(long)
                    TagSerialization.NetworkLE -> stream.writeLongVar(long)
                }
            }
        }

        override fun deserialize(stream: Buffer, type: TagSerialization): LongArrayTag {
            val len = when (type) {
                TagSerialization.BE -> stream.readInt()
                TagSerialization.LE -> stream.readIntLe()
                TagSerialization.NetworkLE -> stream.readIntVar()
            }

            return LongArrayTag(
                List(len) {
                    when (type) {
                        TagSerialization.BE -> stream.readLong()
                        TagSerialization.LE -> stream.readLongLe()
                        TagSerialization.NetworkLE -> stream.readLongVar()
                    }
                }
            )
        }
    }
}
