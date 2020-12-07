package days.day7

class Bag(val code: String = "", var number: Int = 1) {

    private var name: String? = null

    fun getName(): String = name ?: code.split("\\s+".toRegex()).subList(0, 2)
        .joinToString(" ")

    fun getChildren(): List<Bag> {
        if (code.contains("no other bags")) {
            return listOf()
        }

        val parts = code.split("\\s+".toRegex())
        val chunkedParts = parts.subList(4, parts.size).chunked(4)
        return chunkedParts.map { part ->
            val bag = Bag()
            bag.name = "${part[1]} ${part[2]}"
            bag.number = part[0].toInt()

            bag
        }
    }
}