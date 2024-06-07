package org.car;

import org.hyperledger.fabric.contract.annotation.Property;

public class RSU {
    @Property
    String id;

    @Property
    Double trust;

    @Property
    Boolean isPeer;

    @Property
    Double importance;
}
