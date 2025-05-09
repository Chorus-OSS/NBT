package org.chorus_oss.nbt.tags

import kotlinx.io.Buffer
import kotlinx.io.readLongLe
import kotlinx.io.writeLongLe
import org.chorus_oss.nbt.Tag
import org.chorus_oss.nbt.TagCodec
import org.chorus_oss.nbt.TagSerialization
import org.chorus_oss.nbt.TagType
import org.chorus_oss.varlen.types.readLongVar
import org.chorus_oss.varlen.types.writeLongVar

data class LongTag(val data: Long = 0L) : Tag {
    override val type: TagType = TagType.Long

    override fun toString(): String = "${data}l"

    companion object : TagCodec<LongTag> {
        override fun serialize(value: LongTag, stream: Buffer, type: TagSerialization) {
            when (type) {
                TagSerialization.BE -> stream.writeLong(value.data)
                TagSerialization.LE -> stream.writeLongLe(value.data)
                TagSerialization.NetworkLE -> stream.writeLongVar(value.data)
            }
        }

        override fun deserialize(stream: Buffer, type: TagSerialization): LongTag {
            return LongTag(
                when (type) {
                    TagSerialization.BE -> stream.readLong()
                    TagSerialization.LE -> stream.readLongLe()
                    TagSerialization.NetworkLE -> stream.readLongVar()
                }
            )
        }
    }
}
