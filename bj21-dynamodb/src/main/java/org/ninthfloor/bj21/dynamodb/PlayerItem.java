package org.ninthfloor.bj21.dynamodb;

import java.util.Optional;

import org.ninthfloor.bj21.gson.Hands;
import org.ninthfloor.bj21.gson.Player;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.document.Item;

@DynamoDBTable(tableName="Players")
public class PlayerItem {

    private String key;
    private Long id;
    private Long table;
    private Long chips;
    private Long bet;

    protected static PlayerItem fromGSON(Player player) {
        PlayerItem playerItem = new PlayerItem();
        playerItem.setKey("version0");
        playerItem.setId(player.getId());
        playerItem.setChips(player.getChips());
        playerItem.setBet(
            Optional.ofNullable(player.getHand())
                .map(Hands::getBet)
                .get()
        );
        return playerItem;
    }

    public static PlayerItem fromItem(Item item) {
        PlayerItem playerItem = new PlayerItem();
        playerItem.setKey("version0");
        playerItem.setId(item.getLong("id"));
        if (item.isPresent("table")) {
            playerItem.setTable(item.getLong("table"));
        }
        if (item.isPresent("chips")) {
            playerItem.setChips(item.getLong("chips"));
        }
        if (item.isPresent("bet")) {
            playerItem.setBet(item.getLong("bet"));
        }
        return playerItem;
    }

    public Player toGSON() {
        Player player = new Player();
        player.setId(getId());
        player.setChips(getChips());
        return player;
    }

    @DynamoDBHashKey(attributeName="key")
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }

    @DynamoDBRangeKey(attributeName="id")
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @DynamoDBRangeKey(attributeName="table")
    public Long getTable() { return table; }
    public void setTable(Long table) { this.table = table; }

    @DynamoDBAttribute(attributeName="chips")
    public Long getChips() {return chips; }
    public void setChips(Long chips) { this.chips = chips; }

    @DynamoDBAttribute(attributeName="bet")
    public Long getBet() {return bet; }
    public void setBet(Long bet) { this.bet = bet; }
}
