package at.htl.boundry;

import at.htl.entity.Event;
import at.htl.entity.Store;
//import at.htl.repository.EventRepository;
//import at.htl.repository.StoreRepository;
import at.htl.repository.EventRepository;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("events")
public class EventService {

    private ObjectMapper mapper = new ObjectMapper();
    private final DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    @Inject
    EventRepository eventRepository;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String allEvents() throws JsonProcessingException {
        StringBuilder output = new StringBuilder();
        for (PanacheEntityBase e : eventRepository.listAll()) {
            output.append(mapper.writeValueAsString(e)).append("\n");
        }
        return output.toString();
    }


    @POST
    @Path("addEvent")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public String addEvent(JsonObject eventJson) throws JsonProcessingException, ParseException {
        String name = eventJson.getString("name");
        String date = eventJson.getString("date");
        List<Store> storesList = new ArrayList<Store>();
        Event e = new Event();
        e.setDate(formatter.parse(date));
        e.setName(name);
        eventRepository.persist(e);
        return mapper.writeValueAsString(e);
    }

    @DELETE
    @Path("delEvent")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public String delEvent(JsonObject eventJson) throws JsonProcessingException {
        String eventName = eventJson.getString("name");
        long event = eventRepository.delete("name",eventName);
        return mapper.writeValueAsString(event);
    }

    /*@PATCH
    @Path("updateDate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public String updateDate(JsonObject eventJson) throws JsonProcessingException, ParseException {

        String name = eventJson.getString("name");
        String newDate = eventJson.getString("newDate");
        return mapper.writeValueAsString(Event.update("date = '"+newDate+"' where name = ?1",name));
    }

    @GET
    @Path("findEventName")
    @Produces(MediaType.APPLICATION_JSON)
    public String findEventName(@QueryParam("name") String name) throws JsonProcessingException {
        return mapper.writeValueAsString(Event.find("select e from Event e where name='"+name+"'"));
    }

    @GET
    @Path("findEventDate")
    @Produces(MediaType.TEXT_PLAIN)
    public String findEventDate(@QueryParam("date") String date) throws JsonProcessingException, ParseException {
        StringBuilder output = new StringBuilder();
        for (PanacheEntityBase e : Event.find("select e from Event e where date="+date+"").list()) {
            output.append(mapper.writeValueAsString(e)).append("\n");
        }
        return output.toString();
    }*/


}
