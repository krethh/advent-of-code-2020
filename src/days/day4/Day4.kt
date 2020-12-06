package days.day4

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.IntStream
import kotlin.streams.toList

object Day4 {
    @JvmStatic
    @Throws(IOException::class)
    fun solve(input: Path?) {
        val lines = Files.readAllLines(input)

        val blankLineIndexes = IntStream
            .range(0, lines.size)
            .filter { lines[it].isBlank() }
            .toList()
            .toMutableList()

        blankLineIndexes.add(lines.size)

        val rangePairs = IntStream
            .range(0, blankLineIndexes.size - 1)
            .mapToObj { Pair(blankLineIndexes[it], blankLineIndexes[it + 1]) }
            .toList()
            .toMutableList()

        rangePairs.add(0, Pair(-1, rangePairs[0].first))

        val passports = rangePairs
            .map{ lines.subList(it.first + 1, it.second)}
            .map{ it.joinToString(" ")}
            .map { Passport(it) }

        println(passports.map { it.isValidPartOne() }.filter { it }.size)
        println(passports.map { it.isValidPartTwo() }.filter { it }.size)
    }

}