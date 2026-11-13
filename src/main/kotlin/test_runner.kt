fun test_lex(){
    val result = lex( """
            {:database {:port 5432
                        :host "lsdkfj"
                        :pool-size 10}}
    """)
    result.map{ r->   
        println(r.literal.toString())
    }
   require(result.size == 11)  
}

fun main() {
    // testLexer()
    // testParser()
    test_lex()
    println("All tests passed")
}