package org.ninthfloor.bj21.dynamodb;

import java.util.ArrayList;
import java.util.Arrays;
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

public class HandsTest {

    private Hands hands;

    private AmazonDynamoDB ddb;

    final private GsonBuilder gsonBuilder =
        new GsonBuilder();

    private Gson gson;

    @Before
    public void init() {
        gson = gsonBuilder.create();

        ddb = DynamoDBEmbedded.create().amazonDynamoDB();

        hands = new Hands("Hands", ddb, gson);

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
            createTable(ddb, "Hands", attrs, ks, throughput);
    }

    @After
    public void destroy() {
        ddb.shutdown();
    }

    @Test
    public void addTest() {
        org.ninthfloor.bj21.gson.Hand gsonHand =
            new org.ninthfloor.bj21.gson.Hand();

        gsonHand.setId(0L);

        assertEquals(0L, hands.add(gsonHand).getLong("id"));
    }

    @Test
    public void getTest() {
        assertFalse(hands.getById(0L).isPresent());
    }

    @Test
    public void loadTest() {
        assertFalse(hands.load(0L).isPresent());
    }

    @Test
    public void addGetTest() {
        org.ninthfloor.bj21.gson.Hand gsonHand =
            new org.ninthfloor.bj21.gson.Hand();

        org.ninthfloor.bj21.gson.Card card0 =
            new org.ninthfloor.bj21.gson.Card();

        card0.setFace(org.ninthfloor.bj21.gson.Card.FaceEnum.Q);
        card0.setSuit(org.ninthfloor.bj21.gson.Card.SuitEnum.HEART);

        List<org.ninthfloor.bj21.gson.Card> cards = Arrays.asList(
            card0
        );

        List<org.ninthfloor.bj21.gson.Hand.ActionsEnum> actions = Arrays.asList(
            org.ninthfloor.bj21.gson.Hand.ActionsEnum.HIT
        );

        gsonHand.setId(0L);
        gsonHand.setInitial(0L);
        gsonHand.setBet(0L);
        gsonHand.setCards(cards);
        gsonHand.setActions(actions);

        Item addedItem = hands.add(gsonHand);

        assertEquals("version0", hands.getItem(0L).get().getString("key"));

        Optional<HandItem> readItem = hands.load(0L);

        assertTrue(readItem.isPresent());
        assertEquals(Long.valueOf(0L), readItem.get().getInitial());
        assertEquals(Long.valueOf(0L), readItem.get().getBet());

        Optional<org.ninthfloor.bj21.gson.Hand> opt =
            hands.getById(0L);

        assertEquals(Long.valueOf(0L), opt.get().getInitial());
        assertEquals(Long.valueOf(0L), opt.get().getBet());
        assertEquals("Q", opt.get().getCards().get(0).getFace().getValue());
        assertEquals("heart", opt.get().getCards().get(0).getSuit().getValue());
        assertEquals("hit", opt.get().getActions().get(0).getValue());
    }

    @Test
    public void updateTest() {
        org.ninthfloor.bj21.gson.Hand gsonHand =
            new org.ninthfloor.bj21.gson.Hand();

        gsonHand.setId(0L);

        hands.update(gsonHand);
    }

    @Test
    public void addUpdateTest() {
        org.ninthfloor.bj21.gson.Hand gsonHand =
            new org.ninthfloor.bj21.gson.Hand();

        gsonHand.setId(0L);
        gsonHand.setInitial(0L);

        Item item = hands.add(gsonHand);

        gsonHand.setInitial(1L);

        hands.update(gsonHand);
    }

    @Test
    public void removeTest() {
        org.ninthfloor.bj21.gson.Hand gsonHand =
            new org.ninthfloor.bj21.gson.Hand();

        gsonHand.setId(0L);

        hands.remove(gsonHand);
    }

    @Test
    public void addRemoveTest() {
        org.ninthfloor.bj21.gson.Hand gsonHand =
            new org.ninthfloor.bj21.gson.Hand();

        gsonHand.setId(0L);
        gsonHand.setInitial(0L);

        Item item = hands.add(gsonHand);

        gsonHand.setInitial(1L);

        hands.remove(gsonHand);
    }

    @Test
    public void removeIdTest() {
        Optional<org.ninthfloor.bj21.gson.Hand> opt =
            hands.remove(0L);

        assertFalse(opt.isPresent());
    }

    @Test
    public void addRemoveIdTest() {
        org.ninthfloor.bj21.gson.Hand gsonHand =
            new org.ninthfloor.bj21.gson.Hand();

        gsonHand.setId(0L);

        Item item = hands.add(gsonHand);

        Optional<org.ninthfloor.bj21.gson.Hand> opt =
            hands.remove(0L);
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
