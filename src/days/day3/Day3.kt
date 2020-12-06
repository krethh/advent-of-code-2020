package days.day3

import java.io.IOException
import java.math.BigDecimal
import java.nio.file.Files
import java.nio.file.Path

object Day3 {
    @JvmStatic
    @Throws(IOException::class)
    fun solve(input: Path?) {
        val lines = Files.readAllLines(input)

        val positionsOfTrees = lines
            .map { it.chunked(1) }
            .map { list ->
                list.map { it == "#" }.toList()
            }

        val rights = listOf(1, 3, 5, 7, 1)
        val downs = listOf(1, 1, 1, 1, 2)

        var product: BigDecimal = BigDecimal.ONE
        for (i in rights.indices) {
            var treesHit = 0
            var x = 0
            var y = 0

            do {
                x += rights[i]
                y += downs[i]

                if (positionsOfTrees[y][x % positionsOfTrees[0].size]) {
                    treesHit++
                }
            } while (y < positionsOfTrees.size - 1)

            product = product.multiply(treesHit.toBigDecimal())
            println(treesHit)
        }

        println(product)
    }
}