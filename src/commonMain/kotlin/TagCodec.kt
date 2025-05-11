package org.chorus_oss.nbt

import kotlinx.io.Sink
import kotlinx.io.Source

interface TagCodec<T : Tag> {
    fun serialize(value: T, stream: Sink, type: TagSerialization = TagSerialization.BE)

    fun deserialize(stream: Source, type: TagSerialization = TagSerialization.BE): T
}