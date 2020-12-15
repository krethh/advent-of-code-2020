package days.day15

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

object Day15 {
    @JvmStatic
    @Throws(IOException::class)
    fun solve(input: Path?) {
        val numbers = Files.readAllLines(input)[0].split(",").map { it.toInt() }.toList()

        var lastIndexMap = mutableMapOf<Int, Int>()
        var penultimateIndexMap = mutableMapOf<Int, Int>()
        var lastNumberSpoken = numbers.last()

        for (i in numbers.indices) {
            lastIndexMap[numbers[i]] = i + 1
        }

        val nthNumber = 30000000

        for (i in numbers.size + 1 until nthNumber + 1) {
            if (!(lastIndexMap.containsKey(lastNumberSpoken) && penultimateIndexMap.containsKey(lastNumberSpoken))) {
                say(lastIndexMap, penultimateIndexMap, 0, i)
                lastNumberSpoken = 0
                continue
            }

            val lastTime = lastIndexMap[lastNumberSpoken]!!
            val penultimateTime = penultimateIndexMap[lastNumberSpoken]!!

            lastNumberSpoken = lastTime - penultimateTime
            say(lastIndexMap, penultimateIndexMap, lastNumberSpoken, i)
        }

        println(lastNumberSpoken)
    }

    private fun say(lastIndexMap: MutableMap<Int, Int>, penultimateIndexMap: MutableMap<Int, Int>, number: Int, n: Int) {
        if (lastIndexMap.containsKey(number)) {
            penultimateIndexMap[number] = lastIndexMap[number]!!
        }
        lastIndexMap[number] = n
    }
}