package red

import red.domain.Card
import red.domain.Colour

/**
 * Created by willpitt on 22/04/2017.
 */
class HandAnalyser {

    static Integer compareHands(List hands, Colour rule) {
        Map rules = [(Colour.R): redRule, (Colour.O): orangeRule]

        List allCards = hands.flatten()
        if (allCards.size() != (allCards as Set).size()) {
            throw new RuntimeException('duplicate cards')
        }

        return rules[rule](hands)
    }

    static Closure redRule = { List hands ->
        return winningHandFromRule(sortHands(hands), { it.value[0]} )
    }

    static Closure orangeRule = { List hands ->
        List handsNumberGroup = hands.collect{findLargestGroupWithSameNumber(it)}
        Integer maxSize = handsNumberGroup.max{it.size()}.size()
        handsNumberGroup = handsNumberGroup.collect{ it.size() == maxSize ? it : []}
        return winningHandFromRule(sortHands(handsNumberGroup), { it.value[0] })
    }

    /**
     * returns largest group (higher number group if draw)
     */
    static List findLargestGroupWithSameNumber(List hand) {
        return hand.groupBy{Card card -> card.number}
                .max{group -> group.key + group.value.size()*10}.value
    }

    static List sortHands(List hands) {
        return hands.collect{ hand -> hand.sort().reverse() }
    }

    static Integer winningHandFromRule(List hands, Closure rule) {
        return getHandsAsMap(hands).max{ rule(it) }.key
    }

    static Map getHandsAsMap(List hands) {
        return hands.withIndex().collectEntries { hand, i -> [(i):hand] }
    }
}
