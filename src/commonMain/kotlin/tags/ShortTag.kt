package org.chorus_oss.nbt.tags

import kotlinx.io.Buffer
import kotlinx.io.readShortLe
import kotlinx.io.writeShortLe
import org.chorus_oss.nbt.Tag
import org.chorus_oss.nbt.TagCodec
import org.chorus_oss.nbt.TagSerialization
import org.chorus_oss.nbt.TagType

data class ShortTag(val data: Short = 0) : Tag {
    override val type: TagType = TagType.Short

    override fun toString(): String = "${data}s"

    companion object : TagCodec<ShortTag> {
        override fun serialize(value: ShortTag, stream: Buffer, type: TagSerialization) {
            when (type) {
                TagSerialization.BE -> stream.writeShort(value.data)
                TagSerialization.LE,
                TagSerialization.NetworkLE -> stream.writeShortLe(value.data)
            }
        }

        override fun deserialize(stream: Buffer, type: TagSerialization): ShortTag {
            return ShortTag(
                when (type) {
                    TagSerialization.BE -> stream.readShort()
                    TagSerialization.LE,
                    TagSerialization.NetworkLE -> stream.readShortLe()
                }
            )
        }
    }
}
