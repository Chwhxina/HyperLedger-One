package org.hepeng;

import com.alibaba.fastjson.JSON;
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

import java.util.Random;

/**
 * author he peng
 * date 2022/1/19 14:56
 */


@Contract(
        name = "Cat",
        info = @Info(
                title = "Cat contract",
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
public class CatContract implements ContractInterface {


    @Transaction
    public void initLedger(final Context ctx) {

        ChaincodeStub stub = ctx.getStub();
        for (int i = 0; i < 10; i++ ) {
            Cat cat = new Cat().setName("cat-" + i)
                    .setAge(new Random().nextInt())
                    .setBreed("橘猫")
                    .setColor("橘黄色");
            stub.putStringState(cat.getName() , JSON.toJSONString(cat));
        }

    }

    @Transaction
    public Cat queryCat(final Context ctx, final String key) {

        ChaincodeStub stub = ctx.getStub();
        String catState = stub.getStringState(key);

        if (StringUtils.isBlank(catState)) {
            String errorMessage = String.format("Cat %s does not exist", key);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage);
        }

        return JSON.parseObject(catState , Cat.class);
    }

    @Transaction
    public Cat createCat(final Context ctx, final String key , String name , Integer age , String color , String breed) {

        ChaincodeStub stub = ctx.getStub();
        String catState = stub.getStringState(key);

        if (StringUtils.isNotBlank(catState)) {
            String errorMessage = String.format("Cat %s already exists", key);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage);
        }

        Cat cat = new Cat().setName(name)
                .setAge(age)
                .setBreed(breed)
                .setColor(color);

        stub.putStringState(key, JSON.toJSONString(cat));
        return cat;
    }

    @Transaction
    public Cat updateCat(final Context ctx, final String key , String name , Integer age , String color , String breed) {

        ChaincodeStub stub = ctx.getStub();
        String catState = stub.getStringState(key);

        if (StringUtils.isBlank(catState)) {
            String errorMessage = String.format("Cat %s does not exist", key);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage);
        }

        Cat cat = new Cat().setName(name)
                .setAge(age)
                .setBreed(breed)
                .setColor(color);

        stub.putStringState(key, JSON.toJSONString(cat));

        return cat;
    }

//    @Transaction
//    public Cat deleteCat(final Context ctx, final String key) {
//
//        ChaincodeStub stub = ctx.getStub();
//        String catState = stub.getStringState(key);
//
//        if (StringUtils.isBlank(catState)) {
//            String errorMessage = String.format("Cat %s does not exist", key);
//            System.out.println(errorMessage);
//            throw new ChaincodeException(errorMessage);
//        }
//
//        stub.delState(key);
//
//        return JSON.parseObject(catState , Cat.class);
//    }

    @Override
    public void beforeTransaction(Context ctx) {
        System.out.println("*************************************** 交易开始前 ***************************************");
    }

    @Override
    public void afterTransaction(Context ctx, Object result) {
        System.out.println("*************************************** 交易后 ***************************************");
        System.out.println("result --------> " + result);
    }
}
