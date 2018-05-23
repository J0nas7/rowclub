package com.rowclub.proto;

import com.rowclub.proto.repository.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class RowingclubApplication {

    public RowingclubApplication()  {
    }

    public static void main(String[] args) throws SQLException {

        //SpringApplication.run(RowingclubApplication.class, args);

        //Test kode til fremvisning hvor UI ikke kan vise funtionalitet (Alt efter denne kommentar skal slettes når UI er tilstrækkelig)
        MemberDbRepository memberDbRepository = new MemberDbRepository();
        BoatDbRepository boatDbRepository = new BoatDbRepository();
        BoatTripLinkDbRepository boatTripLinkDbRepository = new BoatTripLinkDbRepository();
        BoatTripsDbRepository boatTripsDbRepository = new BoatTripsDbRepository();
        PreDetTripsDbRepository preDetTripsDbRepository = new PreDetTripsDbRepository();
        SeasonDbRepository seasonDbRepository = new SeasonDbRepository();
        WarningDbRepository warningDbRepository = new WarningDbRepository();

        //Member (Create, Update, Delete)
        //memberDbRepository.createMember("Lars","Andersen","02/10/1985","05/11/1999","12345678",Boolean.FALSE,Boolean.FALSE,"Junior","No Image");
        //memberDbRepository.updateMember(1,"","","","","87654321",null,null,"","");
        //memberDbRepository.deleteMember(1);

        //Boat (Create, Update, Delete)
        //boatDbRepository.createBoat("Dan","Out-rigger","idle",8);
        //boatDbRepository.updateBoat(1,"","","På vandet",0);
        //boatDbRepository.deleteBoat(1);

        //BoatTrip (Create, Update, Delete)



    }

}
