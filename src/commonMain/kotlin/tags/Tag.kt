package org.chorus_oss.nbt.tags

import org.chorus_oss.chorus.nbt.tag.*
import kotlin.reflect.KClass

abstract class Tag<T : Any> {
    abstract override fun toString(): String

    abstract fun toSNBT(): String

    abstract fun toSNBT(space: Int): String

    abstract val id: Byte

    abstract fun copy(): Tag<T>

    abstract fun parseValue(): T

    companion object {
        const val TAG_END: Byte = 0
        const val TAG_BYTE: Byte = 1
        const val TAG_SHORT: Byte = 2
        const val TAG_INT: Byte = 3
        const val TAG_LONG: Byte = 4
        const val TAG_FLOAT: Byte = 5
        const val TAG_DOUBLE: Byte = 6
        const val TAG_BYTE_ARRAY: Byte = 7
        const val TAG_STRING: Byte = 8
        const val TAG_LIST: Byte = 9
        const val TAG_COMPOUND: Byte = 10
        const val TAG_INT_ARRAY: Byte = 11

        fun newTag(type: Byte): Tag<*> {
            return when (type) {
                TAG_END -> EndTag()
                TAG_BYTE -> ByteTag()
                TAG_SHORT -> ShortTag()
                TAG_INT -> IntTag()
                TAG_LONG -> LongTag()
                TAG_FLOAT -> FloatTag()
                TAG_DOUBLE -> DoubleTag()
                TAG_BYTE_ARRAY -> ByteArrayTag()
                TAG_INT_ARRAY -> IntArrayTag()
                TAG_STRING -> StringTag()
                TAG_LIST -> ListTag<Tag<*>>()
                TAG_COMPOUND -> CompoundTag()
                else -> EndTag()
            }
        }

        fun getTagName(type: Byte): String {
            return when (type) {
                TAG_END -> "TAG_End"
                TAG_BYTE -> "TAG_Byte"
                TAG_SHORT -> "TAG_Short"
                TAG_INT -> "TAG_Int"
                TAG_LONG -> "TAG_Long"
                TAG_FLOAT -> "TAG_Float"
                TAG_DOUBLE -> "TAG_Double"
                TAG_BYTE_ARRAY -> "TAG_Byte_Array"
                TAG_INT_ARRAY -> "TAG_Int_Array"
                TAG_STRING -> "TAG_String"
                TAG_LIST -> "TAG_List"
                TAG_COMPOUND -> "TAG_Compound"
                else -> "UNKNOWN"
            }
        }

        fun getTagType(type: KClass<*>): Int {
            return when (type) {
                ListTag::class -> TAG_LIST.toInt()
                CompoundTag::class -> TAG_COMPOUND.toInt()
                EndTag::class -> TAG_END.toInt()
                ByteTag::class -> TAG_BYTE.toInt()
                ShortTag::class -> TAG_SHORT.toInt()
                IntTag::class -> TAG_INT.toInt()
                FloatTag::class -> TAG_FLOAT.toInt()
                LongTag::class -> TAG_LONG.toInt()
                DoubleTag::class -> TAG_DOUBLE.toInt()
                ByteArrayTag::class -> TAG_BYTE_ARRAY.toInt()
                IntArrayTag::class -> TAG_INT_ARRAY.toInt()
                StringTag::class -> TAG_INT_ARRAY.toInt()
                else -> throw IllegalArgumentException("Unknown tag type " + type.simpleName)
            }
        }
    }
}
