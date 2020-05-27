package org.ninthfloor.bj21.dynamodb;

import java.util.List;
import java.util.stream.Collectors;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="Hands")
public class HandItem {

    private String key;
    private Long id;
    private Long playerId;
    private Long prev;
    private List<Card> cards;
    private List<String> actions;
    private Long initial;
    private Long bet;
    private Long total;

    protected static HandItem fromGSON(org.ninthfloor.bj21.gson.Hand hand) {
        HandItem handItem = new HandItem();
        handItem.setKey("version0");
        handItem.setId(hand.getId());
        handItem.setPrev(hand.getPrev());
        handItem.setCards(Card.fromGSON(hand.getCards()));
        handItem.setActions(hand.getActions().stream()
            .map((org.ninthfloor.bj21.gson.Hand.ActionsEnum action)
                 -> action.getValue()
            )
            .collect(Collectors.toList())
        );
        handItem.setInitial(hand.getInitial());
        handItem.setBet(hand.getBet());
        handItem.setTotal(hand.getTotal());
        return handItem;
    }

    @DynamoDBHashKey(attributeName="key")
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }

    @DynamoDBRangeKey(attributeName="id")
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @DynamoDBAttribute(attributeName="prev")
    public Long getPrev() { return prev; }
    public void setPrev(Long prev) { this.prev = prev; }

    @DynamoDBAttribute(attributeName="playerId")
    public Long getPlayerId() { return playerId; }
    public void setPlayerId(Long playerId) { this.playerId = playerId; }

    @DynamoDBAttribute(attributeName="cards")
    public List<Card> getCards() { return cards; }
    public void setCards(List<Card> cards) { this.cards = cards; }

    @DynamoDBAttribute(attributeName="actions")
    public List<String> getActions() { return actions; }
    public void setActions(List<String> actions) { this.actions = actions; }

    @DynamoDBAttribute(attributeName="initial")
    public Long getInitial() { return initial; }
    public void setInitial(Long initial) { this.initial = initial; }

    @DynamoDBAttribute(attributeName="total")
    public Long getTotal() { return total; }
    public void setTotal(Long total) { this.total = total; }

    @DynamoDBAttribute(attributeName="bet")
    public Long getBet() {return bet; }
    public void setBet(Long bet) { this.bet = bet; }
}
