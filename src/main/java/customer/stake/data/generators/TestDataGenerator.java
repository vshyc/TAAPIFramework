package customer.stake.data.generators;

import com.github.javafaker.Faker;

import java.util.Random;

public class TestDataGenerator {

    public Faker faker(){
        return Faker.instance();
    }

    public Random random(){
        return new Random();
    }
}
