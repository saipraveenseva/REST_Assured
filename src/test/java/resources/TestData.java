package resources;

import Eleven.AddPlaceSerialization;
import Eleven.Location;

import java.util.ArrayList;
import java.util.List;

public class TestData {

    public AddPlaceSerialization addPlacePayload() {
        AddPlaceSerialization aps = new AddPlaceSerialization();

        aps.setAccuracy(100);
        aps.setAddress("Ferry road, Ibrahimpatnam");
        aps.setLanguage("Telugu");
        aps.setPhone_number("9959958191");
        aps.setName("Sai praveen Seva");
        aps.setWebsite("www.saipraveenseva.com");

        List<String> myList = new ArrayList<String>();
        myList.add("Seva home");

        aps.setTypes(myList);
        Location lo = new Location();
        lo.setLat(-38.456789);
        lo.setLng(-38.456789);

        aps.setLocation(lo);

        return aps;
    }
}
