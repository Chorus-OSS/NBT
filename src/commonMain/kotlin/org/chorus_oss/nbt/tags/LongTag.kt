package org.chorus_oss.nbt.tags

import kotlinx.io.Sink
import kotlinx.io.Source
import org.chorus_oss.nbt.*

data class LongTag(val data: Long = 0L) : Tag {
    override val type: TagType = TagType.Long

    override fun toString(): String = "${data}l"

    companion object : TagCodec<LongTag> {
        override fun serialize(value: LongTag, stream: Sink, type: TagSerialization) {
            TagHelper.serializeLong(value.data, stream, type)
        }

        override fun deserialize(stream: Source, type: TagSerialization): LongTag {
            return LongTag(
                TagHelper.deserializeLong(stream, type)
            )
        }
    }
}
