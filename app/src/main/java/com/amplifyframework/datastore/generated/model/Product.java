package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;

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

/** This is an auto generated class representing the Product type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Products")
public final class Product implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField NAME = field("name");
  public static final QueryField BARCODE = field("barcode");
  public static final QueryField STORE = field("productStoreId");
  public static final QueryField QUANTITY = field("quantity");
  public static final QueryField PRICE = field("price");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String name;
  private final @ModelField(targetType="String") String barcode;
  private final @ModelField(targetType="Store") @BelongsTo(targetName = "productStoreId", type = Store.class) Store store;
  private final @ModelField(targetType="Float") Float quantity;
  private final @ModelField(targetType="Float") Float price;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getBarcode() {
      return barcode;
  }
  
  public Store getStore() {
      return store;
  }
  
  public Float getQuantity() {
      return quantity;
  }
  
  public Float getPrice() {
      return price;
  }
  
  private Product(String id, String name, String barcode, Store store, Float quantity, Float price) {
    this.id = id;
    this.name = name;
    this.barcode = barcode;
    this.store = store;
    this.quantity = quantity;
    this.price = price;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Product product = (Product) obj;
      return ObjectsCompat.equals(getId(), product.getId()) &&
              ObjectsCompat.equals(getName(), product.getName()) &&
              ObjectsCompat.equals(getBarcode(), product.getBarcode()) &&
              ObjectsCompat.equals(getStore(), product.getStore()) &&
              ObjectsCompat.equals(getQuantity(), product.getQuantity()) &&
              ObjectsCompat.equals(getPrice(), product.getPrice());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getBarcode())
      .append(getStore())
      .append(getQuantity())
      .append(getPrice())
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
  public static Product justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Product(
      id,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      barcode,
      store,
      quantity,
      price);
  }
  public interface BuildStep {
    Product build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep name(String name);
    BuildStep barcode(String barcode);
    BuildStep store(Store store);
    BuildStep quantity(Float quantity);
    BuildStep price(Float price);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String name;
    private String barcode;
    private Store store;
    private Float quantity;
    private Float price;
    @Override
     public Product build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Product(
          id,
          name,
          barcode,
          store,
          quantity,
          price);
    }
    
    @Override
     public BuildStep name(String name) {
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep barcode(String barcode) {
        this.barcode = barcode;
        return this;
    }
    
    @Override
     public BuildStep store(Store store) {
        this.store = store;
        return this;
    }
    
    @Override
     public BuildStep quantity(Float quantity) {
        this.quantity = quantity;
        return this;
    }
    
    @Override
     public BuildStep price(Float price) {
        this.price = price;
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
    private CopyOfBuilder(String id, String name, String barcode, Store store, Float quantity, Float price) {
      super.id(id);
      super.name(name)
        .barcode(barcode)
        .store(store)
        .quantity(quantity)
        .price(price);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder barcode(String barcode) {
      return (CopyOfBuilder) super.barcode(barcode);
    }
    
    @Override
     public CopyOfBuilder store(Store store) {
      return (CopyOfBuilder) super.store(store);
    }
    
    @Override
     public CopyOfBuilder quantity(Float quantity) {
      return (CopyOfBuilder) super.quantity(quantity);
    }
    
    @Override
     public CopyOfBuilder price(Float price) {
      return (CopyOfBuilder) super.price(price);
    }
  }
  
}
