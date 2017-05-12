package red

import red.domain.Colour
import red.domain.Hand
import spock.lang.Specification

/**
 * Created by willpitt on 26/04/2017.
 */
class GameTest extends Specification {
    Game game
    void setup() {
        game = new Game()
    }

    void "createDeck" () {
        when:
            List deck = game.createDeck()
        then:
            deck.size() == 49
            (0..6).every{ index -> deck.count{ card -> card.number == index } == 7}
            Colour.values().every{ colour -> deck.count{ card -> card.colour == colour } == 7}
    }

    void "shuffleDeck" () {
        when:
        List deck = game.createDeck()
        List newDeck = game.shuffleDeck(deck)

        then:
        newDeck.size() == 49
        deck == game.createDeck()
        (0..6).every{ index -> newDeck.count{ card -> card.number == index } == 7}
        Colour.values().every{ colour -> newDeck.count{ card -> card.colour == colour } == 7}
        deck != newDeck // slight chance of failure
        deck.sort() == newDeck.sort()
    }

    void "startGame"() {
        when:
        List<Hand> hands = game.createHands(2)
        then:
        hands.every{ Hand hand -> hand.palette.size() == 1 }
        hands.every{ Hand hand -> hand.inHand.size() == 7 }


    }
}
