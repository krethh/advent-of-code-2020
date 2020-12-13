package days.day13

import java.io.IOException
import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.Path

object Day13 {
    @JvmStatic
    @Throws(IOException::class)
    fun solve(input: Path?) {
        val lines = Files.readAllLines(input)

        val estimate = lines[0].toInt()
        val entries = lines[1].split(",").toList()
        val buses = lines[1].split(",").filter { it != "x" }.map { it.toInt() }.toList()

        println(buses)

        val departures = mutableMapOf<Int, MutableList<Int>>()

        buses.forEach { bus ->
            departures[bus] = mutableListOf()
            var last = bus
            while (last < estimate) {
                last += bus
                departures[bus]?.add(last)
            }
        }

        val n = mutableListOf<BigInteger>()
        val a = mutableListOf<BigInteger>()
        entries.filter { it != "x" }.forEach { entry ->
            val index = entries.indexOf(entry)
            a.add(entry.toBigInteger() - index.toBigInteger())
            n.add(entry.toBigInteger())
        }

        // Chinese Remainder Theorem implementation based on Rosetta Stone
        println(calculateChineseRemainder(n, a))
    }

    fun multiplicativeInvers(a: BigInteger, b: BigInteger): BigInteger {
        if (b == BigInteger.ONE) return BigInteger.ONE
        var aa = a
        var bb = b
        var x0 = BigInteger.ZERO
        var x1 = BigInteger.ONE
        while (aa > BigInteger.ONE) {
            val q = aa / bb
            var t = bb
            bb = aa % bb
            aa = t
            t = x0
            x0 = x1 - q * x0
            x1 = t
        }
        if (x1 < BigInteger.ZERO) {
            x1 += b
        }
        return x1
    }

    fun calculateChineseRemainder(n: List<BigInteger>, a: List<BigInteger>): BigInteger {
        val prod = n.reduceRight(BigInteger::multiply)
        var sum = BigInteger.ZERO
        for (i in n.indices) {
            val p = prod / n[i]
            sum += (a[i] * multiplicativeInvers(p, n[i]) * p)
        }
        return sum % prod
    }

}