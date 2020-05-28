package org.ninthfloor.bj21.dynamodb;

import java.util.List;
import java.util.stream.Collectors;

import org.ninthfloor.bj21.gson.Card;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class CardDocument {

    private String suit;
    private String face;

    protected static CardDocument fromGSON(Card card) {
        CardDocument cardDocument = new CardDocument();
        cardDocument.setSuit(card.getSuit().getValue());
        cardDocument.setFace(card.getFace().getValue());
        return cardDocument;
    }

    protected static List<CardDocument> fromGSON(List<Card> cards) {
        return cards.stream()
            .map(CardDocument::fromGSON)
            .collect(Collectors.toList());
    }

    public String getSuit() { return suit; }
    public void setSuit(String suit) { this.suit = suit; }

    public String getFace() { return face; }
    public void setFace(String face) { this.face = face; }
}
