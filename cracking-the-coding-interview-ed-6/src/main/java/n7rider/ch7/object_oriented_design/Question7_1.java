package n7rider.ch7.object_oriented_design;

/**
 * Deck of Cards: Design the data structures for a generic deck of cards. Explain how you would
 * subclass the data structures to implement blackjack.
 *
 * After completing:
 * Tried to add a lot of details, but not sure how much is enough
 *
 * After comparing with solution:
 * I have added more details in terms of types, but less in terms of methods. I especially
 * missed adding a method which computes card values specifically for Blackjack.
 *
 *
 */
public class Question7_1 {
    class GameRoom {
        Table[] table;
    }

    class Table {
        Player[] players;
        Dealer dealer; // Singleton
        Deck[] decks;
        Game game; // Singleton
    }

    abstract class Game {
        int gameId;

        abstract void initiateBids();


    }

    abstract class Player {
        String name;
        Token[] tokens;
        Card[] cards;

        abstract Token[] submitToken();
        abstract void requestCard();
        abstract void denyCard();
        abstract void receiveCard(Card card);
        abstract void checkCount(Card[] cards);
    }

    abstract class Dealer {
        Token[] tokens;
        abstract Card presentCard(Deck[] decks, CardPresentation cardPresentation, Player player);
    }

    class Token {
        int val;
        Color color;
    }

    abstract class Deck {
        Card[] cards;

        abstract void initDeck();
    }

    abstract class Card {
        Suit suit;
        char val;
        abstract int findCardVal();
    }

    enum Suit {
        SPADE, CLUB, HEART, DIAMOND
    };

    enum CardPresentation {
        UP, DOWN
    };

    enum Color {
        RED, BLUE, GREEN, YELLOW;
    }
}

/**
 * Entities involved:
 * Person-Type: Dealer, Player [Dealer is a singleton for a table]
 * Person : person-type, name, token
 * Token : value, color
 * Deck : card[] (Pre-populated with 52 cards)
 * Card : value, suit
 * Suit: Spade | Club | Heart | Diamond
 * Table : Person[], dealer, Deck[], Game
 * Game: game-id, Person[], dealer, Deck[] [Game is a singleton for a table]
 * GameRoom: Table[] (game room is a singleton)
 * Card-Presentation: Up | Down
 *
 * Create a game room instance // Constructor
 * Create a table // Constructor
 *  Create a dealer (name, token) // Constructor
 *  Create a person[] (name, token) // Constructor
 *  Create deck[] (card[]) // Constructor
 *
 * Create a game // Constructor
 *  Continue only if person[] have at-least 1 token
 *  Call game.initiateBids()
 *      Call person.submitToken for each in person[]
 *      Continue only if dealer has at-least submitted tokens.val * 2.5
 *
 *      dealer.presentCard (Card-Presentation)
 *      player.requestCard
 *      player.denyCard
 *
 *      player.checkCount
 *      dealer.transferTo(player, token) // depends on game.getBid(player-name)
 *
 * Dealer : name, token [Singleton]
 *      presentCard
 *      collect
 *      transferTo(player, token)
 *      hasEnoughTokens(int val)
 *      initiateTurn(player)
 *      endTurn(player)
 * Player : person-type, name, token
 *      submitTokens(Token[])
 *      requestCard
 *      denyCard
 *      checkCount
 *
 * Token : value, color
 * Deck : card[] (Pre-populated with 52 cards)
 * Card : value, suit
 * Suit: Spade | Club | Heart | Diamond
 * Table : Person[], dealer, Deck[], Game
 * Game: game-id, Person[], dealer, Deck[] [Game is a singleton for a table]
 *      initiateBids
 *      printResults
 * GameRoom: Table[] (game room is a singleton)
 * Card-Presentation: Up | Down
 *
 */