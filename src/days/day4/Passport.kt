package days.day4

class Passport(val code: String) {

    private val fieldsWithoutCid = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
    private val fields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid")

    fun isValidPartOne(): Boolean {
        return !fieldsWithoutCid.map { code.contains("$it:") }
            .contains(false)
    }

    fun isValidPartTwo(): Boolean {
        val fields = code.split("\\s+".toRegex()).toList()

        fields.forEach{field ->
            if (field.contains("byr")) {
                val year = field.replace("byr:", "").toInt()

                if (year < 1920 || year > 2002) {
                    return false
                }
            }

            if (field.contains("iyr")) {
                val year = field.replace("iyr:", "").toInt()

                if (year < 2010 || year > 2020) {
                    return false
                }
            }

            if (field.contains("eyr")) {
                val year = field.replace("eyr:", "").toInt()

                if (year < 2020 || year > 2030) {
                    return false
                }
            }

            if (field.contains("hgt")) {
                val height = field.replace("iyr:", "")

                if (!(height.contains("cm") || height.contains("in"))) {
                    return false
                }

                if (height.contains("cm")) {
                    val intHeight = height.replace("cm", "").replace("hgt:", "").toInt()
                    if (intHeight < 150 || intHeight > 193) {
                        return false
                    }
                }
                if (height.contains("in")) {
                    val intHeight = height.replace("in", "").replace("hgt:", "").toInt()
                    if (intHeight < 59 || intHeight > 76) {
                        return false
                    }
                }
            }

            if (field.contains("hcl")) {
                val matches = field.replace("hcl:", "").matches(Regex("#[0-9a-f][0-9a-f][0-9a-f][0-9a-f][0-9a-f][0-9a-f]"))

                if (!matches) {
                    return false
                }
            }

            if (field.contains("ecl")) {
                val matches = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
                    .contains(field.replace("ecl:", ""))

                if (!matches) {
                    return false
                }
            }

            if (field.contains("pid")) {
                val matches = field.replace("pid:", "")
                    .matches(Regex("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]"))

                if (!matches) {
                    return false
                }
            }
        }

        return isValidPartOne()
    }
}