package days.day8

class Instruction(var code: String) {

    fun getType(): String = code.split("\\s+".toRegex())[0]

    fun getValue(): Int {
        val number = code.split("\\s+".toRegex())[1]

        return if (number.contains("+")) {
            number.replace("+", "").toInt()
        } else {
            -1 * number.replace("-", "").toInt()
        }
    }
}