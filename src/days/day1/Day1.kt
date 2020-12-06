package days.day1

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

object Day1 {
    @JvmStatic
    @Throws(IOException::class)
    fun solve(input: Path?) {
        val items = Files.readAllLines(input).map { it.toInt() }

        items.forEach { item ->
            val difference = 2020 - item
            if (items.contains(difference)) {
                println (item * difference)
            }
        }

        var sumOfAllPairsMap = mutableMapOf<Pair<Int, Int>, Int>()

        for (i in items.indices) {
            for (j in items.indices) {
                if (i != j) {
                    sumOfAllPairsMap.put(Pair(items[i], items[j]), items[i] + items[j])
                }
            }
        }

        items.forEach { item ->
            val difference = 2020 - item
            if (sumOfAllPairsMap.values.contains(difference)) {
                println(item)
            }
        }
    }
}