// Second attempt skipped, could not find any Kotlin solutions(reddit main thread), the git links were all outdated/dead links.
// All solutions didn't provide anything exciting for the A part, as the real coding problem lies in the B part where you have
// to write an efficient algorithm, but if i were to adjust the A part for that then...
// I would restructure the storage to be map with key as current day interval of the fishes(total 9 keys(Day 0-8))
// And value as the amount of fishes with that day so there is only ever 9 elements.

fun main() {

    println("Add new lantern with what cycle?")
    println("Send empty string when finished.")
    var listLanterns = mutableListOf<Lantern>();

    var stopAddingLanterns : Boolean = false
    while(!stopAddingLanterns) {
        val userInput : String = readln()

        if(userInput.isEmpty())
            stopAddingLanterns = true
        else
        {
            if(isNumeric(userInput)) {
                listLanterns.add(Lantern(userInput.toInt()))
                println("Added new lantern with cycle: $userInput")
            }
            else
                println("Incorrect input, try again.")
        }
    }
    //val reader = BufferedReader(InputStream.reader())

    println("You initialized a list of following lanterns: ")
    for(lantern in listLanterns)
    {
        println(lantern.Cycle)
    }

    println("How many cycles forward do you want to go?")
    var inputCycles = readln()

    if(isNumeric(inputCycles))
    {
        var inputCyclesInt = inputCycles.toInt()
        while(inputCyclesInt > 0)
        {
            var addLanternsAmount = 0
            for(lantern in listLanterns) {
                if(lantern.Cycle > 0)
                    lantern.Cycle--
                else {
                    lantern.Cycle = 6
                    addLanternsAmount++
                }
            }

            if(addLanternsAmount > 0) {
                for (i in 1..addLanternsAmount) {
                    listLanterns.add(Lantern(8))
                }
            }
            inputCyclesInt--
        }

        println("After cycles, lanterns now has a total amount of: " + listLanterns.size)
        for(lantern in listLanterns)
        {
            print(lantern.Cycle.toString() + ", ")
        }
    }

}