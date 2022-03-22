package org.hepeng;

import lombok.Data;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

/**
 * @author he peng
 * @date 2022/3/22
 */

@DataType
@Data
public class UserInfo {

    @Property
    String key;

    @Property
    String idCard;

    @Property
    String name;

    @Property
    String sex;

    @Property
    String birthday;

    @Property
    String phone;


}
