package red

import red.domain.Card
import red.domain.Colour
import spock.lang.Specification

/**
 * Created by willpitt on 22/04/2017.
 */
class HandAnalyserTest extends Specification {

    void "fail if cards in common" () {
        given:
        List handA = [new Card(Colour.R, 7)]
        List handB = [new Card(Colour.R, 7)]

        when:
        HandAnalyser.compareHands([handA, handB], Colour.R)

        then:
        RuntimeException ex = thrown()
        ex.message == 'duplicate cards'
    }


    void "RedRule Highest Number Wins"() {
        given:
        List handA = [new Card(Colour.B, 7)]
        List handB = [new Card(Colour.R, 5)]

        when:
        Integer result = HandAnalyser.compareHands([handA, handB], Colour.R)

        then:
        result == 0
    }

    void "RedRule Highest Colour If Draw"() {
        given:
        List handA = [new Card(Colour.Y, 6)]
        List handB = [new Card(Colour.R, 6)]

        when:
        Integer result = HandAnalyser.compareHands([handA, handB], Colour.R)

        then:
        result == 1
    }

    void "RedRule use Hishest Card In hand"() {
        given:
        List handA = [new Card(Colour.Y, 7)]
        List handB = [new Card(Colour.V, 1), new Card(Colour.R, 7)]

        when:
        Integer result = HandAnalyser.compareHands([handA, handB], Colour.R)

        then:
        result == 1
    }

    void "RedRule ignore size of hands"() {
        given:
        List handA = [new Card(Colour.Y, 4), new Card(Colour.O, 5), new Card(Colour.B, 5)]
        List handB = [new Card(Colour.V, 1), new Card(Colour.R, 7)]

        when:
        Integer result = HandAnalyser.compareHands([handA, handB], Colour.R)

        then:
        result == 1
    }

    void "OrangeRule most of one number"() {
        given:
        List handA = [new Card(Colour.O, 5), new Card(Colour.B, 5)]
        List handB = [new Card(Colour.V, 1), new Card(Colour.R, 7)]

        when:
        Integer result = HandAnalyser.compareHands([handA, handB], Colour.O)

        then:
        result == 0
    }

    void "OrangeRule higher number if draw"() {
        given:
        List handA = [new Card(Colour.O, 5), new Card(Colour.B, 5), new Card(Colour.R, 7)]
        List handB = [new Card(Colour.V, 6), new Card(Colour.R, 6)]

        when:
        Integer result = HandAnalyser.compareHands([handA, handB], Colour.O)

        then:
        result == 1
    }

    void "OrangeRule use the higher value if one hand has two equal sized groups"() {
        given:
        List handA = [new Card(Colour.O, 5), new Card(Colour.B, 5), new Card(Colour.R, 7), new Card(Colour.O, 7)]
        List handB = [new Card(Colour.V, 6), new Card(Colour.R, 6)]

        when:
        Integer result = HandAnalyser.compareHands([handA, handB], Colour.O)

        then:
        result == 0
    }

    void "OrangeRule highest colour if numbers draw"() {
        given:
        List handA = [new Card(Colour.O, 5), new Card(Colour.B, 5)]
        List handB = [new Card(Colour.V, 5), new Card(Colour.R, 5)]

        when:
        Integer result = HandAnalyser.compareHands([handA, handB], Colour.O)

        then:
        result == 1
    }
}
