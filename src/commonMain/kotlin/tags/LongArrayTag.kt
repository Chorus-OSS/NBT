package org.chorus_oss.nbt.tags

import kotlinx.io.*
import org.chorus_oss.nbt.*
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
            TagHelper.serializeList(value, stream, type) { long, st ->
                when (type) {
                    TagSerialization.BE -> st.writeLong(long)
                    TagSerialization.LE -> st.writeLongLe(long)
                    TagSerialization.NetworkLE -> st.writeLongVar(long)
                }
            }
        }

        override fun deserialize(stream: Buffer, type: TagSerialization): LongArrayTag {
            return LongArrayTag(
                TagHelper.deserializeList(stream, type) {
                    when (type) {
                        TagSerialization.BE -> it.readLong()
                        TagSerialization.LE -> it.readLongLe()
                        TagSerialization.NetworkLE -> it.readLongVar()
                    }
                }
            )
        }
    }
}
