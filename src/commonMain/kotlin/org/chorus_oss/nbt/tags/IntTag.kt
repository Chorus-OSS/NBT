package org.chorus_oss.nbt.tags

import kotlinx.io.Sink
import kotlinx.io.Source
import org.chorus_oss.nbt.*

data class IntTag(val data: Int = 0) : Tag {
    override val type: TagType = TagType.Int

    override fun toString(): String = "${data}i"

    companion object : TagCodec<IntTag> {
        override fun serialize(value: IntTag, stream: Sink, type: TagSerialization) {
            TagHelper.serializeInt(value.data, stream, type)
        }

        override fun deserialize(stream: Source, type: TagSerialization): IntTag {
            return IntTag(
                TagHelper.deserializeInt(stream, type)
            )
        }
    }
}
