package days.day9

import java.io.IOException
import java.math.BigDecimal
import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.Path

object Day9 {
    @JvmStatic
    @Throws(IOException::class)
    fun solve(input: Path?) {
        val numbers = Files.readAllLines(input).map { it.toBigDecimal() }

        val preamble = 25
        var theNumber = BigDecimal.ZERO

        for (i in preamble..numbers.size - 1) {
            val checkedNumber = numbers[i]
            val differences = mutableListOf<BigDecimal>()

            for (j in i-preamble..i) {
                differences.add(numbers[i].subtract(numbers[j]))
            }

            if (numbers.subList(i - preamble, i).minus(differences).size == preamble) {
                println(numbers[i])
                theNumber = numbers[i]
            }
        }

        for (i in numbers.indices) {
            var index = i
            var sum = BigDecimal.ZERO
            var range = mutableListOf<BigDecimal>()

            while (sum < theNumber) {
                sum += numbers[index]
                range.add(numbers[index])
                index++
                if (sum == theNumber) {
                    println(range.min()?.add(range.max()))
                }
                if (sum > theNumber) {
                    continue
                }
            }
        }
    }
}