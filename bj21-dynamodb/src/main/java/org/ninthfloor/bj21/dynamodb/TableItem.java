package org.ninthfloor.bj21.dynamodb;

import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="Tables")
public class TableItem {

    private String key;
    private Long id;
    private Long decks;
    private Long seats;
    private Long players;
    private Long minimum;
    private Long maximum;
    private Long rounds;

    protected static TableItem fromGSON(org.ninthfloor.bj21.gson.Table table) {
        TableItem tableItem = new TableItem();
        tableItem.setKey("version0");
        tableItem.setId(table.getId());
        tableItem.setDecks(table.getDecks());
        tableItem.setSeats(table.getSeats());
        tableItem.setPlayers(table.getPlayers());
        tableItem.setMinimum(table.getMinimum());
        tableItem.setMaximum(table.getMaximum());
        tableItem.setRounds(table.getRounds());
        return tableItem;
    }

    @DynamoDBHashKey(attributeName="key")
    public String getKey() { return key; }
    public void setKey(String key) {this.key = key; }

    @DynamoDBRangeKey(attributeName="id")
    public Long getId() { return id; }
    public void setId(Long id) {this.id = id; }

    @DynamoDBAttribute(attributeName="decks")
    public Long getDecks() {return decks; }
    public void setDecks(Long decks) { this.decks = decks; }

    @DynamoDBAttribute(attributeName="seats")
    public Long getSeats() {return seats; }
    public void setSeats(Long seats) { this.seats = seats; }

    @DynamoDBAttribute(attributeName="players")
    public Long getPlayers() {return players; }
    public void setPlayers(Long players) { this.players = players; }

    @DynamoDBAttribute(attributeName="minimum")
    public Long getMinimum() {return minimum; }
    public void setMinimum(Long minimum) { this.minimum = minimum; }

    @DynamoDBAttribute(attributeName="maximum")
    public Long getMaximum() {return maximum; }
    public void setMaximum(Long maximum) { this.maximum = maximum; }

    @DynamoDBAttribute(attributeName="rounds")
    public Long getRounds() {return rounds; }
    public void setRounds(Long rounds) { this.rounds = rounds; }
}
