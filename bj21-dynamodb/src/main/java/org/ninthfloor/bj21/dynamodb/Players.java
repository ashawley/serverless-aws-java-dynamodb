package org.ninthfloor.bj21.dynamodb;

import java.util.Optional;

import com.google.gson.Gson;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;

public class Players {

    private String playersTableName;

    private DynamoDB ddb;

    private DynamoDBMapper mapper;

    private Gson gson;

    public Players(String playersTableName, AmazonDynamoDB client, Gson gson) {
        this.playersTableName = playersTableName;
        this.ddb = new DynamoDB(client);
        DynamoDBMapperConfig mapperConfig =
            DynamoDBMapperConfig.builder()
            .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(playersTableName))
            .build();
        this.mapper = new DynamoDBMapper(client, mapperConfig);
        this.gson = gson;
    }

    public PutItemOutcome add(org.ninthfloor.bj21.gson.Player player) {
        String json = gson.toJson(player);
        Item item = Item.fromJSON(json).withPrimaryKey("key", "version0");
        return ddb.getTable(playersTableName).putItem(item);
    }

    public org.ninthfloor.bj21.gson.Player toJSON(Item item) {
        return gson.fromJson(item.toJSON(), org.ninthfloor.bj21.gson.Player.class);
    }

    public Optional<Item> getItem(Long id) {
        return Optional.ofNullable(
            ddb.getTable(playersTableName).getItem("key", "version0", "id", id)
        );
    }

    public Optional<org.ninthfloor.bj21.gson.Player> getById(Long id) {
        return getItem(id).map(this::toJSON);
    }

    public PlayerItem load(String key, Long id) {
        return mapper.load(PlayerItem.class, key, id);
    }

    public void update(PlayerItem item) {
        mapper.save(item);
    }

    public void update(org.ninthfloor.bj21.gson.Player player) {
        // mapper.load(PlayerItem.class, "version0", player.getId());
        mapper.save(PlayerItem.fromGSON(player));
    }

    public Optional<org.ninthfloor.bj21.gson.Player> remove(Long id) {
        return Optional.ofNullable(
            ddb.getTable(playersTableName)
               .deleteItem("key", "version0", "id", id)
               .getItem()
        ).map(this::toJSON);
    }

    public void remove(org.ninthfloor.bj21.gson.Player player) {
        mapper.delete(PlayerItem.fromGSON(player));
    }
}
