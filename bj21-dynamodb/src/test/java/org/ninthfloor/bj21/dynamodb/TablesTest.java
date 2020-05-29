package org.ninthfloor.bj21.dynamodb;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

public class TablesTest {

    private Tables tables;

    private AmazonDynamoDB ddb;

    final private GsonBuilder gsonBuilder =
        new GsonBuilder();

    private Gson gson;

    @Before
    public void init() {
        gson = gsonBuilder.create();

        ddb = DynamoDBEmbedded.create().amazonDynamoDB();

        tables = new Tables("Tables", ddb, gson);

        List<AttributeDefinition> attrs =
            new ArrayList<AttributeDefinition>();
        attrs.add(new AttributeDefinition("key", ScalarAttributeType.S));
        attrs.add(new AttributeDefinition("id", ScalarAttributeType.N));

        List<KeySchemaElement> ks =
            new ArrayList<KeySchemaElement>();
        ks.add(new KeySchemaElement("key", KeyType.HASH));
        ks.add(new KeySchemaElement("id", KeyType.RANGE));

        ProvisionedThroughput throughput =
            new ProvisionedThroughput(1000L, 1000L);

        CreateTableResult res =
            createTable(ddb, "Tables", attrs, ks, throughput);
    }

    @After
    public void destroy() {
        ddb.shutdown();
    }

    @Test
    public void addTest() {
        org.ninthfloor.bj21.gson.Table gsonTable =
            new org.ninthfloor.bj21.gson.Table();

        gsonTable.setId(0L);

        assertEquals(0L, tables.add(gsonTable).getLong("id"));
    }

    @Test
    public void getTest() {
        assertFalse(tables.getById(0L).isPresent());
    }

    @Test
    public void addGetTest() {
        org.ninthfloor.bj21.gson.Table gsonTable =
            new org.ninthfloor.bj21.gson.Table();

        gsonTable.setId(0L);
        gsonTable.setDecks(0L);
        gsonTable.setSeats(0L);

        Item addedItem = tables.add(gsonTable);

        assertEquals("version0", tables.getItem(0L).get().getString("key"));

        Optional<TableItem> readItem = tables.load(0L);

        assertTrue(readItem.isPresent());
        assertEquals(Long.valueOf(0L), readItem.get().getDecks());
        assertEquals(Long.valueOf(0L), readItem.get().getSeats());

        Optional<org.ninthfloor.bj21.gson.Table> opt =
            tables.getById(0L);

        assertEquals(Long.valueOf(0L), opt.get().getDecks());
        assertEquals(Long.valueOf(0L), opt.get().getSeats());
    }

    @Test
    public void updateTest() {
        org.ninthfloor.bj21.gson.Table gsonTable =
            new org.ninthfloor.bj21.gson.Table();

        gsonTable.setId(0L);

        tables.update(gsonTable);
    }

    @Test
    public void addUpdateTest() {
        org.ninthfloor.bj21.gson.Table gsonTable =
            new org.ninthfloor.bj21.gson.Table();

        gsonTable.setId(0L);
        gsonTable.setDecks(0L);

        Item item = tables.add(gsonTable);

        gsonTable.setDecks(1L);

        tables.update(gsonTable);
    }

    @Test
    public void removeTest() {
        org.ninthfloor.bj21.gson.Table gsonTable =
            new org.ninthfloor.bj21.gson.Table();

        gsonTable.setId(0L);

        tables.remove(gsonTable);
    }

    @Test
    public void addRemoveTest() {
        org.ninthfloor.bj21.gson.Table gsonTable =
            new org.ninthfloor.bj21.gson.Table();

        gsonTable.setId(0L);
        gsonTable.setDecks(0L);

        Item item = tables.add(gsonTable);

        gsonTable.setDecks(1L);

        tables.remove(gsonTable);
    }

    @Test
    public void removeIdTest() {
        Optional<org.ninthfloor.bj21.gson.Table> opt =
            tables.remove(0L);

        assertFalse(opt.isPresent());
    }

    @Test
    public void addRemoveIdTest() {
        org.ninthfloor.bj21.gson.Table gsonTable =
            new org.ninthfloor.bj21.gson.Table();

        gsonTable.setId(0L);

        Item item = tables.add(gsonTable);

        Optional<org.ninthfloor.bj21.gson.Table> opt =
            tables.remove(0L);
    }

    private CreateTableResult createTable(
        AmazonDynamoDB ddb,
        String tableName,
        List<AttributeDefinition> attrs,
        List<KeySchemaElement> ks,
        ProvisionedThroughput throughput
    ) {

        CreateTableRequest request =
            new CreateTableRequest()
            .withTableName(tableName)
            .withAttributeDefinitions(attrs)
            .withKeySchema(ks)
            .withProvisionedThroughput(throughput);

        return ddb.createTable(request);
    }
}
