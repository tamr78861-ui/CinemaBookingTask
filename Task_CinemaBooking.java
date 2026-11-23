import java.util.*;

/*
 =======================================================================
  TASK: Cinema Seat Booking - a HashMap with your own class as the key
 =======================================================================
*/

class Task_CinemaBooking {
    static class Seat {
        final char row;
        final int number;

        Seat(char row, int number) {
            this.row = row;
            this.number = number;
        }

        @Override
        public String toString() {
            return "" + row + number;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Seat)) return false;
            Seat seat = (Seat) o;
            return row == seat.row && number == seat.number;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, number);
        }
    }

    public static void main(String[] args) {
        HashMap<Seat, String> bookings = new HashMap<>();
        HashSet<Seat> broken = new HashSet<>();

        // Part A
        bookings.put(new Seat('A', 1), "Ali");
        bookings.put(new Seat('A', 2), "Sara");
        bookings.put(new Seat('B', 5), "Omar");
        bookings.put(new Seat('C', 3), "Mona");
        System.out.println("Bookings:           " + bookings);

        // Part B
        System.out.println("Who booked C3:      " + bookings.get(new Seat('C', 3)));

        // Part C
        System.out.println("Is A2 taken:        " + bookings.containsKey(new Seat('A', 2)));
        System.out.println("Is E6 taken:        " + bookings.containsKey(new Seat('E', 6)));

        // Part D
        String existing = bookings.putIfAbsent(new Seat('B', 5), "Youssef");
        if (existing == null) {
            System.out.println("Youssef -> B5:      booked");
        } else {
            System.out.println("Youssef -> B5:      refused, taken by " + existing);
        }

        // Part E
        String ali = bookings.remove(new Seat('A', 1));
        if (ali != null) bookings.put(new Seat('D', 2), ali);
        System.out.println("After Ali moved:    " + bookings);

        // Part F
        broken.add(new Seat('E', 1));
        broken.add(new Seat('E', 2));
        if (broken.contains(new Seat('E', 1))) {
            System.out.println("Hana -> E1:         refused, seat is broken");
        } else {
            bookings.put(new Seat('E', 1), "Hana");
            System.out.println("Hana -> E1:         booked");
        }

        // Part G
        System.out.println("Seats booked:       " + bookings.size());
        System.out.println();
        printChart(bookings, broken);
    }

    // GIVEN - do not change.
    static void printChart(HashMap<Seat, String> bookings, HashSet<Seat> broken) {
        System.out.println("     1  2  3  4  5  6");
        for (char row = 'A'; row <= 'E'; row++) {
            System.out.print(row + "  ");
            for (int number = 1; number <= 6; number++) {
                Seat seat = new Seat(row, number);
                if (bookings.containsKey(seat)) {
                    System.out.print("  X");
                } else if (broken.contains(seat)) {
                    System.out.print("  #");
                } else {
                    System.out.print("  .");
                }
            }
            System.out.println();
        }
    }
}

/*
 QUESTIONS - answers

 Q1. HashMap and HashSet use hashCode() to find a bucket and equals() to
     distinguish keys in that bucket. Implementing both makes different Seat
     objects representing the same row and number behave as the same key.

 Q2. Without hashCode(), Object's identity-based hash code remains in use.
     Equal Seat objects can then have different hashes, violating the required
     contract. Lookups/contains checks using newly created seats can fail, and
     duplicate logical seats can be stored; the output becomes incorrect.

 Q3. final prevents a seat's identity fields from changing. If a key changes
     after insertion, its hash/bucket may no longer match where it was stored,
     so it can become difficult or impossible to find or remove from the map.

 Q4. equals(Seat) is an overload, not an override of equals(Object). With
     @Override it fails to compile. If @Override is removed, it compiles, but
     HashMap calls equals(Object), so the overload is not used and distinct
     Seat objects are compared by Object's identity implementation.

 BONUS. A record automatically provides value-based equals() and hashCode()
        from its components, so newly created equal Seat records work as keys.
*/
