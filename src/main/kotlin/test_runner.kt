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
    test_lex()
    println("All tests passed")
}