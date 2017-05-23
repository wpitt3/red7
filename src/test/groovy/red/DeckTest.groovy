package red

import red.domain.Colour
import red.domain.Hand
import spock.lang.Specification

/**
 * Created by willpitt on 26/04/2017.
 */
class DeckTest extends Specification {
    Deck deck

    void setup() {
        deck = new Deck()
    }

    void "createDeck" () {
        when:
            List cards = deck.createDeck()
        then:
        cards.size() == 49
            (0..6).every{ index -> cards.count{ card -> card.number == index } == 7}
            Colour.values().every{ colour -> cards.count{ card -> card.colour == colour } == 7}
    }

    void "shuffleDeck" () {
        when:
        List cards = deck.createDeck()
        List newCards = deck.shuffleDeck(cards)

        then:
        newCards.size() == 49
        cards == deck.createDeck()
        (0..6).every{ index -> newCards.count{ card -> card.number == index } == 7}
        Colour.values().every{ colour -> newCards.count{ card -> card.colour == colour } == 7}
        cards != newCards // slight chance of failure
        cards.sort() == newCards.sort()
    }

    void "start new deck"() {
        when:
        List<Hand> hands = deck.createHands(2)
        then:
        hands.every{ Hand hand -> hand.palette.size() == 1 }
        hands.every{ Hand hand -> hand.inHand.size() == 7 }
    }
}
