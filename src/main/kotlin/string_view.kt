data class StringView(
    val source: String,
    var start: Int,
    var end: Int
) {
    val length: Int
        get() = end - start

    fun get(index: Int): Char {
        require(index in 0 until length)
        return source[start + index]
    }

    fun slice(start: Int, end: Int): StringView {
        require(start >= 0 && end >= start && end <= length)

        return StringView(
            source,
            this.start + start,
            this.start + end
        )
    }

    override fun toString(): String =
        source.substring(start, end)
}