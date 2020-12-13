package days.day12

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

object Day12 {
    @JvmStatic
    @Throws(IOException::class)
    fun solve(input: Path?) {
        val directions = Files.readAllLines(input).map { Direction(it.substring(0..0), it.substring(1).toInt()) }

        var current = "E"
        var x = 0
        var y = 0

        directions.forEach { direction ->
            current = getNextDirectionFirst(current, direction)

            if (direction.isMovingFirst()) {
                val (xMove, yMove) = getDifferenceFirst(current, direction)
                x += xMove
                y += yMove
            }
        }

        println("$x, $y")
        println(kotlin.math.abs(x) + kotlin.math.abs(y))

        x = 0
        y = 0
        var waypointX = 10
        var waypointY = -1

        directions.forEach { direction ->
            if (direction.isRotation()) {
                var (newX, newY) = rotateWaypoint(waypointX, waypointY, direction)

                waypointX = newX
                waypointY = newY
            }

            if (direction.isMoveWaypoint()) {
                var (moveX, moveY) = getDifferenceFirst("", direction)

                waypointX += moveX
                waypointY += moveY
            }

            if (direction.isMoveShip()) {
                for (i in 0 until direction.value) {
                    x += waypointX
                    y += waypointY
                }
            }
        }

        println("$x, $y")
        println(kotlin.math.abs(x) + kotlin.math.abs(y))
    }

    fun rotateWaypoint(x: Int, y: Int, turn: Direction): Pair<Int, Int> {
        val turnUnits = turn.value / 90

        var newX = x
        var newY = y

        if (turn.letter == "R") {
            //rotate clockwise
            for (i in 0 until turnUnits) {
                var nextX = -newY
                var nextY = newX

                newX = nextX
                newY = nextY
            }
        }
        if (turn.letter == "L") {
            //rotate counterclockwise
            for (i in 0 until turnUnits) {
                var nextX = newY
                var nextY = -newX

                newX = nextX
                newY = nextY
            }
        }

        return Pair(newX, newY)
    }



    fun getNextDirectionFirst(current: String, turn: Direction): String {
        val rightTurns = listOf("N", "E", "S", "W")
        val leftTurns = listOf("N", "W", "S", "E")
        val turnUnits = turn.value / 90

        if (turn.letter == "R") {
            val currentIndex = rightTurns.indexOf(current)
            val nextIndex = (currentIndex + turnUnits) % 4
            return rightTurns[nextIndex]
        }
        if (turn.letter == "L") {
            val currentIndex = leftTurns.indexOf(current)
            val nextIndex = (currentIndex + turnUnits) % 4
            return leftTurns[nextIndex]
        }
        return current
    }

    fun getDifferenceFirst(current: String, direction: Direction): Pair<Int, Int> {
        return if (direction.letter == "F") {
            moveFirst(current, direction.value)
        } else {
            moveFirst(direction.letter, direction.value)
        }
    }

    private fun moveFirst(letter: String, value: Int): Pair<Int, Int> {
        if (letter == "N") {
            return Pair(0, - value)
        }
        if (letter == "S") {
            return Pair(0, value)
        }
        if (letter == "E") {
            return Pair(value, 0)
        }
        if (letter == "W") {
            return Pair(- value, 0)
        }
        return Pair(0, 0)
    }
}