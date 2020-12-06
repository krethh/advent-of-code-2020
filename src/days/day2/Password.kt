package days.day2

class Password(val code: String) {

    fun isValidPartOne(): Boolean {
        val parts = code.split("\\s+".toRegex())

        val min = parts[0].split("-")[0].toInt()
        val max = parts[0].split("-")[1].toInt()
        val char = parts[1].replace(":", "")

        val occurences = parts[2]
            .chunked(1)
            .filter { it == char }
            .size

        return occurences in min..max
    }

    fun isValidPartTwo(): Boolean {
        val parts = code.split("\\s+".toRegex())

        val first = parts[0].split("-")[0].toInt()
        val second = parts[0].split("-")[1].toInt()
        val char = parts[1].replace(":", "")

        return (parts[2][first - 1] == char[0]) xor (parts[2][second - 1] == char[0])
    }
}