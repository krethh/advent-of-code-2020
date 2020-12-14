package days.day14

import java.io.IOException
import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.Path

object Day14 {
    @JvmStatic
    @Throws(IOException::class)
    fun solve(input: Path?) {
        val lines = Files.readAllLines(input)
        var inputUnits = mutableListOf<InputUnit>()

        var currentInputUnit = mutableListOf<String>()
        for (line in lines) {
            if (line.startsWith("mask")) {
                if (currentInputUnit.size > 0) {
                    inputUnits.add(InputUnit(currentInputUnit))
                    currentInputUnit = mutableListOf()
                }
            }
            currentInputUnit.add(line)
        }
        inputUnits.add(InputUnit(currentInputUnit))

        var memory = mutableMapOf<BigInteger, BigInteger>()

        inputUnits.forEach { inputUnit ->
            val mask = inputUnit.getMask()

            inputUnit.getWrites().forEach { write ->
                val asBinary = write.value.toString(2).padStart(36, '0')
                val masked = applyMask(asBinary, mask)

                memory[write.address] = BigInteger(masked, 2)
            }
        }

        println (memory.values.map { it.toLong() }.sum())

        // part 2
        memory = mutableMapOf()

        inputUnits.forEach { inputUnit ->
            val mask = inputUnit.getMask()

            inputUnit.getWrites().forEach { write ->
                val asBinary = write.address.toString(2).padStart(36, '0')
                val masked = applyMask2(asBinary, mask)

                getAllAdresses(masked).forEach { address ->
                    val intAddress = BigInteger(address, 2)
                    memory[intAddress] = write.value
                }
            }
        }


        println (memory.values.map { it.toLong() }.sum())
    }

    private fun applyMask(value: String, mask: String): String {
        var result = ""

        for (i in value.indices) {
            result += if (mask[i] == 'X') {
                value[i]
            } else {
                mask[i]
            }
        }

        return result
    }

    private fun applyMask2(value: String, mask: String): String {
        var result = ""

        for (i in value.indices) {
            result += if (mask[i] == '0') {
                value[i]
            } else {
                mask[i]
            }
        }

        return result
    }

    private fun getAllAdresses(mask: String): List<String> {
        var resultList = mutableListOf<String>()
        getAllAddressesUtil(mask, mask, "", resultList)
        return resultList
    }

    private fun getAllAddressesUtil(mask: String, remaining: String, current: String, resultList: MutableList<String>) {
        if (remaining.length == 0) {
            resultList.add(current)
        } else {
            val currentChar = mask[mask.length - remaining.length]
            if (currentChar == 'X') {
                getAllAddressesUtil(mask, remaining.substring(1), current + '0', resultList)
                getAllAddressesUtil(mask, remaining.substring(1), current + '1', resultList)
            }
            else {
                getAllAddressesUtil(mask, remaining.substring(1), current + currentChar, resultList)
            }
        }
    }

}