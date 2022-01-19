package org.hepeng;

import lombok.Data;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

/**
 * author he peng
 * date 2022/1/19 15:07
 */

@DataType
@Data
public class CatQueryResult {

    @Property
    String key;

    @Property
    Cat cat;


}
