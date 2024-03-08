import java.io.File

fun main() {
    //Create a List of List<Int> (Each line in file stores as a List<Int> and put in a List)
    //No proper checking if test data is actually valid
    val input : List<List<Int>> = File("src", "testdata/Day11.txt").readLines()
        .map { line -> line.toList().map { digitChar -> digitChar.toString().toInt() } }


    val counts = countFlashesAfterEachStepUntil(input) { step, flashCount -> step >= 100 && flashCount == 100 }

    val part1TotalFlashes : Int = counts
        .filter { (step, _) -> step <= 100 }
        .map { (_, flashCount) -> flashCount }
        .sum()

    println(part1TotalFlashes)

    /*
    val part2 = counts
        .filter { (_, flashCount) -> flashCount == 100 }
        .firstNotNullOf { (step, _) -> step }

     */
    //println(part2)
}

fun countFlashesAfterEachStepUntil(input: List<List<Int>>, predicate: (Int, Int) -> Boolean): Map<Int, Int> {
    val matrix = initialPointMatrix(input)
    var currStep = 1
    var currFlashCount = 0
    val result = mutableMapOf<Int, Int>()

    while (!predicate(currStep, currFlashCount)) {
        val m = matrix.size
        val n = matrix[0].size
        val zeros = mutableListOf<ZeroPoint>()

        // increment all points and find initial zeros
        for (j in 0 until m) {
            for (i in 0 until n) {
                val currentPoint = matrix[j][i]
                when (currentPoint.value) {
                    9 -> {
                        currentPoint.value = 0
                        zeros.add(ZeroPoint(currentPoint))
                    }
                    else -> currentPoint.value += 1
                }
            }
        }

        // handle zeros
        var allZerosHandled = zeros.isEmpty()
        while (!allZerosHandled) {
            val zerosToAdd = mutableListOf<ZeroPoint>()
            zeros.filter { !it.handled }.forEach { zero ->
                zero.point.neighbours(matrix)
                    .filter { neighbour -> neighbour.value != 0 }
                    .forEach { neighbour ->
                        when (neighbour.value) {
                            9 -> {
                                neighbour.value = 0
                                zerosToAdd.add(ZeroPoint(neighbour))
                            }
                            else -> neighbour.value += 1
                        }
                    }
                zero.handled = true
            }
            zeros.addAll(zerosToAdd)
            allZerosHandled = zeros.all { it.handled }
        }
        currFlashCount = zeros.size
        result[currStep] = currFlashCount

        currStep += 1
    }
    return result
}

private fun initialPointMatrix(input: List<List<Int>>): List<List<Point>> {
    val m = input.size
    val n = input[0].size
    val result = mutableListOf<MutableList<Point>>()
    for (j in 0 until m) {
        val row = mutableListOf<Point>()
        for (i in 0 until n) {
            row.add(Point(input[j][i], i, j))
        }
        result.add(row)
    }
    return result
}

class ZeroPoint(val point: Point, var handled: Boolean = false)
data class Point(var value: Int, val i: Int, val j: Int)

fun Point.neighbours(matrix: List<List<Point>>): List<Point> {
    val m = matrix.size
    val n = matrix[0].size
    val i = this.i
    val j = this.j


    val top = if (j > 0) matrix[j - 1][i] else null
    val left = if (i > 0) matrix[j][i - 1] else null
    val right = if (i < n - 1) matrix[j][i + 1] else null
    val bottom = if (j < m - 1) matrix[j + 1][i] else null
    val topLeft = if (j > 0 && i > 0) matrix[j - 1][i - 1] else null
    val topRight = if (j > 0 && i < n - 1) matrix[j - 1][i + 1] else null
    val bottomLeft = if (j < m - 1 && i > 0) matrix[j + 1][i - 1] else null
    val bottomRight = if (j < m - 1 && i < n - 1) matrix[j + 1][i + 1] else null

    return listOf(top, left, right, bottom, topLeft, topRight, bottomLeft, bottomRight).mapNotNull { it }
}