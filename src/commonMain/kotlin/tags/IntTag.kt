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

data class IntTag(val data: Int = 0) : Tag {
    override val type: TagType = TagType.Int

    override fun toString(): String = "${data}i"

    companion object : TagCodec<IntTag> {
        override fun serialize(value: IntTag, stream: Buffer, type: TagSerialization) {
            when (type) {
                TagSerialization.BE -> stream.writeInt(value.data)
                TagSerialization.LE -> stream.writeIntLe(value.data)
                TagSerialization.NetworkLE -> stream.writeIntVar(value.data)
            }
        }

        override fun deserialize(stream: Buffer, type: TagSerialization): IntTag {
            return IntTag(
                when (type) {
                    TagSerialization.BE -> stream.readInt()
                    TagSerialization.LE -> stream.readIntLe()
                    TagSerialization.NetworkLE -> stream.readIntVar()
                }
            )
        }

    }
}
