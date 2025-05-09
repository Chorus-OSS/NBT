package org.chorus_oss.nbt

interface Tag {
    val type: TagType

    override fun toString(): String
}
