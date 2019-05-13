package com.sun.moviesun.util.extension

inline fun <T : Any> T?.notNull(f: (it: T) -> Unit) {
    if (this != null) f(this)
}

inline fun <T : Any> T?.isNull(f: () -> Unit) {
    if (this == null) f()
}

