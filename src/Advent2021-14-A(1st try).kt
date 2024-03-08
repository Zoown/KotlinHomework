// This coding problem lies in the B part, it's an algorithm efficiency problem.
// So there isn't any solutions i can adjust my code to out on the reddit main thread as they all solve for B part.
// If i want to do a second attempt, it would be to solve the B part by only changing my algorithm / data structure

import java.io.File

fun calculateNewChar(char1: Char, char2: Char, decodeList: Map<String, Char>) : Char? {
    var keyString : String = char1.toString()+char2

    var mapElement = decodeList.get(keyString)
    if(mapElement != null)
        return mapElement

    return null
}

fun copyCharList(charList: MutableList<Char>) : MutableList<Char> {
    var originalListCopy = mutableListOf<Char>()
    for(character in charList) {
        originalListCopy.add(character)
    }

    return originalListCopy
}

fun main() {

    var inputList : MutableList<Char> = File("src", "testdata/Day14.txt").bufferedReader().readLine().map{ it }.toMutableList()
    var decodeList : Map<String, Char> = File("src", "testdata/Day14decode.txt").readLines()
        .associate { line -> Pair(line[0].toString() + line[1], line[6]) }

    /* Data testing

    for(character in inputList) {
        println(character)
    }

    for(decodePair in decodeList) {
        println(decodePair.key + " = " + decodePair.value)
    }
     */

    var originalListCopy = copyCharList(inputList)


    println("How many steps forward do you want to go?")
    var totalSteps = readln()
    if(isNumeric(totalSteps)) {
        var inputCyclesInt = totalSteps.toInt()
        while (inputCyclesInt > 0) {

            for(i in 0..originalListCopy.size-1) {
                if(i == originalListCopy.size-1) break

                var charToAdd = calculateNewChar(originalListCopy[i], originalListCopy[i+1], decodeList)

                if(charToAdd != null)
                    inputList.add(i*2+1, charToAdd)
            }

            //Copy the newly step-completed list
            originalListCopy = copyCharList(inputList)

            inputCyclesInt--
        }
    }

    println("Finished string: ")
    for(character in inputList) {
        print(character)
    }

}