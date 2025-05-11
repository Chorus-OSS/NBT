package org.chorus_oss.nbt.tags

import kotlinx.io.Sink
import kotlinx.io.Source
import org.chorus_oss.nbt.Tag
import org.chorus_oss.nbt.TagCodec
import org.chorus_oss.nbt.TagSerialization
import org.chorus_oss.nbt.TagType

class EndTag : Tag {
    override val type: TagType = TagType.End

    override fun toString(): String = ""

    companion object : TagCodec<EndTag> {
        override fun serialize(value: EndTag, stream: Sink, type: TagSerialization) = Unit

        override fun deserialize(stream: Source, type: TagSerialization): EndTag = EndTag()
    }
}
