package org.chorus_oss.nbt.tags

import org.chorus_oss.chorus.nbt.tag.*
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set
import kotlin.math.max
import kotlin.reflect.KClass

data class CompoundTag(
    var tags: MutableMap<String, Tag<*>> = mutableMapOf()
) :
    Tag<MutableMap<String, Any>>() { 
    val allTags: Collection<Tag<*>>
        get() = tags.values

    override val id: Byte
        get() = TAG_COMPOUND

    fun put(name: String, tag: Tag<*>): CompoundTag {
        tags[name] = tag
        return this
    }

    fun putIfNull(name: String, tag: Tag<*>): CompoundTag {
        if (!tags.containsKey(name)) {
            tags[name] = tag
        }
        return this
    }

    fun putIfNull(other: CompoundTag): CompoundTag {
        for ((key, value) in other.tags) {
            this.putIfNull(key, value)
        }
        return this
    }

    fun putByte(name: String, value: Byte): CompoundTag {
        tags[name] = ByteTag(value)
        return this
    }

    fun putShort(name: String, value: Int): CompoundTag {
        tags[name] = ShortTag(value)
        return this
    }

    fun putInt(name: String, value: Int): CompoundTag {
        tags[name] = IntTag(value)
        return this
    }

    fun putLong(name: String, value: Long): CompoundTag {
        tags[name] = LongTag(value)
        return this
    }

    fun putFloat(name: String, value: Float): CompoundTag {
        tags[name] = FloatTag(value)
        return this
    }

    fun putDouble(name: String, value: Double): CompoundTag {
        tags[name] = DoubleTag(value)
        return this
    }

    fun putString(name: String, value: String): CompoundTag {
        tags[name] = StringTag(value)
        return this
    }

    fun putByteArray(name: String, value: ByteArray): CompoundTag {
        tags[name] = ByteArrayTag(value)
        return this
    }

    fun putIntArray(name: String, value: IntArray): CompoundTag {
        tags[name] = IntArrayTag(value)
        return this
    }

    fun putList(name: String, listTag: ListTag<*>): CompoundTag {
        tags[name] = listTag
        return this
    }

    fun putCompound(name: String, value: CompoundTag): CompoundTag {
        tags[name] = value
        return this
    }

    fun putBoolean(string: String, value: Boolean): CompoundTag {
        putByte(string, if (value) 1 else 0)
        return this
    }

    operator fun get(name: String): Tag<*>? {
        return tags[name]
    }

    fun contains(name: String): Boolean {
        return tags.containsKey(name)
    }

    fun containsCompound(name: String): Boolean {
        return tags[name] is CompoundTag
    }

    fun containsString(name: String): Boolean {
        return tags[name] is StringTag
    }

    fun containsIntArray(name: String): Boolean {
        return tags[name] is IntArrayTag
    }

    fun containsByteArray(name: String): Boolean {
        return tags[name] is ByteArrayTag
    }

    fun containsNumber(name: String): Boolean {
        return tags[name] is NumberTag<*>
    }

    fun containsList(name: String): Boolean {
        return tags[name] is ListTag<*>
    }

    fun containsList(name: String, type: Byte): Boolean {
        val tag = tags[name] as? ListTag<*> ?: return false
        val listType = tag.type
        return listType.toInt() == 0 || listType == type
    }

    fun containsByte(name: String): Boolean {
        return tags[name] is ByteTag
    }

    fun containsShort(name: String): Boolean {
        return tags[name] is ShortTag
    }

    fun containsInt(name: String): Boolean {
        return tags[name] is IntTag
    }

    fun containsDouble(name: String): Boolean {
        return tags[name] is DoubleTag
    }

    fun containsFloat(name: String): Boolean {
        return tags[name] is FloatTag
    }

    fun exist(name: String): Boolean {
        return tags.containsKey(name)
    }

    fun remove(name: String): CompoundTag {
        tags.remove(name)
        return this
    }

    fun <T : Tag<*>> removeAndGet(name: String): T? {
        @Suppress("UNCHECKED_CAST")
        return tags.remove(name) as? T?
    }

    fun getByte(name: String): Byte {
        return (tags[name] as NumberTag<*>?)?.data?.toByte() ?: 0
    }

    fun getShort(name: String): Short {
        return (tags[name] as NumberTag<*>?)?.data?.toShort() ?: 0
    }

    fun getInt(name: String): Int {
        return (tags[name] as NumberTag<*>?)?.data?.toInt() ?: 0
    }

    fun getLong(name: String): Long {
        return (tags[name] as NumberTag<*>?)?.data?.toLong() ?: 0L
    }

    fun getFloat(name: String): Float {
        return (tags[name] as NumberTag<*>?)?.data?.toFloat() ?: 0.0f
    }

    fun getDouble(name: String): Double {
        return (tags[name] as NumberTag<*>?)?.data?.toDouble() ?: 0.0
    }

    fun getBoolean(name: String): Boolean {
        return getByte(name).toInt() != 0
    }

    fun getString(name: String): String {
        if (!tags.containsKey(name)) return ""
        val tag = tags[name]
        if (tag is NumberTag<*>) {
            return tag.data.toString()
        }
        return (tag as StringTag).data
    }

    fun getByteArray(name: String): ByteArray? {
        return (tags[name] as? ByteArrayTag?)?.data
    }

    fun getIntArray(name: String): IntArray? {
        return (tags[name] as? IntArrayTag?)?.data
    }

    fun getCompound(name: String): CompoundTag? {
        return tags[name] as? CompoundTag?
    }

    fun getList(name: String): ListTag<*>? {
        return tags[name] as? ListTag<*>?
    }

    fun <T : Tag<*>> getList(name: String): ListTag<T>? {
        @Suppress("UNCHECKED_CAST")
        return tags[name] as? ListTag<T>
    }

    val entrySet: Set<Map.Entry<String, Tag<*>>>
        get() = tags.entries

    override fun parseValue(): MutableMap<String, Any> {
        val value: MutableMap<String, Any> = HashMap(
            tags.size
        )

        for ((key, value1) in tags) {
            value[key] = value1.parseValue()
        }

        return value
    }

    override fun toString(): String {
        val joiner = StringJoiner(",\n\t")
        tags.forEach { (key, tag) ->
            joiner.add(
                '\''.toString() + key + "' : " + tag.toString().replace("\n", "\n\t")
            )
        }
        return "CompoundTag '" + "' (" + tags.size + " entries) {\n\t" + joiner + "\n}"
    }

    override fun toSNBT(): String {
        val joiner = StringJoiner(",")
        tags.forEach { (key, tag) -> joiner.add("\"" + key + "\":" + tag.toSNBT()) }
        return "{$joiner}"
    }

    override fun toSNBT(space: Int): String {
        val addSpace = StringBuilder()
        addSpace.append(" ".repeat(max(0.0, space.toDouble()).toInt()))
        val joiner = StringJoiner(",\n$addSpace")
        tags.forEach { (key, tag) ->
            joiner.add(
                "\"$key\": " + tag.toSNBT(space).replace(
                    "\n",
                    """
                    
                    $addSpace
                    """.trimIndent()
                )
            )
        }
        return "{\n$addSpace$joiner\n}"
    }

    val isEmpty: Boolean
        get() = tags.isEmpty()

    override fun copy(): CompoundTag {
        val tag = CompoundTag()
        for (key in tags.keys) {
            tag.put(key, tags[key]!!.copy())
        }
        return tag
    }
}
