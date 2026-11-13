fun test_lex(){
    val result = lex("    {}   }    ")
    result.map{ r->   
        println(r.literal.toString())
    }
   require(result.size == 3) 
}

fun main() {
    // testLexer()
    // testParser()
    test_lex()
    println("All tests passed")
}