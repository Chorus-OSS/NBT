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

data class IntArrayTag(private val data: List<Int> = listOf()) : Tag, List<Int> by data {
    override val type: TagType = TagType.IntArray

    override fun toString(): String = data.joinToString(
        prefix = "[I;",
        separator = ",",
        postfix = "]"
    )

    companion object : TagCodec<IntArrayTag> {
        override fun serialize(value: IntArrayTag, stream: Buffer, type: TagSerialization) {
            when (type) {
                TagSerialization.BE -> stream.writeInt(value.size)
                TagSerialization.LE -> stream.writeIntLe(value.size)
                TagSerialization.NetworkLE -> stream.writeIntVar(value.size)
            }

            for (int in value) {
                when (type) {
                    TagSerialization.BE -> stream.writeInt(int)
                    TagSerialization.LE -> stream.writeIntLe(int)
                    TagSerialization.NetworkLE -> stream.writeIntVar(int)
                }
            }
        }

        override fun deserialize(stream: Buffer, type: TagSerialization): IntArrayTag {
            val len = when (type) {
                TagSerialization.BE -> stream.readInt()
                TagSerialization.LE -> stream.readIntLe()
                TagSerialization.NetworkLE -> stream.readIntVar()
            }

            return IntArrayTag(
                List(len) {
                    when (type) {
                        TagSerialization.BE -> stream.readInt()
                        TagSerialization.LE -> stream.readIntLe()
                        TagSerialization.NetworkLE -> stream.readIntVar()
                    }
                }
            )
        }
    }
}
