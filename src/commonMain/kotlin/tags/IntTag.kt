package org.chorus_oss.nbt.tags

import kotlinx.io.Buffer
import kotlinx.io.readIntLe
import kotlinx.io.writeIntLe
import org.chorus_oss.nbt.*
import org.chorus_oss.varlen.types.readIntVar
import org.chorus_oss.varlen.types.writeIntVar

data class IntTag(val data: Int = 0) : Tag {
    override val type: TagType = TagType.Int

    override fun toString(): String = "${data}i"

    companion object : TagCodec<IntTag> {
        override fun serialize(value: IntTag, stream: Buffer, type: TagSerialization) {
            TagHelper.serializeInt(value.data, stream, type)
        }

        override fun deserialize(stream: Buffer, type: TagSerialization): IntTag {
            return IntTag(
                TagHelper.deserializeInt(stream, type)
            )
        }
    }
}
