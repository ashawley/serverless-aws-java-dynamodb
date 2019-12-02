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


package org.ninthfloor.bj21.api.gson;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Gets or Sets Suits
 */
@JsonAdapter(Suits.Adapter.class)
public enum Suits {
  
  SPADE("spade"),
  
  HEART("heart"),
  
  DIAMOND("diamond"),
  
  CLUB("club");

  private String value;

  Suits(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  public static Suits fromValue(String value) {
    for (Suits b : Suits.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }

  public static class Adapter extends TypeAdapter<Suits> {
    @Override
    public void write(final JsonWriter jsonWriter, final Suits enumeration) throws IOException {
      jsonWriter.value(enumeration.getValue());
    }

    @Override
    public Suits read(final JsonReader jsonReader) throws IOException {
      String value = jsonReader.nextString();
      return Suits.fromValue(value);
    }
  }
}

