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
import org.ninthfloor.bj21.gson.Hand;

/**
 * Dealer
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2020-05-19T09:07:39.626-04:00[America/New_York]")
public class Dealer {
  public static final String SERIALIZED_NAME_TABLE_ID = "tableId";
  @SerializedName(SERIALIZED_NAME_TABLE_ID)
  private Long tableId;

  public static final String SERIALIZED_NAME_CHIPS = "chips";
  @SerializedName(SERIALIZED_NAME_CHIPS)
  private Long chips;

  public static final String SERIALIZED_NAME_HAND = "hand";
  @SerializedName(SERIALIZED_NAME_HAND)
  private Hand hand;


  public Dealer tableId(Long tableId) {
    
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


  public Dealer chips(Long chips) {
    
    this.chips = chips;
    return this;
  }

   /**
   * Table hold
   * @return chips
  **/
  @ApiModelProperty(required = true, value = "Table hold")

  public Long getChips() {
    return chips;
  }


  public void setChips(Long chips) {
    this.chips = chips;
  }


  public Dealer hand(Hand hand) {
    
    this.hand = hand;
    return this;
  }

   /**
   * Get hand
   * @return hand
  **/
  @ApiModelProperty(required = true, value = "")

  public Hand getHand() {
    return hand;
  }


  public void setHand(Hand hand) {
    this.hand = hand;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Dealer dealer = (Dealer) o;
    return Objects.equals(this.tableId, dealer.tableId) &&
        Objects.equals(this.chips, dealer.chips) &&
        Objects.equals(this.hand, dealer.hand);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tableId, chips, hand);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Dealer {\n");
    sb.append("    tableId: ").append(toIndentedString(tableId)).append("\n");
    sb.append("    chips: ").append(toIndentedString(chips)).append("\n");
    sb.append("    hand: ").append(toIndentedString(hand)).append("\n");
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

