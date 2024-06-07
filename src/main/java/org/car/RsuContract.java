package org.car;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;

@DataType
@Data
@Accessors(chain = true)

public class RsuContract {
    @Transaction
    public Car regCar(final Context ctx, String id, Double rep, Double trust) {
        ChaincodeStub stub = ctx.getStub();
        String carState = stub.getStringState(id);
        if (StringUtils.isNotBlank(carState)) {
            String errorMessage = String.format("Car %s already exists", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage);
        }

        Car car = new Car().setId(id)
                .setRep(rep)
                .setTrust(trust);

        String json = JSON.toJSONString(car);
        stub.putStringState(id, json);
        stub.setEvent("createCarEvent" , org.apache.commons.codec.binary.StringUtils.getBytesUtf8(json));
        return car;
    }
}
