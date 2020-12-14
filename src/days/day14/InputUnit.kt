package days.day14

import java.math.BigInteger
import java.util.regex.Pattern

class InputUnit(private val lines: List<String>) {

    fun getMask(): String {
        return lines.first { it.startsWith("mask") }.split("=")[1].trim()
    }

    fun getWrites(): List<Write> {
        return lines.filter { it.startsWith("mem") }.map { line ->
            val matcher = Pattern.compile("\\[(.*?)\\]").matcher(line)
            matcher.find()
            val address = matcher.group(1).toBigInteger()

            val value = line.split("=")[1].trim().toBigInteger()

            Write(address, value)
        }.toList()
    }
}

data class Write (val address: BigInteger, val value: BigInteger)