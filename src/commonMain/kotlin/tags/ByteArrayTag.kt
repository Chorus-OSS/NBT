package org.chorus_oss.nbt.tags

data class ByteArrayTag(
    var data: ByteArray = ByteArray(0)
) : Tag<ByteArray>() {
    override val id: Byte
        get() = TAG_BYTE_ARRAY

    @OptIn(ExperimentalStdlibApi::class)
    override fun toString(): String {
        return "ByteArrayTag " + " (data: 0x" + data.toHexString() + " [" + data.size + " bytes])"
    }

    override fun toSNBT(): String {
        val builder = StringBuilder("[B;")
        for (i in 0..<data.size - 1) {
            builder.append(data[i].toInt()).append('b').append(',')
        }
        builder.append(data[data.size - 1].toInt()).append("b]")
        return builder.toString()
    }

    override fun toSNBT(space: Int): String {
        val builder = StringBuilder("[B; ")
        for (i in 0..<data.size - 1) {
            builder.append(data[i].toInt()).append("b, ")
        }
        builder.append(data[data.size - 1].toInt()).append("b]")
        return builder.toString()
    }

    override fun copy(): Tag<ByteArray> {
        return ByteArrayTag(this.data.copyOf())
    }

    override fun parseValue(): ByteArray {
        return this.data
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ByteArrayTag

        return data.contentEquals(other.data)
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}
