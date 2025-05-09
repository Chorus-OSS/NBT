package org.chorus_oss.nbt.tags

import kotlinx.io.Buffer
import kotlinx.io.readIntLe
import kotlinx.io.writeIntLe
import org.chorus_oss.nbt.*
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
            TagHelper.serializeList(value, stream, type) { int, st ->
                when (type) {
                    TagSerialization.BE -> st.writeInt(int)
                    TagSerialization.LE -> st.writeIntLe(int)
                    TagSerialization.NetworkLE -> st.writeIntVar(int)
                }
            }
        }

        override fun deserialize(stream: Buffer, type: TagSerialization): IntArrayTag {
            return IntArrayTag(
                TagHelper.deserializeList(stream, type) {
                    when (type) {
                        TagSerialization.BE -> it.readInt()
                        TagSerialization.LE -> it.readIntLe()
                        TagSerialization.NetworkLE -> it.readIntVar()
                    }
                }
            )
        }
    }
}
