package red

import red.domain.Card
import red.domain.Colour
import red.domain.Hand

/**
 * Created by willpitt on 26/04/2017.
 */
class Game {
    Random random
    Integer players
    List playerStates

    Game(Integer players = 2) {
        random = new Random()
        this.players = players
        playerStates = (1..players).collect{true}
    }

    List<Hand> createHands() {
        List<Card> deck = shuffleDeck(createDeck())
        return (1..players).collect{
            new Hand([deck.pop()], (0..6).collect{deck.pop()})
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

    Integer nextPlayer(Integer currentIndex) {
        if(playerStates.count{it} < 2) return -1
        currentIndex = (currentIndex+1)%players
        while( !playerStates[currentIndex] ) {
            currentIndex = (currentIndex+1)%players
        }
        return currentIndex
    }
}
