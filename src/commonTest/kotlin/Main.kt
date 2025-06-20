import kotlinx.io.Buffer
import org.chorus_oss.nbt.Tag
import org.chorus_oss.nbt.TagSerialization
import org.chorus_oss.nbt.tags.ByteArrayTag
import org.chorus_oss.nbt.tags.ByteTag
import org.chorus_oss.nbt.tags.CompoundTag
import org.chorus_oss.nbt.tags.DoubleTag
import org.chorus_oss.nbt.tags.FloatTag
import org.chorus_oss.nbt.tags.IntTag
import org.chorus_oss.nbt.tags.ListTag
import org.chorus_oss.nbt.tags.LongTag
import org.chorus_oss.nbt.tags.ShortTag
import org.chorus_oss.nbt.tags.StringTag
import kotlin.test.Test
import kotlin.test.assertEquals

class Main {
    @Test
    fun all() {
        val tag = CompoundTag(mapOf(
            "byte" to ByteTag(42),
            "short" to ShortTag(42),
            "int" to IntTag(42),
            "long" to LongTag(42),
            "float" to FloatTag(42f),
            "double" to DoubleTag(42.0),
            "byteArray" to ByteArrayTag(listOf(1, 2, 3)),
            "string" to StringTag("Hello World!"),
            "list" to ListTag(listOf(
                CompoundTag(mapOf(
                    "name" to StringTag("Compound 1")
                )),
                CompoundTag(mapOf(
                    "name" to StringTag("Compound 2")
                ))
            )),
            "compound" to CompoundTag(mapOf(
                "name" to StringTag("Compound 3")
            ))
        ))

        val bytesBE = Buffer().apply { Tag.serialize(tag, this, TagSerialization.BE, true) }
        val bytesLE = Buffer().apply { Tag.serialize(tag, this, TagSerialization.LE, true) }
        val bytesNetLE = Buffer().apply { Tag.serialize(tag, this, TagSerialization.NetLE, true) }

        val fromBytesBE = Tag.deserialize(bytesBE, TagSerialization.BE)
        val fromBytesLE = Tag.deserialize(bytesLE, TagSerialization.LE)
        val fromBytesNetLE = Tag.deserialize(bytesNetLE, TagSerialization.NetLE)

        assertEquals(tag, fromBytesBE)
        assertEquals(tag, fromBytesLE)
        assertEquals(tag, fromBytesNetLE)
    }
}