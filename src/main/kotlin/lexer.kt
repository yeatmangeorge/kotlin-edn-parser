/*
{:database {:host "localhost"
            :port 5432
            :name "myapp_prod"
            :pool-size 10}
 :logging {:level :info
           :file "/var/log/myapp.log"
           :max-size "100MB"}
 :features #{:notifications :analytics :beta-ui}
 :cache-ttl 3600}
 */

enum class TokenType{
    OpenMap,
    CloseMap,
    Key,
    Int
}

data class Token(
    val type: TokenType,
    val literal: StringView,
    val pos: Position,
)

data class Position(var line: Int, var column:Int)

private class Ctx(val input:String){
    var current: StringView
    val tok : MutableList<Token> = mutableListOf()
    var pos: Position = Position(0,0)

   init {
      current = StringView(input, 0, 1) 
   } 
   
  fun peek(): Char = current.source[++current.end]
  
  fun atEnd() = current.start >= input.length

  fun swallow() {
      if(current.toString() == "\n"){
        pos.line++
        pos.column=0
      }else{
        pos.column+=current.start-current.end
      }
    current.start = current.end
    current.end++
  }
  
  fun swallowToken(type: TokenType){
    tok.add(Token( type, current.copy(), pos.copy()))
    swallow()
  }
}

private fun lexKey(ctx: Ctx){
    while (ctx.peek().let { 
        it != ' ' && it != '\n' && it != '{'  && it != '}' }) {
            if(ctx.current.end>=ctx.input.length) break
        } 
    ctx.swallowToken(TokenType.Key)
}

fun lex(input: String): List<Token>{
    val ctx = Ctx(input)
    while(!ctx.atEnd()){
        when(val str = ctx.current.toString()){
            " ","\n" -> ctx.swallow()
            "{" -> ctx.swallowToken(TokenType.OpenMap)
            "}" -> ctx.swallowToken(TokenType.CloseMap)
            ":" -> lexKey(ctx) 
        }
    }
    return ctx.tok
}