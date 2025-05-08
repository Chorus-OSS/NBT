package org.chorus_oss.nbt.tags

data class ByteTag(
    var data: Byte = 0
) : Tag<Byte>() {
    override val id: Byte
        get() = TAG_BYTE

    override fun parseValue(): Byte {
        return data
    }

    override fun toString(): String {
        var hex = this.data.toString(16).padStart(2, '0').uppercase()
        if (hex.length < 2) {
            hex = "0$hex"
        }
        return "ByteTag  (data: 0x$hex)"
    }

    override fun toSNBT(): String {
        return data.toString() + "b"
    }

    override fun toSNBT(space: Int): String {
        return data.toString() + "b"
    }

    override fun copy(): Tag<Byte> {
        return ByteTag(data)
    }
}
