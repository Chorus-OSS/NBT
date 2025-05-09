package org.chorus_oss.nbt

enum class TagType {
    End,
    Byte,
    Short,
    Int,
    Long,
    Float,
    Double,
    ByteArray,
    String,
    List,
    Compound,
    IntArray,
    LongArray;

    companion object {
        fun toByte(value: TagType): kotlin.Byte {
            return value.ordinal.toByte()
        }

        fun fromByte(value: kotlin.Byte): TagType {
            return entries[value.toInt()]
        }
    }
}