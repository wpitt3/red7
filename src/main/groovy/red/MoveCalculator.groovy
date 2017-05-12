package red

import red.HandAnalyser
import red.domain.Card
import red.domain.Colour
import red.domain.Hand

/**
 * Created by willpitt on 09/05/2017.
 */
class MoveCalculator {

    Map calculateMoves(List<Hand> hands, Integer currentIndex, Colour currentRule = Colour.R) {
        Hand currentHand = hands[currentIndex]

        List<Card> cardsWhichChangeRule = currentHand.inHand.findAll{ Card card -> card.colour != currentRule }

        List<Card> cardsWhichCanWinOnOwn = findCardsWhichWinOnOwn(hands, currentIndex, currentRule)

        List<Card> ruleChangeWins = cardsWhichChangeRule.findAll { Card ruleChangeCard ->
            List newPalettes = hands.palette.clone()
            HandAnalyser.compareHands(newPalettes, ruleChangeCard.colour) == currentIndex
        }

        List<Map> combos = cardsWhichChangeRule.collect{ Card ruleChangeCard ->
            List successes = currentHand.inHand.findAll{ it != ruleChangeCard}.findAll{ Card card ->
                List newPalette = currentHand.palette.clone() + card
                List newPalettes = hands.palette.clone()
                newPalettes[currentIndex] = newPalette
                HandAnalyser.compareHands(newPalettes, ruleChangeCard.colour) == currentIndex
            }
            return [ruleCard: ruleChangeCard.clone(), otherCard: successes]
        }.findAll{it.otherCard != []}

        return [addToPalette: cardsWhichCanWinOnOwn, ruleChange: ruleChangeWins, twoCard:combos]
    }

    private List<Card> findCardsWhichWinOnOwn(List<Hand> hands, Integer currentIndex, Colour currentRule) {
        Hand currentHand = hands[currentIndex]
        return currentHand.inHand.findAll{ Card card ->
            List newPalettes = hands.palette.clone()
            newPalettes[currentIndex] += card
            HandAnalyser.compareHands(newPalettes, currentRule) == currentIndex
        }
    }

//    private List<Card>
}