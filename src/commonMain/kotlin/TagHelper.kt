package org.chorus_oss.nbt

import kotlinx.io.*
import org.chorus_oss.varlen.types.*

object TagHelper {
    fun serializeInt(value: Int, stream: Buffer, type: TagSerialization) {
        when (type) {
            TagSerialization.BE -> stream.writeInt(value)
            TagSerialization.LE -> stream.writeIntLe(value)
            TagSerialization.NetworkLE -> stream.writeIntVar(value)
        }
    }

    fun deserializeInt(stream: Buffer, type: TagSerialization): Int {
        return when (type) {
            TagSerialization.BE -> stream.readInt()
            TagSerialization.LE -> stream.readIntLe()
            TagSerialization.NetworkLE -> stream.readIntVar()
        }
    }

    fun serializeLong(value: Long, stream: Buffer, type: TagSerialization) {
        when (type) {
            TagSerialization.BE -> stream.writeLong(value)
            TagSerialization.LE -> stream.writeLongLe(value)
            TagSerialization.NetworkLE -> stream.writeLongVar(value)
        }
    }

    fun deserializeLong(stream: Buffer, type: TagSerialization): Long {
        return when (type) {
            TagSerialization.BE -> stream.readLong()
            TagSerialization.LE -> stream.readLongLe()
            TagSerialization.NetworkLE -> stream.readLongVar()
        }
    }

    fun serializeString(value: String, stream: Buffer, type: TagSerialization) {
        when (type) {
            TagSerialization.BE -> stream.writeShort(value.length.toShort())
            TagSerialization.LE -> stream.writeShortLe(value.length.toShort())
            TagSerialization.NetworkLE -> stream.writeUIntVar(value.length.toUInt())
        }
        stream.write(value.encodeToByteArray())
    }

    fun deserializeString(stream: Buffer, type: TagSerialization): String {
        return stream.readByteArray(
            when (type) {
                TagSerialization.BE -> stream.readShort().toInt()
                TagSerialization.LE -> stream.readShortLe().toInt()
                TagSerialization.NetworkLE -> stream.readUIntVar().toInt()
            }
        ).decodeToString()
    }

    fun <T> deserializeList(stream: Buffer, type: TagSerialization, fn: (Buffer) -> T): List<T> {
        val len = deserializeInt(stream, type)

        return List(len) { fn(stream) }
    }

    fun <T> serializeList(list: List<T>, stream: Buffer, type: TagSerialization, fn: (T, Buffer) -> Unit) {
        serializeInt(list.size, stream, type)

        for (item in list) {
            fn(item, stream)
        }
    }
}