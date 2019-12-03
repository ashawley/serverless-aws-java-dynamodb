/*
 * Newrow 0.1.0
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

/**
 * Table
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2019-12-03T12:21:26.249-05:00[America/New_York]")
public class Table {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private Long id;

  public static final String SERIALIZED_NAME_DECKS = "decks";
  @SerializedName(SERIALIZED_NAME_DECKS)
  private Long decks;

  public static final String SERIALIZED_NAME_SEATS = "seats";
  @SerializedName(SERIALIZED_NAME_SEATS)
  private Long seats;

  public static final String SERIALIZED_NAME_PLAYERS = "players";
  @SerializedName(SERIALIZED_NAME_PLAYERS)
  private Long players;

  public static final String SERIALIZED_NAME_MINIMUM = "minimum";
  @SerializedName(SERIALIZED_NAME_MINIMUM)
  private Long minimum;

  public static final String SERIALIZED_NAME_MAXIMUM = "maximum";
  @SerializedName(SERIALIZED_NAME_MAXIMUM)
  private Long maximum;

  public static final String SERIALIZED_NAME_ROUNDS = "rounds";
  @SerializedName(SERIALIZED_NAME_ROUNDS)
  private Long rounds;


  public Table id(Long id) {
    
    this.id = id;
    return this;
  }

   /**
   * Table number
   * @return id
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Table number")

  public Long getId() {
    return id;
  }


  public void setId(Long id) {
    this.id = id;
  }


  public Table decks(Long decks) {
    
    this.decks = decks;
    return this;
  }

   /**
   * Number of decks
   * @return decks
  **/
  @ApiModelProperty(required = true, value = "Number of decks")

  public Long getDecks() {
    return decks;
  }


  public void setDecks(Long decks) {
    this.decks = decks;
  }


  public Table seats(Long seats) {
    
    this.seats = seats;
    return this;
  }

   /**
   * Number of seats at the table (default: 7)
   * @return seats
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Number of seats at the table (default: 7)")

  public Long getSeats() {
    return seats;
  }


  public void setSeats(Long seats) {
    this.seats = seats;
  }


  public Table players(Long players) {
    
    this.players = players;
    return this;
  }

   /**
   * Number of players at the table (default: 0)
   * @return players
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Number of players at the table (default: 0)")

  public Long getPlayers() {
    return players;
  }


  public void setPlayers(Long players) {
    this.players = players;
  }


  public Table minimum(Long minimum) {
    
    this.minimum = minimum;
    return this;
  }

   /**
   * Minimum bet
   * @return minimum
  **/
  @ApiModelProperty(required = true, value = "Minimum bet")

  public Long getMinimum() {
    return minimum;
  }


  public void setMinimum(Long minimum) {
    this.minimum = minimum;
  }


  public Table maximum(Long maximum) {
    
    this.maximum = maximum;
    return this;
  }

   /**
   * Maximum bet
   * @return maximum
  **/
  @ApiModelProperty(required = true, value = "Maximum bet")

  public Long getMaximum() {
    return maximum;
  }


  public void setMaximum(Long maximum) {
    this.maximum = maximum;
  }


  public Table rounds(Long rounds) {
    
    this.rounds = rounds;
    return this;
  }

   /**
   * Number of rounds played
   * @return rounds
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Number of rounds played")

  public Long getRounds() {
    return rounds;
  }


  public void setRounds(Long rounds) {
    this.rounds = rounds;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Table table = (Table) o;
    return Objects.equals(this.id, table.id) &&
        Objects.equals(this.decks, table.decks) &&
        Objects.equals(this.seats, table.seats) &&
        Objects.equals(this.players, table.players) &&
        Objects.equals(this.minimum, table.minimum) &&
        Objects.equals(this.maximum, table.maximum) &&
        Objects.equals(this.rounds, table.rounds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, decks, seats, players, minimum, maximum, rounds);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Table {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    decks: ").append(toIndentedString(decks)).append("\n");
    sb.append("    seats: ").append(toIndentedString(seats)).append("\n");
    sb.append("    players: ").append(toIndentedString(players)).append("\n");
    sb.append("    minimum: ").append(toIndentedString(minimum)).append("\n");
    sb.append("    maximum: ").append(toIndentedString(maximum)).append("\n");
    sb.append("    rounds: ").append(toIndentedString(rounds)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

