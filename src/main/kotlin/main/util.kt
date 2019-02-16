package main

import glm_.L
import java.foreign.NativeTypes
import java.foreign.Scope
import java.foreign.memory.Pointer

fun Char.parseInt() = java.lang.Character.getNumericValue(this)


inline fun <R> scope(block: Scope.() -> R): R {
    val sc = Scope.newNativeScope()
    return sc.block().also {
        sc.close()
    }
}

inline fun <R> scope(string: String, block: Scope.(Pointer<Byte>) -> R): R = scope { block(alloc(string)) }

inline fun <R> scopeByte(block: Scope.(Pointer<Byte>) -> R): R = scope { block(allocByte()) }
inline fun <R> scopeShort(block: Scope.(Pointer<Short>) -> R): R = scope { block(allocShort()) }
inline fun <R> scopeInt(block: Scope.(Pointer<Int>) -> R): R = scope { block(allocInt()) }
inline fun <R> scopeLong(block: Scope.(Pointer<Long>) -> R): R = scope { block(allocLong()) }
inline fun <R> scopeFloat(block: Scope.(Pointer<Float>) -> R): R = scope { block(allocFloat()) }
inline fun <R> scopeDouble(block: Scope.(Pointer<Double>) -> R): R = scope { block(allocDouble()) }

fun Scope.allocByte() = allocate(NativeTypes.INT8)
fun Scope.allocShort() = allocate(NativeTypes.INT16)
fun Scope.allocInt() = allocate(NativeTypes.INT)
fun Scope.allocLong() = allocate(NativeTypes.LONG)
fun Scope.allocFloat() = allocate(NativeTypes.FLOAT)
fun Scope.allocDouble() = allocate(NativeTypes.DOUBLE)
fun Scope.alloc(string: String) = allocateCString(string)

operator fun <T> Pointer<T>.invoke(): T = get()
operator fun <T> java.foreign.memory.Array<T>.get(i: Int): T = get(i.L)


inline fun <reified T> Pointer<T>.toArraySafe(size: Pointer<Int>): Array<Pointer<T>>? = when{
    isNull -> null
    else -> toArray(size)
}
inline fun <reified T> Pointer<T>.toArray(size: Pointer<Int>): Array<Pointer<T>> = Array(size()) { offset(it.L) }

fun Pointer<Byte>.toTypedArraySafe(size: Pointer<Int>): ByteArray? = when {
    isNull -> null
    else -> toTypedArray(size)
}

fun Pointer<Byte>.toTypedArray(size: Pointer<Int>): ByteArray = ByteArray(size()) { offset(it.L)() }

fun Pointer<Int>.toTypedArraySafe(size: Pointer<Int>): IntArray? = when {
    isNull -> null
    else -> toTypedArray(size)
}

fun Pointer<Int>.toTypedArray(size: Pointer<Int>): IntArray = IntArray(size()) { offset(it.L)() }

fun Pointer<Float>.toTypedArraySafe(size: Pointer<Int>): FloatArray? = when {
    isNull -> null
    else -> toTypedArray(size)
}

fun Pointer<Float>.toTypedArray(size: Pointer<Int>): FloatArray = FloatArray(size()) { offset(it.L)() }