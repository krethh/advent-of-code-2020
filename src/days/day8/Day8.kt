package days.day8

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

object Day8 {
    @JvmStatic
    @Throws(IOException::class)
    fun solve(input: Path?) {
        val lines = Files.readAllLines(input)
        val instructions = lines.map { Instruction(it) }

        var accumulator = 0
        var index = 0
        var visited = mutableMapOf<Instruction, Boolean>()

        while (true) {
            val instruction = instructions[index]
            if (visited.containsKey(instruction) && visited[instruction]!!) {
                break
            }
            if (instruction.getType() == "nop") {
                index++
            } else if (instruction.getType() == "acc") {
                index++
                accumulator += instruction.getValue()
            } else {
                index += instruction.getValue()
            }
            visited[instruction] = true
        }

        println(accumulator)

        instructions.forEach { instruction ->
            accumulator = 0
            index = 0
            visited = mutableMapOf()

            if (instruction.getType() == "acc") {
                return@forEach
            }

            if (instruction.getType() == "nop") {
                instruction.code = instruction.code.replace("nop", "jmp")
            } else {
                instruction.code = instruction.code.replace("jmp", "nop")
            }

            while (true) {
                val innerInstruction = instructions[index]
                if (visited.containsKey(innerInstruction) && visited[innerInstruction]!!) {
                    break
                }
                if (innerInstruction.getType() == "nop") {
                    index++
                } else if (innerInstruction.getType() == "acc") {
                    index++
                    accumulator += innerInstruction.getValue()
                } else {
                    index += innerInstruction.getValue()
                }
                if (index == instructions.size) {
                    println("terminated")
                    println(accumulator)
                    break
                }
                visited[innerInstruction] = true
            }

            if (instruction.getType() == "nop") {
                instruction.code = instruction.code.replace("nop", "jmp")
            } else {
                instruction.code = instruction.code.replace("jmp", "nop")
            }

        }
    }

}