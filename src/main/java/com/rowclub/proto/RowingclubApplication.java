package com.rowclub.proto;

import com.rowclub.proto.model.Boat;
import com.rowclub.proto.model.Member;
import com.rowclub.proto.repository.BoatTripsDbRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class RowingclubApplication {

    public RowingclubApplication()  {
    }

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(RowingclubApplication.class, args);
        

        System.out.println();
    }

}
