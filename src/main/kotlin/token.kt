enum class TokenType{
    OpenMap,
    CloseMap,
    Key,
    Int,
    String,
}

data class Token(
    val type: TokenType,
    val literal: StringView,
    val pos: Position,
)

data class Position(var line: Int, var column:Int)