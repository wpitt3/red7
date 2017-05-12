package red

import red.domain.Card
import red.domain.Colour
import red.domain.Hand
import spock.lang.Specification

/**
 * Created by willpitt on 22/04/2017.
 */
class MoveCalculatorTest extends Specification {
    MoveCalculator moveCalculator

    void setup() {
        moveCalculator = new MoveCalculator()
    }

    void "No cards, no moves"() {
        given:
        Hand hand1 = new Hand([new Card(Colour.Y, 7)], [])
        Hand hand2 = new Hand([new Card(Colour.O, 7)], [])

        when:
        Map result = moveCalculator.calculateMoves([hand1, hand2], 0, Colour.R)

        then:
        result.addToPalette == []
        result.ruleChange == []
        result.twoCard == []
    }

    void "Add to palette wins it"() {
        given:
        Hand hand1 = new Hand([new Card(Colour.Y, 7)], [new Card(Colour.R, 7)])
        Hand hand2 = new Hand([new Card(Colour.O, 7)], [])

        when:
        Map result = moveCalculator.calculateMoves([hand1, hand2], 0, Colour.R)

        then:
        result.addToPalette == [new Card(Colour.R, 7)]
        result.ruleChange == []
        result.twoCard == []
    }

    void "Rule change wins it"() {
        given:
        Hand hand1 = new Hand([new Card(Colour.Y, 6)], [new Card(Colour.G, 7)])
        Hand hand2 = new Hand([new Card(Colour.O, 7)], [])

        when:
        Map result = moveCalculator.calculateMoves([hand1, hand2], 0, Colour.R)

        then:
        result.addToPalette == []
        result.ruleChange == [new Card(Colour.G, 7)]
        result.twoCard == []
    }

    void "Two cards needed to wins it"() {
        given:
        Hand hand1 = new Hand([new Card(Colour.Y, 6)], [new Card(Colour.O, 6), new Card(Colour.R, 6)])
        Hand hand2 = new Hand([new Card(Colour.O, 7)], [])

        when:
        Map result = moveCalculator.calculateMoves([hand1, hand2], 0, Colour.R)

        then:
        result.addToPalette == []
        result.ruleChange == []
        result.twoCard == [[ruleCard: new Card(Colour.O, 6), otherCard: [new Card(Colour.R, 6)]]]
    }
}
