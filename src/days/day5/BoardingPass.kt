package days.day5

class BoardingPass(val code: String) {

    fun getSeatId(): Int {
        return getRow() * 8 + getColumn()
    }

    fun getRow(): Int {
        return getPosition(0, 127, 64, 0..7, 'F')
    }

    fun getColumn(): Int {
        return getPosition(0, 7, 4, 7..9, 'L')
    }

    private fun getPosition(start: Int, end: Int, spacing: Int, range: IntRange, lowerChar: Char): Int {
        var start = start
        var end = end
        var spacing = spacing

        for (i in range) {
            if (code[i] == lowerChar) {
                end -= spacing
                spacing /= 2
            } else {
                start += spacing
                spacing /= 2
            }
        }

        return start
    }
}