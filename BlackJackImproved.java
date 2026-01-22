/******************************************************************************
 * 
 Eura Pulliam
 1.22.26
 Reviewing Arrays

 *******************************************************************************/

 import java.util.*;

public class BlackJackImproved {

    // 1. Scanner for user input
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // 2. Create and shuffle a new deck
        Deck deck = new Deck();
        deck.shuffle();

        // 3. Create hands for player and dealer
        Hand playerHand = new Hand();
        Hand dealerHand = new Hand();

        // 4. Deal two cards to player
        playerHand.addCard(deck.dealCard());
        playerHand.addCard(deck.dealCard());

        // 5. Deal two cards to dealer
        dealerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());

        // 6. Show player's hand
        System.out.println("Your hand:");
        playerHand.printHand();

        // 7. Show only ONE dealer card (standard Blackjack rule)
        System.out.println("\nDealer shows:");
        System.out.println(dealerHand.getCards().get(0));

        // 8. Player turn loop
        while (true) {

            // 9. Calculate and show player's total
            int playerTotal = playerHand.getValue();
            System.out.println("\nYour total: " + playerTotal);

            // 10. Check if player busted
            if (playerTotal > 21) {
                System.out.println("You busted! Dealer wins.");
                return;
            }

            // 11. Ask player for action
            System.out.print("Hit or stand? ");
            String action = scanner.nextLine().toLowerCase();

            // 12. If player hits, deal another card
            if (action.equals("hit")) {
                Card newCard = deck.dealCard();
                playerHand.addCard(newCard);
                System.out.println("You drew: " + newCard);

            // 13. If player stands, stop player turn
            } else if (action.equals("stand")) {
                break;

            // 14. Handle invalid input
            } else {
                System.out.println("Invalid input. Please type hit or stand.");
            }
        }

        // 15. Dealer reveals hand
        System.out.println("\nDealer's hand:");
        dealerHand.printHand();

        // 16. Dealer draws until total is at least 17
        while (dealerHand.getValue() < 17) {

            // 17. Dealer draws a card
            Card newCard = deck.dealCard();
            dealerHand.addCard(newCard);
            System.out.println("Dealer draws: " + newCard);
        }

        // 18. Show final totals
        int playerTotal = playerHand.getValue();
        int dealerTotal = dealerHand.getValue();

        System.out.println("\nFinal Totals:");
        System.out.println("Player: " + playerTotal);
        System.out.println("Dealer: " + dealerTotal);

        // 19. Determine winner
        if (dealerTotal > 21) {
            System.out.println("Dealer busted! You win!");
        } else if (playerTotal > dealerTotal) {
            System.out.println("You win!");
        } else if (playerTotal < dealerTotal) {
            System.out.println("Dealer wins!");
        } else {
            System.out.println("It's a tie!");
        }

        // 20. Close scanner
        scanner.close();
    }
}

/*
 * Card class represents a single playing card
 */
class Card {

    // 21. Store rank and suit
    private String rank;
    private String suit;

    // 22. Constructor to create a card
    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    // 23. Get Blackjack value of the card
    public int getValue() {

        // 24. Face cards are worth 10
        if (rank.equals("Jack") || rank.equals("Queen") || rank.equals("King")) {
            return 10;
        }

        // 25. Ace starts as 11 (may change to 1 in Hand class)
        if (rank.equals("Ace")) {
            return 11;
        }

        // 26. Number cards use their numeric value
        return Integer.parseInt(rank);
    }

    // 27. String version of card for printing
    public String toString() {
        return rank + " of " + suit;
    }
}

/*
 * Deck class represents a full deck of 52 cards
 */
class Deck {

    // 28. List to hold all cards
    private List<Card> cards = new ArrayList<>();

    // 29. Constructor builds the deck
    public Deck() {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2","3","4","5","6","7","8","9","10",
                          "Jack","Queen","King","Ace"};

        // 30. Create all 52 combinations
        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    // 31. Shuffle the deck
    public void shuffle() {
        Collections.shuffle(cards);
    }

    // 32. Deal (remove and return) the top card
    public Card dealCard() {
        return cards.remove(0);
    }
}

/*
 * Hand class represents a player's or dealer's hand
 */
class Hand {

    // 33. Store cards in the hand
    private List<Card> cards = new ArrayList<>();

    // 34. Add a card to the hand
    public void addCard(Card card) {
        cards.add(card);
    }

    // 35. Return list of cards
    public List<Card> getCards() {
        return cards;
    }

    // 36. Calculate total hand value (with Ace logic)
    public int getValue() {
        int total = 0;
        int aceCount = 0;

        // 37. Add all card values
        for (Card card : cards) {
            total += card.getValue();
            if (card.toString().startsWith("Ace")) {
                aceCount++;
            }
        }

        // 38. Convert Aces from 11 to 1 if busted
        while (total > 21 && aceCount > 0) {
            total -= 10;
            aceCount--;
        }

        return total;
    }

    // 39. Print all cards in hand
    public void printHand() {
        for (Card card : cards) {
            System.out.println(card);
        }
    }
}
