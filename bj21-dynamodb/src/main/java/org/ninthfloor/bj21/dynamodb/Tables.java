package org.ninthfloor.bj21.dynamodb;

import java.util.Optional;

import org.ninthfloor.bj21.gson.Table;

import com.google.gson.Gson;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;

public class Tables {

    private String tablesTableName;

    private DynamoDB ddb;

    private DynamoDBMapper mapper;

    private Gson gson;

    public Tables(String tablesTableName, AmazonDynamoDB client, Gson gson) {
        this.tablesTableName = tablesTableName;
        this.ddb = new DynamoDB(client);
        DynamoDBMapperConfig mapperConfig =
            DynamoDBMapperConfig.builder()
            .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(tablesTableName))
            .build();
        this.mapper = new DynamoDBMapper(client, mapperConfig);
        this.gson = gson;
    }

    public Item add(Table table) {
        String json = gson.toJson(table);
        Item item = Item.fromJSON(json).withPrimaryKey("key", "version0");
        ddb.getTable(tablesTableName).putItem(item);
        return item;
    }

    public Table toJSON(Item item) {
        return gson.fromJson(item.toJSON(), Table.class);
    }

    public Optional<Item> getItem(Long id) {
        return Optional.ofNullable(
            ddb.getTable(tablesTableName).getItem("key", "version0", "id", id)
        );
    }

    public Optional<Table> getById(Long id) {
        return getItem(id).map(this::toJSON);
    }

    public Optional<TableItem> load(Long id) {
        return Optional.ofNullable(
            mapper.load(TableItem.class, "version0", id)
        );
    }

    public void update(TableItem item) {
        mapper.save(item);
    }

    public void update(Table table) {
        // mapper.load(TableItem.class, "version0", table.getId());
        mapper.save(TableItem.fromGSON(table));
    }

    public Optional<Table> remove(Long id) {
        return Optional.ofNullable(
            ddb.getTable(tablesTableName)
               .deleteItem("key", "version0", "id", id)
               .getItem()
        ).map((Item item) ->
            gson.fromJson(item.toJSON(), Table.class)
        );
    }

    public void remove(Table table) {
        mapper.delete(TableItem.fromGSON(table));
    }
}
