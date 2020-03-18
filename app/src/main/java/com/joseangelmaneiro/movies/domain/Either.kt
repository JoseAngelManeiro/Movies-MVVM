package com.joseangelmaneiro.movies.domain

sealed class Either<out L, out R> {
  data class Left<out L>(val a: L) : Either<L, Nothing>()
  data class Right<out R>(val b: R) : Either<Nothing, R>()

  companion object {
    fun <L> left(value: L): Either<L, Nothing> = Left(value)
    fun <R> right(value: R): Either<Nothing, R> = Right(value)
  }

  val isLeft get() = this is Left
  val isRight get() = this is Right

  val leftValue: L get() = when (this) {
    is Left -> this.a
    is Right -> throw NoSuchElementException()
  }

  val rightValue: R get() = when (this) {
    is Left -> throw NoSuchElementException()
    is Right -> this.b
  }

  fun <T> fold(leftOp: (L) -> T, rightOp: (R) -> T): T = when (this) {
    is Left -> leftOp(this.leftValue)
    is Right -> rightOp(this.rightValue)
  }
}
