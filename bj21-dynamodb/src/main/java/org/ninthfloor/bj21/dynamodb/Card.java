package org.ninthfloor.bj21.dynamodb;

import java.util.List;
import java.util.stream.Collectors;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class Card {

    private String suit;
    private String face;

    protected static Card fromGSON(org.ninthfloor.bj21.gson.Card card) {
        Card cardDocument = new Card();
        cardDocument.setSuit(card.getSuit().getValue());
        cardDocument.setFace(card.getFace().getValue());
        return cardDocument;
    }

    protected static List<Card> fromGSON(List<org.ninthfloor.bj21.gson.Card> cards) {
        return cards.stream()
            .map(Card::fromGSON)
            .collect(Collectors.toList());
    }

    public String getSuit() { return suit; }
    public void setSuit(String suit) { this.suit = suit; }

    public String getFace() { return face; }
    public void setFace(String face) { this.face = face; }
}
