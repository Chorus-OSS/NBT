package org.chorus_oss.nbt

import kotlinx.io.*
import org.chorus_oss.varlen.types.readIntVar
import org.chorus_oss.varlen.types.readUIntVar
import org.chorus_oss.varlen.types.writeIntVar
import org.chorus_oss.varlen.types.writeUIntVar

object TagHelper {
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
        val len = when (type) {
            TagSerialization.BE -> stream.readInt()
            TagSerialization.LE -> stream.readIntLe()
            TagSerialization.NetworkLE -> stream.readIntVar()
        }

        return List(len) { fn(stream) }
    }

    fun <T> serializeList(list: List<T>, stream: Buffer, type: TagSerialization, fn: (T, Buffer) -> Unit) {
        when (type) {
            TagSerialization.BE -> stream.writeInt(list.size)
            TagSerialization.LE -> stream.writeIntLe(list.size)
            TagSerialization.NetworkLE -> stream.writeIntVar(list.size)
        }

        for (item in list) {
            fn(item, stream)
        }
    }
}