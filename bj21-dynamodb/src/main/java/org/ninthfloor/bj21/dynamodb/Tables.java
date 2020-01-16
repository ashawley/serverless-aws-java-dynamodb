package org.ninthfloor.bj21.dynamodb;

import java.util.Optional;

import com.google.gson.Gson;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;

public class Tables {

    private String tablesTableName;

    private AmazonDynamoDB client;

    private DynamoDB ddb;

    private DynamoDBMapper mapper;

    private Gson gson;

    public Tables(String tablesTableName, AmazonDynamoDB client, Gson gson) {
        this.tablesTableName = tablesTableName;
        this.client = client;
        this.ddb = new DynamoDB(client);
        DynamoDBMapperConfig mapperConfig =
            DynamoDBMapperConfig.builder()
            .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(tablesTableName))
            .build();
        this.mapper = new DynamoDBMapper(client, mapperConfig);
        this.gson = gson;
    }

    public PutItemOutcome add(org.ninthfloor.bj21.gson.Table table) {
        String json = gson.toJson(table);
        Item item = Item.fromJSON(json).withPrimaryKey("key", "version0");
        return ddb.getTable(tablesTableName).putItem(item);
    }

    public Optional<Item> getItem(Long id) {
        return Optional.ofNullable(
            ddb.getTable(tablesTableName).getItem("key", "version0", "id", id)
        );
    }

    public Optional<org.ninthfloor.bj21.gson.Table> getById(Long id) {
        return getItem(id).map((Item item) ->
            gson.fromJson(item.toJSON(), org.ninthfloor.bj21.gson.Table.class)
        );
    }

    public TableItem load(String key, Long id) {
        return mapper.load(TableItem.class, key, id);
    }

    public void update(TableItem item) {
        mapper.save(item);
    }

    public void update(org.ninthfloor.bj21.gson.Table table) {
        // mapper.load(TableItem.class, "version0", table.getId());
        mapper.save(TableItem.fromGSON(table));
    }

    public void remove(org.ninthfloor.bj21.gson.Table table) {
        mapper.delete(TableItem.fromGSON(table));
    }
}
