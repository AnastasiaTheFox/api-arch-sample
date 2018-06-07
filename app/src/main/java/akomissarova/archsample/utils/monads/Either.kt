package akomissarova.archsample.utils.monads

sealed class Either<L, R> {
    class Left<L, R>(val l: L) : Either<L, R>() {
        override fun toString(): String = "Left $l"
    }

    class Right<L, R>(val r: R) : Either<L, R>() {
        override fun toString(): String = "Right $r"
    }

    infix fun <Rp> bind(f: (R) -> (Either<L, Rp>)): Either<L, Rp> {
        return when (this) {
            is Either.Left<L, R> -> Left<L, Rp>(this.l)
            is Either.Right<L, R> -> f(this.r)
        }
    }

    infix fun <Rp> seq(e: Either<L, Rp>): Either<L, Rp> = e

    inline fun <C> fold(crossinline leftFun: (L) -> C, crossinline rightFun: (R) -> C): C = when (this) {
        is Right<L, R> -> rightFun(this.r)
        is Left<L, R> -> leftFun(this.l)
    }

    companion object {
        fun <L, R> ret(a: R) = Either.Right<L, R>(a)
        fun <L, R> fail(msg: String): Either.Right<L, R> = throw Exception(msg)
    }
}
