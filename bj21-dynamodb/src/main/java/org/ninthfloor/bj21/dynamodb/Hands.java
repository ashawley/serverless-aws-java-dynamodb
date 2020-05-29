package org.ninthfloor.bj21.dynamodb;

import java.util.Optional;

import org.ninthfloor.bj21.gson.Hand;

import com.google.gson.Gson;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;

public class Hands {

    private String handsTableName;

    private DynamoDB ddb;

    private DynamoDBMapper mapper;

    private Gson gson;

    public Hands(String handsTableName, AmazonDynamoDB client, Gson gson) {
        this.handsTableName = handsTableName;
        this.ddb = new DynamoDB(client);
        DynamoDBMapperConfig mapperConfig =
            DynamoDBMapperConfig.builder()
            .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(handsTableName))
            .build();
        this.mapper = new DynamoDBMapper(client, mapperConfig);
        this.gson = gson;
    }

    public Item add(Hand hand) {
        String json = gson.toJson(hand);
        Item item = Item.fromJSON(json).withPrimaryKey("key", "version0");
        ddb.getTable(handsTableName).putItem(item);
        return item;
    }

    public Hand toJSON(Item item) {
        return gson.fromJson(item.toJSON(), Hand.class);
    }

    public Optional<Item> getItem(Long id) {
        return Optional.ofNullable(
            ddb.getTable(handsTableName).getItem("key", "version0", "id", id)
        );
    }

    public Optional<Hand> getById(Long id) {
        return getItem(id).map(this::toJSON);
    }

    public Optional<HandItem> load(Long id) {
        return Optional.ofNullable(
            mapper.load(HandItem.class, "version0", id)
        );
    }

    public void update(HandItem item) {
        mapper.save(item);
    }

    public HandItem update(Hand hand) {
        HandItem handItem = HandItem.fromGSON(hand);
        mapper.save(HandItem.fromGSON(hand));
        return handItem;
    }

    public Optional<Hand> remove(Long id) {
        return Optional.ofNullable(
            ddb.getTable(handsTableName)
               .deleteItem("key", "version0", "id", id)
               .getItem()
        ).map(this::toJSON);
    }

    public void remove(Hand hand) {
        mapper.delete(HandItem.fromGSON(hand));
    }
}
