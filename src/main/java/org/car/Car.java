package org.car;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

@DataType
@Data
@Accessors(chain = true)
public class Car {
    @Property
    String id;

    @Property
    Double rep;

    @Property
    Double trust;

    public String getId() {
        return id;
    }

    public Double getRep() {
        return rep;
    }

    public Double getTrust() {
        return trust;
    }
}
