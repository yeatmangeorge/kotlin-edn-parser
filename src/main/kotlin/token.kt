enum class TokenType{
    OpenMap,
    CloseMap,
    Key,
    Int,
    String,
}

val valueTypes = arrayOf(
TokenType.Int, 
            TokenType.String,
            TokenType.OpenMap,
)

fun isValueType(token:TokenType) = valueTypes.contains(token)

data class Token(
    val type: TokenType,
    val literal: StringView,
    val pos: Position,
)

data class Position(var line: Int, var column:Int)