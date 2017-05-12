package red.domain

import red.Game
import red.HandAnalyser

/**
 * Created by willpitt on 26/04/2017.
 */

Game game = new Game()
List<Hand> hands = game.createHands()
Colour currentRule = Colour.R
Integer currentIndex = game.nextPlayer(HandAnalyser.compareHands(hands.palette, currentRule))

println hands[currentIndex].palette.toString() + " - palette"
println hands[currentIndex].inHand.toString() + " - hand"

Hand currentHand = hands[currentIndex]

List<Card> cardsWhichChangeRule = currentHand.inHand.findAll{ Card card -> card.colour != currentRule }

List<Card> cardsWhichCanWinOnOwn = currentHand.inHand.findAll{ Card card ->
    List newPalette = currentHand.palette.clone() + card
    List newPalettes = hands.palette.clone()
    newPalettes[currentIndex] = newPalette
    HandAnalyser.compareHands(newPalettes, currentRule) == currentIndex
}

List<Card> ruleChangeWins = cardsWhichChangeRule.findAll { Card ruleChangeCard ->
    List newPalettes = hands.palette.clone()
    HandAnalyser.compareHands(newPalettes, ruleChangeCard.colour) == currentIndex
}

List combos = cardsWhichChangeRule.collect{ Card ruleChangeCard ->
    List successes = currentHand.inHand.findAll{ it != ruleChangeCard}.findAll{ Card card ->
        List newPalette = currentHand.palette.clone() + card
        List newPalettes = hands.palette.clone()
        newPalettes[currentIndex] = newPalette
        HandAnalyser.compareHands(newPalettes, ruleChangeCard.colour) == currentIndex
    }
    return [ruleCard: ruleChangeCard, otherCard: successes]
}

println cardsWhichCanWinOnOwn.toString() + ' - win on own'
println ruleChangeWins.toString() + ' - win as rule change'
combos.each {
    println it
}