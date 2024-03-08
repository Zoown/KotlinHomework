//Source inspiration - https://github.com/Eric-Lloyd/advent-of-code-kotlin/blob/main/src/day11/Day11.kt

import java.io.File

fun getNeighbours2Dv2(matrix: List<List<Octopus>>, rowIndex : Int, columnIndex : Int): List<Pair<Int, Int>> {
    val totalRows = matrix.size
    val totalColumns = matrix[0].size
    val j = rowIndex
    val i = columnIndex


    val top = if (j > 0) Pair(j - 1, i) else null
    val left = if (i > 0) Pair(j, i - 1) else null
    val right = if (i < totalColumns - 1) Pair(j, i + 1) else null
    val bottom = if (j < totalRows - 1) Pair(j + 1, i) else null
    val topLeft = if (j > 0 && i > 0) Pair(j - 1, i - 1) else null
    val topRight = if (j > 0 && i < totalColumns - 1) Pair(j - 1, i + 1) else null
    val bottomLeft = if (j < totalRows - 1 && i > 0) Pair(j + 1, i - 1) else null
    val bottomRight = if (j < totalRows - 1 && i < totalColumns - 1) Pair(j + 1, i + 1) else null

    return listOf(top, left, right, bottom, topLeft, topRight, bottomLeft, bottomRight).mapNotNull { it }
}

fun flash2D(listOctopuses: MutableList<List<Octopus>>, rowIndex: Int, columnIndex: Int) {

    if(listOctopuses[rowIndex][columnIndex].Cycle > maxAllowedEnergy && !listOctopuses[rowIndex][columnIndex].flashed) {

        listOctopuses[rowIndex][columnIndex].flashed = true
        totalFlashes++

        //var testString1 = "FlashIndex: [$rowIndex][$columnIndex]| NeighborIndices: "
        for (neighbourOctopusIndices in getNeighbours2Dv2(listOctopuses, rowIndex, columnIndex)) {
           // testString1 += "$listOctopuses.get, "

            val neighbour = listOctopuses[neighbourOctopusIndices.first][neighbourOctopusIndices.second]
            if (neighbour != null && !neighbour.flashed) {
                neighbour.Cycle++

                if(neighbour.Cycle > maxAllowedEnergy)
                    flash2D(listOctopuses, neighbourOctopusIndices.first, neighbourOctopusIndices.second)
            }
        }
        //println(testString1)

        listOctopuses[rowIndex][columnIndex].Cycle = 0
    }
}

var octopusTest2 = ""
fun printOctopusGrid2D(listOctopuses : MutableList<List<Octopus>>) {
    for(octopusRow in listOctopuses) {
        for(octopus in octopusRow) {
            print(octopus.Cycle)
        }
        println()
    }

    //println("OctopusList : ")
    //println(octopusTest)
}

fun main() {

    //Create a List of List<Int> (Each line in file stores as a List<Int> and put in a List)
    //No proper error checking if data in file is actually valid
    val listOfOctopusRows : MutableList<List<Octopus>> = File("src", "testdata/Day11.txt").readLines()
        .map { line -> line.toList().map { charDigit -> Octopus(charDigit.digitToInt(), false) } }.toMutableList()

    if(listOfOctopusRows.size != 10)
        println("You have incorrect row count.")

    for(octopusRow in listOfOctopusRows) {
        if (octopusRow.size != 10) {
            println("You have a row with incorrect column count.")
        }
    }


    //Print visual grid of initial state
    printOctopusGrid2D(listOfOctopusRows)

    //Cycle forward
    println("How many cycles forward do you want to go?")
    val inputCycles = readln()

    if(isNumeric(inputCycles))
    {
        var inputCyclesInt = inputCycles.toInt()
        while(inputCyclesInt > 0)
        {
            //Reset flash-state
            for(octopusRow in listOfOctopusRows) {
                for(octopus in octopusRow) {
                    octopus.flashed = false
                }
            }


            for(octopusRow in listOfOctopusRows) {
                for(octopus in octopusRow) {
                    if(!octopus.flashed)
                        octopus.Cycle++

                    flash2D(listOfOctopusRows, listOfOctopusRows.indexOf(octopusRow), octopusRow.indexOf(octopus))
                }
            }

            inputCyclesInt--
        }

    }

    println("After cycles, grid now looks like:")
    printOctopusGrid2D(listOfOctopusRows)
    println("Total flashes: $totalFlashes")

}