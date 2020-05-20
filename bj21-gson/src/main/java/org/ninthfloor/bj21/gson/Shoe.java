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

/**
 * Shoe
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2020-05-19T09:07:39.626-04:00[America/New_York]")
public class Shoe {
  public static final String SERIALIZED_NAME_TABLE_ID = "tableId";
  @SerializedName(SERIALIZED_NAME_TABLE_ID)
  private Long tableId;

  public static final String SERIALIZED_NAME_DECKS = "decks";
  @SerializedName(SERIALIZED_NAME_DECKS)
  private Long decks;

  public static final String SERIALIZED_NAME_ROUNDS = "rounds";
  @SerializedName(SERIALIZED_NAME_ROUNDS)
  private Long rounds;

  public static final String SERIALIZED_NAME_DISCARD = "discard";
  @SerializedName(SERIALIZED_NAME_DISCARD)
  private Long discard;

  public static final String SERIALIZED_NAME_CUT = "cut";
  @SerializedName(SERIALIZED_NAME_CUT)
  private Long cut;


  public Shoe tableId(Long tableId) {
    
    this.tableId = tableId;
    return this;
  }

   /**
   * Table number
   * @return tableId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Table number")

  public Long getTableId() {
    return tableId;
  }


  public void setTableId(Long tableId) {
    this.tableId = tableId;
  }


  public Shoe decks(Long decks) {
    
    this.decks = decks;
    return this;
  }

   /**
   * Number of decks
   * @return decks
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Number of decks")

  public Long getDecks() {
    return decks;
  }


  public void setDecks(Long decks) {
    this.decks = decks;
  }


  public Shoe rounds(Long rounds) {
    
    this.rounds = rounds;
    return this;
  }

   /**
   * Number of rounds played since last shuffle
   * @return rounds
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Number of rounds played since last shuffle")

  public Long getRounds() {
    return rounds;
  }


  public void setRounds(Long rounds) {
    this.rounds = rounds;
  }


  public Shoe discard(Long discard) {
    
    this.discard = discard;
    return this;
  }

   /**
   * Number of cards in discard pile
   * @return discard
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Number of cards in discard pile")

  public Long getDiscard() {
    return discard;
  }


  public void setDiscard(Long discard) {
    this.discard = discard;
  }


  public Shoe cut(Long cut) {
    
    this.cut = cut;
    return this;
  }

   /**
   * Number of cards ahead of cut card
   * @return cut
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Number of cards ahead of cut card")

  public Long getCut() {
    return cut;
  }


  public void setCut(Long cut) {
    this.cut = cut;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Shoe shoe = (Shoe) o;
    return Objects.equals(this.tableId, shoe.tableId) &&
        Objects.equals(this.decks, shoe.decks) &&
        Objects.equals(this.rounds, shoe.rounds) &&
        Objects.equals(this.discard, shoe.discard) &&
        Objects.equals(this.cut, shoe.cut);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tableId, decks, rounds, discard, cut);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Shoe {\n");
    sb.append("    tableId: ").append(toIndentedString(tableId)).append("\n");
    sb.append("    decks: ").append(toIndentedString(decks)).append("\n");
    sb.append("    rounds: ").append(toIndentedString(rounds)).append("\n");
    sb.append("    discard: ").append(toIndentedString(discard)).append("\n");
    sb.append("    cut: ").append(toIndentedString(cut)).append("\n");
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

