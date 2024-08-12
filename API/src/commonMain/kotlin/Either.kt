sealed class Either<out L, out R> {
    class Left<out L>(val value: L) : Either<L, Nothing>()
    class Right<out R>(val value: R) : Either<Nothing, R>()

    inline fun <T> fold(left: (L) -> T, right: (R) -> T): T =
        when (this) {
            is Left -> left(value)
            is Right -> right(value)
        }
}