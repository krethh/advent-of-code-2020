package days.day2

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

object Day2 {
    @JvmStatic
    @Throws(IOException::class)
    fun solve(input: Path?) {
        val passwords = Files.readAllLines(input).map { Password(it) }

        println(passwords.filter { it.isValidPartOne() }.size)
        println(passwords.filter { it.isValidPartTwo() }.size)
    }
}