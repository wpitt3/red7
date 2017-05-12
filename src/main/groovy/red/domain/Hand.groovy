package red.domain

/**
 * Created by willpitt on 22/04/2017.
 */
class Hand {

    List<Card> palette
    List<Card> inHand

    Hand(List<Card> palette, List<Card> inHand) {
        this.palette = palette
        this.inHand = inHand
    }
}
