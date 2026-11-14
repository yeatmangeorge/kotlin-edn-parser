private data class ParserCtx(
    val toks: List<Token>
){
    var currentToken = 0
    var result = mapOf<String, Any>()
    
    fun current(): Token = toks[currentToken]
    
    fun atEnd() = currentToken>=toks.size

    fun peek(): Token? =
        if(atEnd())null else toks[currentToken+1]
        
    fun advance(): Token {
        ++currentToken
        if(atEnd()) throw Exception("Advanced past end")
        return toks[currentToken]
    } 
        
   fun expectAdvance(vararg types: TokenType): Token {
    val next = advance()

    if (!types.contains(next.type))
        throw Exception(
            "Expected: ${types.joinToString(", or ")}, but got: $next"
        )

    return next
    }
}

data class Pair<K,V>(val k:K,val v:V)

private fun parseValue(ctx: ParserCtx):Any{
    val current = ctx.current()
    if(!isValueType(current.type)) throw Exception("Expected a value type, but got: ${current.type}")
    return when(current.type){
        TokenType.Int -> current.literal.toString().toInt()
        TokenType.String -> {
            //remove the surrounding ""
            current.literal.start++
            current.literal.end--
            return current.literal.toString()
        }
        else ->throw Exception("Unsupported value type")
    }
}

private fun parseKeyValuePair(ctx: ParserCtx):Pair<String, Any>{
    val key = ctx.current()
    require(key.type == TokenType.Key)
    //remove leading :
    key.literal.start++
    val kotlinKey = key.literal.toString()
    val value = ctx.expectAdvance(TokenType.Int, TokenType.String)
    return Pair(kotlinKey, parseValue(ctx))
}

private fun parseMap(ctx:ParserCtx) : Map<String, Any>{
    require(ctx.current().type == TokenType.OpenMap)
    var result = mutableMapOf<String, Any>()
    while(!ctx.atEnd()){
        val new = ctx.expectAdvance(TokenType.CloseMap, TokenType.Key)
        when(new.type) {
            TokenType.Key -> {
                val p = parseKeyValuePair(ctx)
                result.put(p.k, p.v)
            }
            TokenType.CloseMap -> return result 
            else->throw Exception("Expected Key Value pair or close map @${new.pos.line}:${new.pos.column}")
        }
    }
    throw Exception("No Closing brace for map")
}

fun parse(toks: List<Token>): Map<String, Any>{
    val ctx = ParserCtx(toks)
    return parseMap(ctx)
}