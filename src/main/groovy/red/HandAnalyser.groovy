package red

import red.domain.Card
import red.domain.Colour

/**
 * Created by willpitt on 22/04/2017.
 */
class HandAnalyser {

    static Integer compareHands(List hands, Colour rule) {
        Map rules = [(Colour.R): redRule, (Colour.O): orangeRule, (Colour.Y): yellowRule, (Colour.G): greenRule, (Colour.B): blueRule, (Colour.I): indigoRule, (Colour.V): violetRule]

        List allCards = hands.flatten()
        if (allCards.size() != (allCards as Set).size()) {
            throw new RuntimeException('duplicate cards')
        }

        return rules[rule](hands)
    }

    static Closure redRule = { List hands ->
        return getHandsAsMap(sortHands(hands)).max{ it.value[0] }.key
    }

    static Closure orangeRule = { List hands ->
        List sameNumberHands = hands.collect{findLargestGroupWithSameNumber(it)}
        return winningHandIndex(sameNumberHands)
    }

    static List findLargestGroupWithSameNumber(List hand) {
        return hand.groupBy{Card card -> card.number}
                .max{group -> group.key + group.value.size()*10}.value
    }

    static Closure yellowRule = { List hands ->
        List sameColourHands = hands.collect{findLargestGroupWithSameColour(it)}
        return winningHandIndex(sameColourHands)
    }

    static List findLargestGroupWithSameColour(List hand) {
        return hand.groupBy{Card card -> card.colour.rank}
                .max{group -> group.key + group.value.size()*10}.value
    }

    static Closure greenRule = { List hands ->
        List evenHands = hands.collect{findEvenCards(it)}
        return evenHands.flatten().size() == 0 ? -1 : winningHandIndex(evenHands)
    }

    static List findEvenCards(List hand) {
        return hand.findAll{Card card -> card.number%2 == 0}
    }

    static Closure blueRule = { List hands ->
        List rainbowHands = sortHands(hands).collect{findCardsOfEachColour(it)}
        return winningHandIndex(rainbowHands)
    }

    static List findCardsOfEachColour(List hand) {
        return hand.inject([]) {result, toAdd ->
            result + (result.every{card -> card.colour != toAdd.colour} ? [toAdd] : [])
        }
    }

    static Closure indigoRule = { List hands ->
        List sequentialHands = sortHands(hands).collect{findSequentialCards(it)}
        return winningHandIndex(sequentialHands)
    }

    //not 100% sure
    static List findSequentialCards(List hand) {
        List groups = [[]]
        Integer lookingFor = 7
        hand.each { card ->
            if (card.number == lookingFor) {
                groups.last().add(card)
                lookingFor--
            } else if(card.number < lookingFor) {
                while(card.number <= lookingFor) {
                    lookingFor--
                }
                groups.add([card])
            }
        }
        return groups.max{it.size()}
    }

    static Closure violetRule = { List hands ->
        List lowNumberHands = hands.collect{findLowNumber(it)}
        return lowNumberHands.flatten().size() == 0 ? -1 : winningHandIndex(lowNumberHands)
    }

    static List findLowNumber(List hand) {
        return hand.findAll{Card card -> card.number < 4}
    }

    static List sortHands(List hands) {
        return hands.collect{ hand -> hand.sort().reverse() }
    }

    static Integer winningHandIndex(List hands) {
        Integer maxSize = hands.max{it.size()}.size()
        hands = hands.collect{ it.size() == maxSize ? it : []}
        return getHandsAsMap(sortHands(hands)).max{ it.value[0] }.key
    }

    static Map getHandsAsMap(List hands) {
        Map result = [:]
        hands.eachWithIndex{ hand, int i ->
            result[i] = hand
        }
        return result
    }
}
