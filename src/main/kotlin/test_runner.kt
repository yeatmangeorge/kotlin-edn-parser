fun test_lex(){
    val result = lex("  { :abc  {:123}   }    ")
    result.map{ r->   
        println(r.literal.toString())
    }
   require(result.size == 6)  
}

fun main() {
    // testLexer()
    // testParser()
    test_lex()
    println("All tests passed")
}