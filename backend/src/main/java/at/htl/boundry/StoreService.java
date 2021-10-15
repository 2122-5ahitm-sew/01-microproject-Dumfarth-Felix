package at.htl.boundry;

import at.htl.entity.Category;
import at.htl.entity.Person;
import at.htl.entity.Store;
import at.htl.repository.StoreRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("api/stores")
public class StoreService {

    @Inject
    StoreRepository storeRepository;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Store> allStores() throws JsonProcessingException {
        return storeRepository.listAll();
    }


    @Transactional
    @POST
    @Path("addStore")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Store addStore(JsonObject storeJson) throws JsonProcessingException {
        String name = storeJson.getString("name");
        int rent = Integer.parseInt(storeJson.getString("rent"));
        String storekeeper = storeJson.getString("storekeeper");
        String category = storeJson.getString("category");
        Store s = new Store();
        s.setStoreName(name);
        s.setRent(rent);
        s.setCategory(Category.valueOf(category.toUpperCase()));
        String[] nameParts = storekeeper.split(" ");
        Person storekeeperPerson = new Person(nameParts[0], nameParts[1]);
        storekeeperPerson.persist();
        s.setShopkeeper(storekeeperPerson);
        storeRepository.persist(s);
        return s;
    }

    @Transactional
    @POST
    @Path("addStores")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ArrayList<Store> addStore(JsonArray storesJson) throws JsonProcessingException {
        ArrayList<Store> storeList = new ArrayList<>();
        for (JsonValue storeJsonValue :
                storesJson) {
            JsonObject storeJson = storeJsonValue.asJsonObject();
            String name = storeJson.getString("name");
            int rent = Integer.parseInt(storeJson.getString("rent"));
            String storekeeper = storeJson.getString("storekeeper");
            String category = storeJson.getString("category");
            Store s = new Store();
            s.setStoreName(name);
            s.setRent(rent);
            s.setCategory(Category.valueOf(category.toUpperCase()));
            String[] nameParts = storekeeper.split(" ");
            Person storekeeperPerson = new Person(nameParts[0], nameParts[1]);
            storekeeperPerson.persist();
            s.setShopkeeper(storekeeperPerson);
            storeRepository.persist(s);
            storeList.add(s);
        }
        return storeList;
    }

    @Transactional
    @DELETE
    @Path("delStore")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean delStore(JsonObject storeJson) throws JsonProcessingException {
        return storeRepository.deleteById(((long) storeJson.getInt("id")));
    }

    @Transactional
    @PATCH
    @Path("updateRent")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public int updateRent(JsonObject storeJson) throws JsonProcessingException {

        String name = storeJson.getString("name");
        int newRent = Integer.parseInt(storeJson.getString("newRent"));
        return storeRepository.update("update from Store set RENT =" + newRent + " where storename='" + name + "'");
    }

    @GET
    @Path("findStoreName")
    @Produces(MediaType.APPLICATION_JSON)
    public Store findStore(@QueryParam("name") String name) throws JsonProcessingException {
        return storeRepository.find("storename", name).firstResult();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Store findStoreId(@PathParam("id") long id) throws JsonProcessingException {
        return storeRepository.findById(id);
    }


}
