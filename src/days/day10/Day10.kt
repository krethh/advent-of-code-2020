package days.day10

import java.io.IOException
import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.Path

object Day10 {

    var jumps = mutableListOf<Pair<Int, Int>>()
    lateinit var joltages: MutableList<Int>

    @JvmStatic
    @Throws(IOException::class)
    fun solve(input: Path?) {
        joltages = Files.readAllLines(input).map { it.toInt() }.sorted().toMutableList()
        joltages.add(0, 0)

        findPairs(joltages)
        countPaths(0, joltages.max()!!, 300)
    }

    fun findPairs(joltages: List<Int>) {
       for (i in joltages.indices) {
           val eligibleNextNodes = joltages.filter { it - joltages[i] in 1..3 }.toList()
           eligibleNextNodes.forEach { node ->
               jumps.add(Pair(joltages[i], node))
           }
       }
    }

    fun countPaths(sourceV: Int, targetV: Int, numEdges: Int) {
        var count = mutableMapOf<Triple<Int, Int, Int>, BigInteger>()

        for (e in 0..numEdges) {
            for (source in joltages) {
                for (target in joltages) {
                    count[Triple(source, target, e)] = BigInteger.ZERO

                    if (e == 0 && source == target) {
                        count[Triple(source, target, e)] = BigInteger.ONE
                    }
                    if (e == 1 && jumps.find { it.first == source && it.second == target } != null) {
                        count[Triple(source, target, e)] = BigInteger.ONE
                    }

                    if (e > 1) {
                        for (a in joltages) {
                            if (jumps.find { it.first == source && it.second == a } != null) {
                                count[Triple(source, target, e)] = count[Triple(source, target, e)]!! + count[Triple(a, target, e - 1)]!!
                            }
                        }
                    }
                }
            }
        }

        var sum: BigInteger = BigInteger.ZERO
        for (i in 0..numEdges) {
            sum += count[Triple(sourceV, targetV, i)]!!
        }
        println(sum)
    }
}