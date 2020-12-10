package days.day10

import java.io.IOException
import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.Path

object Day10 {

    @JvmStatic
    @Throws(IOException::class)
    fun solve(input: Path?) {
        val joltages = Files.readAllLines(input).map { it.toInt() }.sorted().toMutableList()
        joltages.add(0, 0)

        val myAdapter = joltages.max()!! + 3

        var differences = mutableMapOf(1 to 0, 2 to 0, 3 to 0)

        for (i in 0..joltages.size - 2) {
            val difference = joltages[i + 1] - joltages[i]
            differences[difference] = differences[difference]!! + 1
        }

        differences[3] = differences[3]!! + 1

        println(differences[3]!! * differences[1]!!)
        println(differences)
        println(findPaths(joltages, joltages[0], joltages.max()!!))
    }

    fun findPaths(joltages: List<Int>, fromNode: Int, end: Int): BigInteger {
        if (fromNode == end) {
            return BigInteger.ONE
        } else {
            val eligibleNextNodes = joltages.filter { it - fromNode in 1..3 }.toList()

            return eligibleNextNodes.map { eligibleNextNode ->
                val joltagesMinusThisNodeAndSmaller = joltages.filter { it > eligibleNextNode }.toList()
                findPaths(joltagesMinusThisNodeAndSmaller, eligibleNextNode, end)
            }.reduce(BigInteger::add)
        }
    }
}