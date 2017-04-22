package red.domain

/**
 * Created by willpitt on 22/04/2017.
 */
enum Colour {
    R(7, 'Red', 'Highest card'),
    O(6, 'Orange', 'Most of one number'),
    Y(5, 'Yellow', 'Most of one colour'),
    G(4, 'Green', 'Most even cards'),
    B(3, 'Blue', 'Most different colours'),
    I(2, 'Indigo', 'Most cards in a row'),
    V(1, 'Violet', 'Most cards below 4')

    Integer rank
    String name, rule

    public Colour(Integer rank, String name, String rule) {
        this.rank = rank
        this.name = name
        this.rule = rule
    }
}
