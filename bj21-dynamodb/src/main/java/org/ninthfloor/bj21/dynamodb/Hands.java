package org.ninthfloor.bj21.dynamodb;

import java.util.Optional;

import com.google.gson.Gson;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
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

    public PutItemOutcome add(org.ninthfloor.bj21.gson.Hand hand) {
        String json = gson.toJson(hand);
        Item item = Item.fromJSON(json).withPrimaryKey("key", "version0");
        return ddb.getTable(handsTableName).putItem(item);
    }

    public org.ninthfloor.bj21.gson.Hand toJSON(Item item) {
        return gson.fromJson(item.toJSON(), org.ninthfloor.bj21.gson.Hand.class);
    }

    public Optional<Item> getItem(Long id) {
        return Optional.ofNullable(
            ddb.getTable(handsTableName).getItem("key", "version0", "id", id)
        );
    }

    public Optional<org.ninthfloor.bj21.gson.Hand> getById(Long id) {
        return getItem(id).map(this::toJSON);
    }

    public HandItem load(String key, Long id) {
        return mapper.load(HandItem.class, key, id);
    }

    public void update(HandItem item) {
        mapper.save(item);
    }

    public void update(org.ninthfloor.bj21.gson.Hand hand) {
        // mapper.load(HandItem.class, "version0", hand.getId());
        mapper.save(HandItem.fromGSON(hand));
    }

    public Optional<org.ninthfloor.bj21.gson.Hand> remove(Long id) {
        return Optional.ofNullable(
            ddb.getTable(handsTableName)
               .deleteItem("key", "version0", "id", id)
               .getItem()
        ).map(this::toJSON);
    }

    public void remove(org.ninthfloor.bj21.gson.Hand hand) {
        mapper.delete(HandItem.fromGSON(hand));
    }
}
