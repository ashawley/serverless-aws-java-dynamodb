package org.ninthfloor.bj21.dynamodb;

import java.util.Optional;

import org.ninthfloor.bj21.gson.Player;

import com.google.gson.Gson;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;

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

    public Item add(Player player) {
        String json = gson.toJson(player);
        Item item = Item.fromJSON(json).withPrimaryKey("key", "version0");
        ddb.getTable(playersTableName).putItem(item);
        return item;
    }

    public Player toJSON(Item item) {
        return gson.fromJson(item.toJSON(), Player.class);
    }

    public Optional<Item> getItem(Long id) {
        return Optional.ofNullable(
            ddb.getTable(playersTableName).getItem("key", "version0", "id", id)
        );
    }

    public Optional<Player> getById(Long id) {
        return getItem(id).map(this::toJSON);
    }

    public Optional<PlayerItem> load(Long id) {
        return Optional.ofNullable(
            mapper.load(PlayerItem.class, "version0", id)
        );
    }

    public void update(PlayerItem item) {
        mapper.save(item);
    }

    public void update(Player player) {
        // mapper.load(PlayerItem.class, "version0", player.getId());
        mapper.save(PlayerItem.fromGSON(player));
    }

    public Optional<Player> remove(Long id) {
        return Optional.ofNullable(
            ddb.getTable(playersTableName)
               .deleteItem("key", "version0", "id", id)
               .getItem()
        ).map(this::toJSON);
    }

    public void remove(Player player) {
        mapper.delete(PlayerItem.fromGSON(player));
    }
}
