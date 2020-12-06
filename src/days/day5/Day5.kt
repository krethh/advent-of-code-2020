package days.day5

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

object Day5 {
    @JvmStatic
    @Throws(IOException::class)
    fun solve(input: Path?) {
        val lines = Files.readAllLines(input)

        val boardingPasses = lines.map { BoardingPass(it) }
        val highestId = boardingPasses.map { it.getSeatId() }.max()

        println(highestId)

        val sortedIds = boardingPasses
            .map { it.getSeatId() }
            .sorted()
            .toList()

        for (i in 0..sortedIds.size - 2) {
            if (sortedIds[i + 1] - sortedIds[i] == 2) {
                println("${sortedIds[i]}, ${sortedIds[i + 1]}")
            }
        }
    }

}