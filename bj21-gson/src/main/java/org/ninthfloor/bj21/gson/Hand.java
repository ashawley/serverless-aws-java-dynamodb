/*
 * Blackjack
 * Blackjack
 *
 * The version of the OpenAPI document: 0.1.0
 * Contact: aaron.s.hawley@gmail.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.ninthfloor.bj21.gson;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.ninthfloor.bj21.gson.Card;

/**
 * Hand
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2020-10-29T22:25:59.793-04:00[America/New_York]")
public class Hand {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private Long id;

  public static final String SERIALIZED_NAME_PREV = "prev";
  @SerializedName(SERIALIZED_NAME_PREV)
  private Long prev;

  public static final String SERIALIZED_NAME_CARDS = "cards";
  @SerializedName(SERIALIZED_NAME_CARDS)
  private List<Card> cards = new ArrayList<>();

  /**
   * Gets or Sets actions
   */
  @JsonAdapter(ActionsEnum.Adapter.class)
  public enum ActionsEnum {
    HIT("hit"),
    
    STAND("stand"),
    
    DOUBLE("double"),
    
    SPLIT("split"),
    
    SURRENDER("surrender");

    private String value;

    ActionsEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static ActionsEnum fromValue(String value) {
      for (ActionsEnum b : ActionsEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<ActionsEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final ActionsEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public ActionsEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return ActionsEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_ACTIONS = "actions";
  @SerializedName(SERIALIZED_NAME_ACTIONS)
  private List<ActionsEnum> actions = new ArrayList<>();

  public static final String SERIALIZED_NAME_INITIAL = "initial";
  @SerializedName(SERIALIZED_NAME_INITIAL)
  private Long initial;

  public static final String SERIALIZED_NAME_BET = "bet";
  @SerializedName(SERIALIZED_NAME_BET)
  private Long bet;

  public static final String SERIALIZED_NAME_TOTAL = "total";
  @SerializedName(SERIALIZED_NAME_TOTAL)
  private Long total;


  public Hand id(Long id) {
    
    this.id = id;
    return this;
  }

   /**
   * Hand number
   * @return id
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Hand number")

  public Long getId() {
    return id;
  }


  public void setId(Long id) {
    this.id = id;
  }


  public Hand prev(Long prev) {
    
    this.prev = prev;
    return this;
  }

   /**
   * Hand split from
   * @return prev
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Hand split from")

  public Long getPrev() {
    return prev;
  }


  public void setPrev(Long prev) {
    this.prev = prev;
  }


  public Hand cards(List<Card> cards) {
    
    this.cards = cards;
    return this;
  }

  public Hand addCardsItem(Card cardsItem) {
    this.cards.add(cardsItem);
    return this;
  }

   /**
   * Cards in player hand
   * @return cards
  **/
  @ApiModelProperty(required = true, value = "Cards in player hand")

  public List<Card> getCards() {
    return cards;
  }


  public void setCards(List<Card> cards) {
    this.cards = cards;
  }


  public Hand actions(List<ActionsEnum> actions) {
    
    this.actions = actions;
    return this;
  }

  public Hand addActionsItem(ActionsEnum actionsItem) {
    this.actions.add(actionsItem);
    return this;
  }

   /**
   * Action of player hand
   * @return actions
  **/
  @ApiModelProperty(required = true, value = "Action of player hand")

  public List<ActionsEnum> getActions() {
    return actions;
  }


  public void setActions(List<ActionsEnum> actions) {
    this.actions = actions;
  }


  public Hand initial(Long initial) {
    
    this.initial = initial;
    return this;
  }

   /**
   * Initial hand bet
   * @return initial
  **/
  @ApiModelProperty(required = true, value = "Initial hand bet")

  public Long getInitial() {
    return initial;
  }


  public void setInitial(Long initial) {
    this.initial = initial;
  }


  public Hand bet(Long bet) {
    
    this.bet = bet;
    return this;
  }

   /**
   * Current hand bet
   * @return bet
  **/
  @ApiModelProperty(required = true, value = "Current hand bet")

  public Long getBet() {
    return bet;
  }


  public void setBet(Long bet) {
    this.bet = bet;
  }


  public Hand total(Long total) {
    
    this.total = total;
    return this;
  }

   /**
   * Player hand total
   * @return total
  **/
  @ApiModelProperty(required = true, value = "Player hand total")

  public Long getTotal() {
    return total;
  }


  public void setTotal(Long total) {
    this.total = total;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Hand hand = (Hand) o;
    return Objects.equals(this.id, hand.id) &&
        Objects.equals(this.prev, hand.prev) &&
        Objects.equals(this.cards, hand.cards) &&
        Objects.equals(this.actions, hand.actions) &&
        Objects.equals(this.initial, hand.initial) &&
        Objects.equals(this.bet, hand.bet) &&
        Objects.equals(this.total, hand.total);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, prev, cards, actions, initial, bet, total);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Hand {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    prev: ").append(toIndentedString(prev)).append("\n");
    sb.append("    cards: ").append(toIndentedString(cards)).append("\n");
    sb.append("    actions: ").append(toIndentedString(actions)).append("\n");
    sb.append("    initial: ").append(toIndentedString(initial)).append("\n");
    sb.append("    bet: ").append(toIndentedString(bet)).append("\n");
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

