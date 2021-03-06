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
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Gets or Sets Actions
 */
@JsonAdapter(Actions.Adapter.class)
public enum Actions {
  
  HIT("hit"),
  
  STAND("stand"),
  
  DOUBLE("double"),
  
  SPLIT("split"),
  
  SURRENDER("surrender");

  private String value;

  Actions(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  public static Actions fromValue(String value) {
    for (Actions b : Actions.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }

  public static class Adapter extends TypeAdapter<Actions> {
    @Override
    public void write(final JsonWriter jsonWriter, final Actions enumeration) throws IOException {
      jsonWriter.value(enumeration.getValue());
    }

    @Override
    public Actions read(final JsonReader jsonReader) throws IOException {
      String value = jsonReader.nextString();
      return Actions.fromValue(value);
    }
  }
}

