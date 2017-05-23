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
        game = new Game(5)
    }

    void "next player is one after" () {
        when:
            Integer index = game.nextPlayer(0)
        then:
            index == 1
    }

    void "first player if next if current was last" () {
        when:
        Integer index = game.nextPlayer(4)
        then:
        index == 0
    }

    void "ignore players that are out" () {
        when:
        game.playerStates[1] = false
        Integer index = game.nextPlayer(0)
        then:
        index == 2
    }

    void "stop if all players are out" () {
        when:
        game.playerStates[1] = false
        game.playerStates[2] = false
        game.playerStates[3] = false
        game.playerStates[4] = false

        Integer index = game.nextPlayer(0)
        then:
        index == -1
    }

}
