package org.chorus_oss.nbt

import kotlinx.io.*
import org.chorus_oss.varlen.types.*

object TagHelper {
    fun serializeInt(value: Int, stream: Sink, type: TagSerialization) {
        when (type) {
            TagSerialization.BE -> stream.writeInt(value)
            TagSerialization.LE -> stream.writeIntLe(value)
            TagSerialization.NetLE -> stream.writeIntVar(value)
        }
    }

    fun deserializeInt(stream: Source, type: TagSerialization): Int {
        return when (type) {
            TagSerialization.BE -> stream.readInt()
            TagSerialization.LE -> stream.readIntLe()
            TagSerialization.NetLE -> stream.readIntVar()
        }
    }

    fun serializeLong(value: Long, stream: Sink, type: TagSerialization) {
        when (type) {
            TagSerialization.BE -> stream.writeLong(value)
            TagSerialization.LE -> stream.writeLongLe(value)
            TagSerialization.NetLE -> stream.writeLongVar(value)
        }
    }

    fun deserializeLong(stream: Source, type: TagSerialization): Long {
        return when (type) {
            TagSerialization.BE -> stream.readLong()
            TagSerialization.LE -> stream.readLongLe()
            TagSerialization.NetLE -> stream.readLongVar()
        }
    }

    fun serializeString(value: String, stream: Sink, type: TagSerialization) {
        when (type) {
            TagSerialization.BE -> stream.writeShort(value.length.toShort())
            TagSerialization.LE -> stream.writeShortLe(value.length.toShort())
            TagSerialization.NetLE -> stream.writeUIntVar(value.length.toUInt())
        }
        stream.write(value.encodeToByteArray())
    }

    fun deserializeString(stream: Source, type: TagSerialization): String {
        return stream.readByteArray(
            when (type) {
                TagSerialization.BE -> stream.readShort().toInt()
                TagSerialization.LE -> stream.readShortLe().toInt()
                TagSerialization.NetLE -> stream.readUIntVar().toInt()
            }
        ).decodeToString()
    }

    fun <T> deserializeList(stream: Source, type: TagSerialization, fn: (Source) -> T): List<T> {
        val len = deserializeInt(stream, type)

        return List(len) { fn(stream) }
    }

    fun <T> serializeList(list: List<T>, stream: Sink, type: TagSerialization, fn: (T, Sink) -> Unit) {
        serializeInt(list.size, stream, type)

        for (item in list) {
            fn(item, stream)
        }
    }
}