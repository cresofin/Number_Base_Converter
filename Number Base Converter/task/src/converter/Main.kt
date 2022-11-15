import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import kotlin.math.pow
val letters = mapOf('a' to 10, 'b' to 11, 'c' to 12,'d' to 13,
    'e' to 14, 'f' to 15,'g' to 16,'h' to 17,'i' to 18,'j' to 19,'k' to 20,
    'l' to 21,'m' to 22, 'n' to 23,'o' to 24,'p' to 25,'q' to 26,'r' to 27,
    's' to 28,'t' to 29,'u' to 30,'v' to 31,'w' to 32,'x' to 33,'y' to 34,'z' to 35)

fun main() {
    while(true) {
        print("Enter two numbers in format: {source base} {target base} (To quit type /exit)")
        val bases = readln().split(" ")
        if ("/exit" in bases) {
            return
        }
        while (true) {
            print("Enter number in base ${bases[0]} to convert to base ${bases[1]} (To go back type /back)")
            val number = readln().lowercase()
            val numberNotDecimal = mutableListOf<Char>()
            val partIntRecived = mutableListOf<Char>()
            val partDecRecived = mutableListOf<Char>()
            if (number == "/back") {
                break
            }
            if (number.contains('.')) {
                val numberDecimal = number.split(".")
                for (i in numberDecimal[0]) {
                    partIntRecived.add(i)
                }
                for (i in numberDecimal[1]) {
                    partDecRecived.add(i)
                }
                println("Conversion result: " + convertToTargetBase(bases[1].toBigInteger(),
                    converToDecimal(bases[0].toInt(), partIntRecived)) + "." +
                        convertDecimalPart(partDecRecived,bases[1].toBigInteger(),bases[0].toBigInteger() ))

            } else {
                for (i in number){
                    numberNotDecimal += i
                }
                if (numberNotDecimal.joinToString() == "0"){
                    println("Conversion result: 0")
                } else {
                    println("Conversion result: " + convertToTargetBase(bases[1].toBigInteger(),
                        converToDecimal(bases[0].toInt(), numberNotDecimal)))
                }
            }
        }
    }
}

fun converToDecimal(sorceBase : Int, number : MutableList<Char>) : BigInteger {
    if (number.joinToString("") == "0"){
        return BigInteger.ZERO
    }
    val numberToConvert= number.toMutableList().reversed()
    var covertedNum = BigInteger.ZERO
    var pow = 0


    for (i in numberToConvert) {
        if (i.isLetter()){
            covertedNum += (letters[i]?.toBigInteger()?.times(sorceBase.toBigInteger().pow(pow))!!)
            pow++
        } else {
            covertedNum += (i.toString().toBigInteger() * (sorceBase.toBigInteger().pow(pow)))
            pow++
        }
    }
    return  covertedNum
}

fun convertToTargetBase(targetBase: BigInteger, number : BigInteger) : String {
    if (number == BigInteger.ZERO) {
        return "0"
    }
    val convertedNum = mutableListOf<String>()
    var numberToOperate = number
    while (numberToOperate != BigInteger.ZERO) {
        if (numberToOperate % targetBase <= 9.toBigInteger()) {
            convertedNum.add((numberToOperate % targetBase).toString())
            numberToOperate /= targetBase
        } else {
            for (key in letters.keys) {
                if ((numberToOperate % targetBase).toInt() == letters[key]) {
                    convertedNum.add(key.toString())
                    numberToOperate /= targetBase
                }
            }
        }
    }
    return convertedNum.joinToString("").reversed()
}
fun convertDecimalPart(partDecimalRecived : MutableList<Char>, targetBase: BigInteger, sourceBase : BigInteger) : String {
    var numberReturn = BigDecimal.ZERO
    var pow = 1
    for ( i in partDecimalRecived) {
        if (i.isLetter() && i in letters.keys) {
            numberReturn += ((letters.getValue(i).toDouble()) / (sourceBase.toDouble().pow(pow))).toBigDecimal()
            pow++
        } else {
            numberReturn += ((i.digitToInt().toDouble()) / (sourceBase.toDouble().pow(pow))).toBigDecimal()
            pow++
        }
    }
    val retNumDec = mutableListOf<String>()
    var numberOperation = ((numberReturn * targetBase.toBigDecimal()).setScale(5,RoundingMode.CEILING)).toString().split(".").toMutableList()
    if (numberOperation[0].toInt() <= 9){
        retNumDec.add(numberOperation[0])
    } else {
        for (key in letters.keys) {
            if ((numberOperation[0]).toInt() == letters[key]) {
                retNumDec.add(key.toString())
            }
        }
    }
    numberOperation[0] = "0"
    repeat(4) {
        numberOperation = (numberOperation.joinToString(".").toBigDecimal() * targetBase.toBigDecimal()).toString()
            .split(".") as MutableList<String>
        if (numberOperation[0].toInt() <= 9){
            retNumDec.add(numberOperation[0])
        } else {
            for (key in letters.keys) {
                if ((numberOperation[0]).toInt() == letters[key]) {
                    retNumDec.add(key.toString())
                }
            }
        }
        numberOperation[0] = "0"
    }
    return retNumDec.joinToString("")
}

