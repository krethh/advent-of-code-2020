package days.day12

data class Direction(val letter: String, val value: Int) {
    fun isMovingFirst(): Boolean = listOf("F", "N", "S", "E", "W").contains(letter)

    fun isRotation(): Boolean = listOf("R", "L").contains(letter)

    fun isMoveWaypoint(): Boolean = listOf("N", "W", "S", "E").contains(letter)

    fun isMoveShip(): Boolean = listOf("F").contains(letter)
}