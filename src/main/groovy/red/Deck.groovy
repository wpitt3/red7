package red

import red.domain.Card
import red.domain.Colour
import red.domain.Hand

/**
 * Created by willpitt on 23/05/2017.
 */
class Deck {
    Random random
    List<Card> cards

    Deck() {
        random = new Random()
        cards = shuffleDeck(createDeck())
    }

    List<Hand> createHands(Integer players) {
        return (1..players).collect{
            new Hand([cards.pop()], (0..6).collect{cards.pop()})
        }
    }

    List<Card> createDeck() {
        return Colour.values().collect{ colour ->
            (0..6).collect{ number ->
                new Card(colour, number)
            }
        }.flatten()
    }

    List<Card> shuffleDeck(List<Card> oldDeck) {
        List<Card> deck = oldDeck.clone()
        Integer deckSize = deck.size()
        (0..deckSize-1).each{index ->
            Integer randomPosition = index + random.nextInt(deck.size()-index)
            Card temp = deck[randomPosition]
            deck[randomPosition] = deck[index]
            deck[index] = temp
        }
        return deck
    }
}
