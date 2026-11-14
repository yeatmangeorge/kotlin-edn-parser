# Kotlin EDN Parser
Born to spam elaborate macros in Clojure, forced to write Kotlin. At least, in these trying times, we can now use EDN in Kotlin.

## Features
- Two pass parser
- Lexer using string view pattern for effecient lexing
- Recursive decent parser
- Maps, Ints, Strings

## DISCLAIMER
This is a quickly put together and not particularly idiomatic, as I have been writing C recently, and the code relects this.

It does not support the full spec, and throws instead of handling errors correctly. However, it might be useful as a tool to use EDN as a config file.