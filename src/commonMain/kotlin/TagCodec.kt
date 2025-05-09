package org.chorus_oss.nbt

import kotlinx.io.Buffer

interface TagCodec<T : Tag> {
    fun serialize(value: T, stream: Buffer, type: TagSerialization = TagSerialization.BE)

    fun deserialize(stream: Buffer, type: TagSerialization = TagSerialization.BE): T
}