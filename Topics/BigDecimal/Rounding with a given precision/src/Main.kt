import java.math.BigDecimal
import java.math.RoundingMode

fun main() {             
    // write your code here
    val number = readln().toBigDecimal()
    val newScale = readln().toInt()
    println(number.setScale(newScale,RoundingMode.HALF_DOWN))
}