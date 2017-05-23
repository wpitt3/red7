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

    Integer nextPlayer(Integer currentIndex) {
        if(playerStates.count{it} < 2) return -1
        currentIndex = (currentIndex+1)%players
        while( !playerStates[currentIndex] ) {
            currentIndex = (currentIndex+1)%players
        }
        return currentIndex
    }
}
