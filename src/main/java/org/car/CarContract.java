package org.car;

import com.alibaba.fastjson.JSON;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;

@Contract(
        name = "CarContract",
        info = @Info(
                title = "Car contract",
                description = "The hyperlegendary car contract",
                version = "0.0.1-SNAPSHOT",
                license = @License(
                        name = "Apache 2.0 License",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(
                        email = "f.carr@example.com",
                        name = "F Carr",
                        url = "https://hyperledger.example.com")))
@Default
@Log
public class CarContract implements ContractInterface{
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

    @Transaction
    public Car updateCar(final Context ctx, String id, Double rep, Double trust) {
        ChaincodeStub stub = ctx.getStub();
        String carState = stub.getStringState(id);
        if (StringUtils.isBlank(carState)) {
            String errorMessage = String.format("Car %s does not exists", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage);
        }
        Car car = new Car().setId(id)
                .setRep(rep)
                .setTrust(trust);
        String json = JSON.toJSONString(car);
        stub.putStringState(id, json);
        stub.setEvent("updateCarEvent" , org.apache.commons.codec.binary.StringUtils.getBytesUtf8(json));
        return car;
    }

    @Transaction
    public Car queryCar(final Context ctx, final String id) {

        ChaincodeStub stub = ctx.getStub();
        String carState = stub.getStringState(id);

        if (StringUtils.isBlank(carState)) {
            String errorMessage = String.format("Car %s does not exist", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage);
        }

        return JSON.parseObject(carState , Car.class);
    }
}
