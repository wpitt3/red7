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

    void "YellowRule most of one colour"() {
        given:
        List handA = [new Card(Colour.O, 5), new Card(Colour.B, 7)]
        List handB = [new Card(Colour.R, 1), new Card(Colour.R, 2)]

        when:
        Integer result = HandAnalyser.compareHands([handA, handB], Colour.Y)

        then:
        result == 1
    }

    void "YellowRule higher number if draw"() {
        given:
        List handA = [new Card(Colour.B, 5), new Card(Colour.B, 4)]
        List handB = [new Card(Colour.V, 6), new Card(Colour.V, 1)]

        when:
        Integer result = HandAnalyser.compareHands([handA, handB], Colour.Y)

        then:
        result == 1
    }

    void "YellowRule highest colour if numbers draw"() {
        given:
        List handA = [new Card(Colour.B, 5), new Card(Colour.B, 6)]
        List handB = [new Card(Colour.Y, 1), new Card(Colour.Y, 6)]

        when:
        Integer result = HandAnalyser.compareHands([handA, handB], Colour.Y)

        then:
        result == 1
    }

    void "GreenRule most even"() {
        given:
        List handA = [new Card(Colour.O, 5), new Card(Colour.B, 7)]
        List handB = [new Card(Colour.O, 1), new Card(Colour.R, 2)]

        when:
        Integer result = HandAnalyser.compareHands([handA, handB], Colour.G)

        then:
        result == 1
    }

    void "GreenRule higher number if draw"() {
        given:
        List handA = [new Card(Colour.B, 5), new Card(Colour.B, 4)]
        List handB = [new Card(Colour.V, 6), new Card(Colour.R, 1)]

        when:
        Integer result = HandAnalyser.compareHands([handA, handB], Colour.G)

        then:
        result == 1
    }

    void "GreenRule highest colour if numbers draw"() {
        given:
        List handA = [new Card(Colour.B, 5), new Card(Colour.B, 6)]
        List handB = [new Card(Colour.R, 1), new Card(Colour.Y, 6)]

        when:
        Integer result = HandAnalyser.compareHands([handA, handB], Colour.G)

        then:
        result == 1
    }

    void "GreenRule draw no even"() {
        given:
        List handA = [new Card(Colour.B, 5), new Card(Colour.B, 7)]
        List handB = [new Card(Colour.R, 1), new Card(Colour.Y, 7)]

        when:
        Integer result = HandAnalyser.compareHands([handA, handB], Colour.G)

        then:
        result == -1
    }

    void "BlueRule most different colours"() {
        given:
        List handA = [new Card(Colour.R, 5), new Card(Colour.R, 7)]
        List handB = [new Card(Colour.O, 1), new Card(Colour.R, 2)]

        when:
        Integer result = HandAnalyser.compareHands([handA, handB], Colour.B)

        then:
        result == 1
    }

    void "BlueRule higher number if draw"() {
        given:
        List handA = [new Card(Colour.V, 5), new Card(Colour.B, 4)]
        List handB = [new Card(Colour.V, 6), new Card(Colour.R, 1)]

        when:
        Integer result = HandAnalyser.compareHands([handA, handB], Colour.B)

        then:
        result == 1
    }

    void "BlueRule highest colour if numbers draw"() {
        given:
        List handA = [new Card(Colour.R, 5), new Card(Colour.B, 7)]
        List handB = [new Card(Colour.R, 1), new Card(Colour.Y, 7)]

        when:
        Integer result = HandAnalyser.compareHands([handA, handB], Colour.B)

        then:
        result == 1
    }

    void "IndigoRule most in a row"() {
        given:
        List handA = [new Card(Colour.R, 5), new Card(Colour.R, 7)]
        List handB = [new Card(Colour.O, 1), new Card(Colour.G, 2)]

        when:
        Integer result = HandAnalyser.compareHands([handA, handB], Colour.I)

        then:
        result == 1
    }

    void "IndigoRule higher number if draw"() {
        given:
        List handA = [new Card(Colour.V, 5), new Card(Colour.B, 4), new Card(Colour.R, 7)]
        List handB = [new Card(Colour.V, 6), new Card(Colour.R, 5), new Card(Colour.B, 5)]

        when:
        Integer result = HandAnalyser.compareHands([handA, handB], Colour.I)

        then:
        result == 1
    }

    void "IndigoRule highest colour if numbers draw"() {
        given:
        List handA = [new Card(Colour.Y, 6), new Card(Colour.B, 7)]
        List handB = [new Card(Colour.R, 6), new Card(Colour.Y, 7)]

        when:
        Integer result = HandAnalyser.compareHands([handA, handB], Colour.I)

        then:
        result == 1
    }

    void "VioletRule most below 4"() {
        given:
        List handA = [new Card(Colour.R, 3), new Card(Colour.R, 4)]
        List handB = [new Card(Colour.O, 1), new Card(Colour.R, 2)]

        when:
        Integer result = HandAnalyser.compareHands([handA, handB], Colour.V)

        then:
        result == 1
    }

    void "VioletRule higher number if draw"() {
        given:
        List handA = [new Card(Colour.V, 2), new Card(Colour.B, 2)]
        List handB = [new Card(Colour.V, 1), new Card(Colour.R, 3)]

        when:
        Integer result = HandAnalyser.compareHands([handA, handB], Colour.V)

        then:
        result == 1
    }

    void "VioletRule highest colour if numbers draw"() {
        given:
        List handA = [new Card(Colour.R, 2), new Card(Colour.B, 3)]
        List handB = [new Card(Colour.R, 1), new Card(Colour.Y, 3)]

        when:
        Integer result = HandAnalyser.compareHands([handA, handB], Colour.V)

        then:
        result == 1
    }

    void "VioletRule draw no even"() {
        given:
        List handA = [new Card(Colour.B, 5), new Card(Colour.B, 7)]
        List handB = [new Card(Colour.R, 4), new Card(Colour.Y, 7)]

        when:
        Integer result = HandAnalyser.compareHands([handA, handB], Colour.V)

        then:
        result == -1
    }
}
