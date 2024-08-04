package com.rtga.domain.errorhandler

sealed class Either<out L, out R> {
    abstract fun <C> fold(ifLeft: (L) -> C, ifRight: (R) -> C): C
    data class Left<out L>(val value: L) : Either<L, Nothing>() {
        override fun <C> fold(ifLeft: (L) -> C, ifRight: (Nothing) -> C) = ifLeft(value)
    }

    data class Right<out R>(val value: R) : Either<Nothing, R>() {
        override fun <C> fold(ifLeft: (Nothing) -> C, ifRight: (R) -> C) = ifRight(value)
    }
}
