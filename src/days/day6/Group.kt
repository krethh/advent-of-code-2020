package days.day6

class Group(var answers: List<Answer>) {

    fun getCountOfDistinctAnswers(): Int {
        return answers.flatMap { it.questions }
            .distinct()
            .size
    }

    fun getCountOfEveryoneYesAnswers(): Int {
        val allAnswersPool = answers.flatMap { it.questions }.toList()
        val answerToCount = allAnswersPool.groupingBy { it }.eachCount()

        return answerToCount
            .filter { it.value == answers.size }
            .size
    }
}