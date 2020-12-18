package days.day18

import java.io.IOException
import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.Path

object Day18 {
    @JvmStatic
    @Throws(IOException::class)
    fun solve(input: Path?) {
        val lines = Files.readAllLines(input)

        val result = lines.map { evaluate(it) }.toList()

        println(result.sumOf { it })
    }

    private fun evaluate(expression: String): BigInteger = evaluateExpression(evaluateParentheses(expression)).toBigInteger()

    private fun evaluateParentheses(expression: String): String {
        val regex = "\\(([0-9]|\\*|\\+|\\s)+\\)".toRegex()

        if (!regex.containsMatchIn(expression)) {
            return expression
        }

        val firstExpression = regex.find(expression)!!.value
        val firstExpressionResult = evaluateExpression(firstExpression.replace("(", "").replace(")", ""))

        return evaluateParentheses(expression.replaceFirst(firstExpression, firstExpressionResult))
    }

    private fun evaluateExpression(expression: String): String {
        val regex = "[0-9]+\\s*([*+])\\s*[0-9]+".toRegex()

        if (!regex.containsMatchIn(expression)) {
            return expression
        }

        // for part 1 comment out the following block
        val additionsRegex = "[0-9]+\\s*\\+\\s*[0-9]+".toRegex()
        if (additionsRegex.containsMatchIn(expression)) {
            val firstExpression = additionsRegex.find(expression)!!.value
            val firstExpressionResult = addOrMultiply(firstExpression).toString()

            return evaluateExpression(expression.replaceFirst(firstExpression, firstExpressionResult))
        }

        val firstExpression = regex.find(expression)!!.value
        val firstExpressionResult = addOrMultiply(firstExpression).toString()

        return evaluateExpression(expression.replaceFirst(firstExpression, firstExpressionResult))
    }

    private fun addOrMultiply(trivialExpression: String): BigInteger {
        val parts = trivialExpression.split("([*+])".toRegex())
        val first = parts[0].trim().toBigInteger()
        val second = parts[1].trim().toBigInteger()

        return if (trivialExpression.contains("*")) {
            first * second
        } else {
            first + second
        }
    }
}