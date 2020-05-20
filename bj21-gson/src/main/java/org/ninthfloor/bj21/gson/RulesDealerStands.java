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
 * RulesDealerStands
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2020-05-19T09:07:39.626-04:00[America/New_York]")
public class RulesDealerStands {
  public static final String SERIALIZED_NAME_TOTAL = "total";
  @SerializedName(SERIALIZED_NAME_TOTAL)
  private Long total;

  public static final String SERIALIZED_NAME_SOFT = "soft";
  @SerializedName(SERIALIZED_NAME_SOFT)
  private Boolean soft;


  public RulesDealerStands total(Long total) {
    
    this.total = total;
    return this;
  }

   /**
   * Total dealer stands on (default: 17)
   * @return total
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Total dealer stands on (default: 17)")

  public Long getTotal() {
    return total;
  }


  public void setTotal(Long total) {
    this.total = total;
  }


  public RulesDealerStands soft(Boolean soft) {
    
    this.soft = soft;
    return this;
  }

   /**
   * Can dealer hit on soft totals? (default: false)
   * @return soft
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Can dealer hit on soft totals? (default: false)")

  public Boolean getSoft() {
    return soft;
  }


  public void setSoft(Boolean soft) {
    this.soft = soft;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RulesDealerStands rulesDealerStands = (RulesDealerStands) o;
    return Objects.equals(this.total, rulesDealerStands.total) &&
        Objects.equals(this.soft, rulesDealerStands.soft);
  }

  @Override
  public int hashCode() {
    return Objects.hash(total, soft);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RulesDealerStands {\n");
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
    sb.append("    soft: ").append(toIndentedString(soft)).append("\n");
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

