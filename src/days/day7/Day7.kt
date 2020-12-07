package days.day7

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

object Day7 {

    var results = mutableMapOf<String, Boolean>()

    @JvmStatic
    @Throws(IOException::class)
    fun solve(input: Path?) {
        val lines = Files.readAllLines(input)

        val bags = lines.map { Bag(it) }
        val bagMap = bags.groupBy { it.getName() }.mapValues { it.value[0] }
        bags.forEach { results[it.getName()] = false }
        bags.forEach { hasChild(it, it, bagMap, "shiny gold") }
        println(results.values.filter { it }.size)
        println(howManyBags(bagMap["shiny gold"]!!, bagMap))
    }

    private fun hasChild(firstParent: Bag, bag: Bag, bags: Map<String, Bag>, keyword: String) {
        if (bag.getChildren().map { it.getName() }.contains(keyword)) {
            results[firstParent.getName()] = true
        }

        bag.getChildren().forEach { child ->
            val innerChild = bags[child.getName()]
            hasChild(firstParent, innerChild!!, bags, keyword)
        }
    }

    private fun howManyBags(bag: Bag, bags: Map<String, Bag>): Int {
        return if (bag.getChildren().isEmpty()) {
            0
        } else {
            var sum = 0

            bag.getChildren()
                .forEach {
                    sum += (it.number + it.number*howManyBags(bags[it.getName()]!!, bags))
                }

            sum
        }
    }
}