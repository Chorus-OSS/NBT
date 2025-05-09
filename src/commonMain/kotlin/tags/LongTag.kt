package org.chorus_oss.nbt.tags

import kotlinx.io.Buffer
import kotlinx.io.readLongLe
import kotlinx.io.writeLongLe
import org.chorus_oss.nbt.*
import org.chorus_oss.varlen.types.readLongVar
import org.chorus_oss.varlen.types.writeLongVar

data class LongTag(val data: Long = 0L) : Tag {
    override val type: TagType = TagType.Long

    override fun toString(): String = "${data}l"

    companion object : TagCodec<LongTag> {
        override fun serialize(value: LongTag, stream: Buffer, type: TagSerialization) {
            TagHelper.serializeLong(value.data, stream, type)
        }

        override fun deserialize(stream: Buffer, type: TagSerialization): LongTag {
            return LongTag(
                TagHelper.deserializeLong(stream, type)
            )
        }
    }
}
