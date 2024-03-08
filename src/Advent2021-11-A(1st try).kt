import kotlin.reflect.KClass
var maxAllowedEnergy : Int = 9
var stage2ExpectedResult : String = "8807476555508908705485978896088485769600870090880066000889896800005943000000745690000008768700006848"
var totalFlashes : Int = 0

fun getNeighbors(myIndex : Int, octopusList : List<Octopus>) : List<Int> {
    var neighborList = mutableListOf<Int>()

    if((myIndex - 1) >= 0) {
        if (myIndex % 10 != 0)
            neighborList.add((myIndex - 1))
    }

    if((myIndex - 9) >= 0) {
        if(myIndex % 10 != 9)
        neighborList.add(myIndex - 9)
    }
    if((myIndex - 10) >= 0)
        neighborList.add(myIndex - 10)

    if((myIndex - 11) >= 0) {
        if (myIndex % 10 != 0)
            neighborList.add(myIndex - 11)
    }

    if(!((myIndex + 1) > octopusList.size-1)) {
        if(myIndex % 10 != 9)
            neighborList.add(myIndex + 1)
    }
    if(!((myIndex + 9) > octopusList.size-1)) {
        if(myIndex % 10 != 0)
            neighborList.add(myIndex + 9)
    }
    if(!((myIndex + 10) > octopusList.size-1))
        neighborList.add(myIndex + 10)
    if(!((myIndex + 11) > octopusList.size-1)) {
        if(myIndex % 10 != 9)
            neighborList.add(myIndex + 11)
    }

    return neighborList
}

fun flash(listOctopuses : List<Octopus>, octopusIndex : Int) {

    if(listOctopuses[octopusIndex].Cycle > maxAllowedEnergy && !listOctopuses[octopusIndex].flashed) {

        listOctopuses[octopusIndex].flashed = true
        totalFlashes++
        //Octopus curOctopus = listOctopuses.getOrNull(octopusIndex-1)
        //var curOctopus : KClass<Octopus> = listOctopuses.getOrNull(octopusIndex-1)
        //var neighborOctopus1 = listOctopuses.getOrNull(octopusIndex - 1)

        var testString1 = "FlashIndex: $octopusIndex| NeighborIndices: "
        for (neighborIndex in getNeighbors(octopusIndex, listOctopuses)) {
            testString1 += "$neighborIndex, "

            val neighbor = listOctopuses.getOrNull(neighborIndex)
            if (neighbor != null && !neighbor.flashed) {
                neighbor.Cycle++

                if(neighbor.Cycle > maxAllowedEnergy)
                    flash(listOctopuses, neighborIndex)
            }
        }
        println(testString1)

        listOctopuses[octopusIndex].Cycle = 0
    }
}

var octopusTest = ""
fun printOctopusGrid(listOctopuses : List<Octopus>) {
    var gridIndex = 1
    var currentGridString = ""
    for(octopus in listOctopuses) {

        if(gridIndex <= 10) {
            currentGridString += octopus.Cycle.toString()
            gridIndex++
        }
        else {
            currentGridString = octopus.Cycle.toString()
            gridIndex = 2
        }

        if(gridIndex == 11)
            println(currentGridString)

        octopusTest  += octopus.Cycle.toString()
    }

    //println("OctopusList : ")
    //println(octopusTest)
}

fun main() {

    //Receive input for initialization and create list
    println("Enter 100 numbers without spaces to initialize octopus grid.")
    var listOctopuses = mutableListOf<Octopus>();
    var requestOctopusInput = true
    var userInput: String = ""
    while(requestOctopusInput) {
        userInput = readln()

        if (userInput.length != 100) {
            println("You entered incorrect amount(" + userInput.length + "), please enter 100 numbers.")
        }
        else if (!isNumeric(userInput)) {
            println("You entered a non-numeric character.")
        }
        else
            requestOctopusInput = false
    }


    // V1 - Populate octopus list
    //for(i in 0..userInput.length-1) {
    //    listOctopuses.add(Octopus(userInput.get(i).digitToInt(), false))
    //}

    // V2 - Populate octopus list (1D list)
    listOctopuses = userInput.map{ charDigit -> Octopus(charDigit.digitToInt(), false)}.toMutableList()


    //Print visual grid of initial state
    printOctopusGrid(listOctopuses)

    //Cycle forward
    println("How many cycles forward do you want to go?")
    val inputCycles = readln()

    if(isNumeric(inputCycles))
    {
        var inputCyclesInt = inputCycles.toInt()
        while(inputCyclesInt > 0)
        {
            for(octopusIndex in 0..listOctopuses.size-1) {
                listOctopuses[octopusIndex].flashed = false
            }

            //println("SIZE: " + listOctopuses.size)
            for(octopusIndex in 0..listOctopuses.size-1) {
                if(!listOctopuses[octopusIndex].flashed)
                    listOctopuses[octopusIndex].Cycle++

                flash(listOctopuses, octopusIndex)
            }

            inputCyclesInt--
        }

    }

    println("After cycles, grid now looks like:")
    printOctopusGrid(listOctopuses)
    println("Total flashes: $totalFlashes")

}