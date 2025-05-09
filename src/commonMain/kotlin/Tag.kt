package org.chorus_oss.nbt

import kotlinx.io.Buffer
import org.chorus_oss.nbt.tags.*

interface Tag {
    val type: TagType

    override fun toString(): String

    companion object {
        fun serialize(value: Tag, stream: Buffer, type: TagSerialization, isRoot: Boolean = false) {
            if (isRoot) {
                stream.writeByte(TagType.toByte(value.type))
                TagHelper.serializeString("", stream, type)
            }

            when (value.type) {
                TagType.Byte -> ByteTag.serialize(value as ByteTag, stream, type)
                TagType.Short -> ShortTag.serialize(value as ShortTag, stream, type)
                TagType.Int -> IntTag.serialize(value as IntTag, stream, type)
                TagType.Long -> LongTag.serialize(value as LongTag, stream, type)
                TagType.Float -> FloatTag.serialize(value as FloatTag, stream, type)
                TagType.Double -> DoubleTag.serialize(value as DoubleTag, stream, type)
                TagType.ByteArray -> ByteArrayTag.serialize(value as ByteArrayTag, stream, type)
                TagType.String -> StringTag.serialize(value as StringTag, stream, type)
                TagType.List -> ListTag.serialize(value as ListTag<*>, stream, type)
                TagType.Compound -> CompoundTag.serialize(value as CompoundTag, stream, type)
                TagType.IntArray -> IntArrayTag.serialize(value as IntArrayTag, stream, type)
                TagType.LongArray -> LongArrayTag.serialize(value as LongArrayTag, stream, type)
                else -> Unit
            }
        }

        fun deserialize(stream: Buffer, type: TagSerialization): Tag {
            val tagType = TagType.fromByte(stream.readByte())
            TagHelper.deserializeString(stream, type)

            return deserialize(tagType, stream, type)
        }

        fun deserialize(value: TagType, stream: Buffer, type: TagSerialization): Tag {
            return when (value) {
                TagType.Byte -> ByteTag.deserialize(stream, type)
                TagType.Short -> ShortTag.deserialize(stream, type)
                TagType.Int -> IntTag.deserialize(stream, type)
                TagType.Long -> LongTag.deserialize(stream, type)
                TagType.Float -> FloatTag.deserialize(stream, type)
                TagType.Double -> DoubleTag.deserialize(stream, type)
                TagType.ByteArray -> ByteArrayTag.deserialize(stream, type)
                TagType.String -> StringTag.deserialize(stream, type)
                TagType.List -> ListTag.deserialize(stream, type)
                TagType.Compound -> CompoundTag.deserialize(stream, type)
                TagType.IntArray -> IntArrayTag.deserialize(stream, type)
                TagType.LongArray -> LongArrayTag.deserialize(stream, type)
                else -> EndTag()
            }
        }
    }
}
