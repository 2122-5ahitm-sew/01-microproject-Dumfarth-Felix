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

    private final DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    private final DateFormat formatterSql = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Inject
    EventRepository eventRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Event> allEvents() throws JsonProcessingException {
        return eventRepository.listAll();
    }


    @Transactional
    @POST
    @Path("addEvent")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Event addEvent(JsonObject eventJson) throws JsonProcessingException, ParseException {
        String name = eventJson.getString("name");
        String date = eventJson.getString("date");
        Event e = new Event();
        e.setDate(formatter.parse(date));
        e.setName(name);
        eventRepository.persist(e);
        return e;
    }

    @DELETE
    @Path("delEvent")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Long delEvent(JsonObject eventJson) throws JsonProcessingException {
        String eventName = eventJson.getString("name");
        return eventRepository.delete("name", eventName);
    }

    @PATCH
    @Path("updateDate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public int updateDate(JsonObject eventJson) throws JsonProcessingException, ParseException {
        String name = eventJson.getString("name");
        String newDate = eventJson.getString("newDate");
        return Event.update("date = '" + formatterSql.format(formatter.parse(newDate)) + "' where name = ?1", name);
    }

    @GET
    @Path("findEventName")
    @Produces(MediaType.APPLICATION_JSON)
    public Event findEventName(@QueryParam("name") String name) throws JsonProcessingException {
        return eventRepository.find("name", name).firstResult();
    }

    @GET
    @Path("findEventDate")
    @Produces(MediaType.TEXT_PLAIN)
    public List<Event> findEventDate(@QueryParam("date") String date) throws JsonProcessingException, ParseException {
        return eventRepository.find("date", formatter.parse(date)).list();
    }


}
