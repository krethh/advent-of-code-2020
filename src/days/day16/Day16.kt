package days.day16

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.IntStream
import kotlin.streams.toList

object Day16 {
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

        rangePairs.add(0, Pair(0, rangePairs[0].first))

        val validRanges = lines.subList(rangePairs[0].first, rangePairs[0].second).joinToString(" ")
        val regex = "[0-9]+-[0-9]+".toRegex()
        val ranges = regex.findAll(validRanges).map { it.value }.map {
            val parts = it.split("-")
            parts[0].toInt()..parts[1].toInt()
        }.toList()

        val nearbyTicketValues = lines
            .subList(rangePairs[2].first, rangePairs[2].second)
            .joinToString(",")
            .split(",")
            .mapNotNull { it.toIntOrNull() }
            .toList()

        var errorRate = 0

        nearbyTicketValues.forEach { value ->
            var isInAtLeastOne = false

            ranges.forEach { range ->
                if (value in range) {
                    isInAtLeastOne = true
                }
            }

            if (!isInAtLeastOne) {
                errorRate += value
            }
        }

        println(errorRate)

        val tickets = lines
            .subList(rangePairs[2].first + 2, rangePairs[2].second)
            .map { list -> list.split(",").filter { !it.isEmpty() }.map { elem -> elem.toInt()}.toList() }

        val validTickets = tickets.filter { ticket ->
            var positionsValid = mutableListOf<Boolean>()

            ticket.forEach { value ->
                var isInAtLeastOne = false

                ranges.forEach { range ->
                    if (value in range) {
                        isInAtLeastOne = true
                    }
                }

                if (!isInAtLeastOne) {
                    positionsValid.add(false)
                } else {
                    positionsValid.add(true)
                }
            }

            !positionsValid.contains(false)

        }.toList()

        val fields = lines
            .subList(rangePairs[0].first, rangePairs[0].second)


        var fieldToRanges = mutableMapOf<String, List<IntRange>>()

        fields.forEach { field ->
            val name = field.split(":")[0].trim()
            val ranges = regex.findAll(field).map { it.value }.map {
                val parts = it.split("-")
                parts[0].toInt()..parts[1].toInt()
            }.toList()

            fieldToRanges[name] = ranges
        }

        // sieve

        val indexToValid = mutableMapOf<Pair<String, Int>, Boolean>()

        for (i in validTickets[0].indices) {
            fieldToRanges.keys.forEach { field ->
                var validForAll = true
                val ranges = fieldToRanges[field]

                validTickets.forEach { validTicket ->
                    if (!(validTicket[i] in ranges!![0]) && !(validTicket[i] in ranges!![1])) {
                        validForAll = false
                    }
                }

                indexToValid[Pair(field, i)] = validForAll
            }
        }

        println(indexToValid)

        val ticketSize = validTickets[0].size
        val finalAssociations = mutableListOf<Pair<String, Int>>()
        while (finalAssociations.size < validTickets[0].size) {
            for (i in 0 until ticketSize) {
                var numOfTrue = 0
                var trueKey: String? = null
                for (key in fieldToRanges.keys) {
                    if (indexToValid[Pair(key, i)]!!) {
                        numOfTrue += 1
                        trueKey = key
                    }
                }

                if (numOfTrue == 1) {
                    finalAssociations.add(Pair(trueKey!!, i))
                    for (j in 0 until ticketSize) {
                        indexToValid[Pair(trueKey, j)] = false
                    }
                }
            }
        }

        println (finalAssociations)
    }
}