package org.chorus_oss.nbt.tags

import kotlinx.io.Buffer
import org.chorus_oss.nbt.Tag
import org.chorus_oss.nbt.TagCodec
import org.chorus_oss.nbt.TagSerialization
import org.chorus_oss.nbt.TagType

class EndTag : Tag {
    override val type: TagType = TagType.End

    override fun toString(): String = ""

    companion object : TagCodec<EndTag> {
        override fun serialize(value: EndTag, stream: Buffer, type: TagSerialization) = Unit

        override fun deserialize(stream: Buffer, type: TagSerialization): EndTag = EndTag()
    }
}
