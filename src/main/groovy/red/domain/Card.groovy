package red.domain

/**
 * Created by willpitt on 22/04/2017.
 */
class Card implements Comparable {
    Colour colour
    Integer number

    Card() {

    }

    Card(Colour colour, Integer number) {
        this.colour = colour
        this.number = number
    }

    @Override
    boolean equals(Object obj) {
        if (!(obj instanceof Card))
            return false
        return obj.colour == this.colour && obj.number == this.number
    }

    @Override
    int hashCode() {
        int hash = 17;
        hash = 31 * hash + colour.name.hashCode();
        hash = 31 * hash + number
        return hash;
    }

    @Override
    String toString() {
        return colour.toString() + "" + (number)
    }

    Card clone() {
        return new Card(this.colour, this.number)
    }

    String name() {
        return colour.name + " " + (number)
    }

    int compareTo(Object o) {
        if (o.number < this.number) return 1
        if (o.number > this.number) return -1
        if (o.colour.rank < this.colour.rank) return 1
        if (o.colour.rank > this.colour.rank) return -1
        return 0
    }
}
