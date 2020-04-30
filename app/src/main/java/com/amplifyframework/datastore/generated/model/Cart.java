package com.amplifyframework.datastore.generated.model;


import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Cart type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Carts")
public final class Cart implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField USER = field("user");
  public static final QueryField PAID = field("paid");
  public static final QueryField STORENAME = field("storename");
  public static final QueryField PRODUCT = field("product");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String user;
  private final @ModelField(targetType="Float") Float paid;
  private final @ModelField(targetType="String") String storename;
  private final @ModelField(targetType="String") String product;
  public String getId() {
      return id;
  }
  
  public String getUser() {
      return user;
  }
  
  public Float getPaid() {
      return paid;
  }
  
  public String getStorename() {
      return storename;
  }
  
  public String getProduct() {
      return product;
  }
  
  private Cart(String id, String user, Float paid, String storename, String product) {
    this.id = id;
    this.user = user;
    this.paid = paid;
    this.storename = storename;
    this.product = product;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Cart cart = (Cart) obj;
      return ObjectsCompat.equals(getId(), cart.getId()) &&
              ObjectsCompat.equals(getUser(), cart.getUser()) &&
              ObjectsCompat.equals(getPaid(), cart.getPaid()) &&
              ObjectsCompat.equals(getStorename(), cart.getStorename()) &&
              ObjectsCompat.equals(getProduct(), cart.getProduct());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUser())
      .append(getPaid())
      .append(getStorename())
      .append(getProduct())
      .toString()
      .hashCode();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static Cart justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Cart(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      user,
      paid,
      storename,
      product);
  }
  public interface BuildStep {
    Cart build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep user(String user);
    BuildStep paid(Float paid);
    BuildStep storename(String storename);
    BuildStep product(String product);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String user;
    private Float paid;
    private String storename;
    private String product;
    @Override
     public Cart build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Cart(
          id,
          user,
          paid,
          storename,
          product);
    }
    
    @Override
     public BuildStep user(String user) {
        this.user = user;
        return this;
    }
    
    @Override
     public BuildStep paid(Float paid) {
        this.paid = paid;
        return this;
    }
    
    @Override
     public BuildStep storename(String storename) {
        this.storename = storename;
        return this;
    }
    
    @Override
     public BuildStep product(String product) {
        this.product = product;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String user, Float paid, String storename, String product) {
      super.id(id);
      super.user(user)
        .paid(paid)
        .storename(storename)
        .product(product);
    }
    
    @Override
     public CopyOfBuilder user(String user) {
      return (CopyOfBuilder) super.user(user);
    }
    
    @Override
     public CopyOfBuilder paid(Float paid) {
      return (CopyOfBuilder) super.paid(paid);
    }
    
    @Override
     public CopyOfBuilder storename(String storename) {
      return (CopyOfBuilder) super.storename(storename);
    }
    
    @Override
     public CopyOfBuilder product(String product) {
      return (CopyOfBuilder) super.product(product);
    }
  }
  
}
