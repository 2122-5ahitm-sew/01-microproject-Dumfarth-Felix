
package at.htl.entity;

import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.assertj.core.api.Assertions.assertThat;


class EventTest {
    private final DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    @Test
    void addInvolvedStore() throws ParseException {
        Event event = new Event( "Sale", formatter.parse("24.2.2002"));
        event.addInvolvedStore(new Store(
                "Mediamarkt",
                1,
                new Person("Max", "M"),
                Category.ELECTRONICS));
        assertThat(event.getInvolvedStores().get(0).getStoreName()).isNotNull().isEqualTo("Mediamarkt");
    }
    @Test
    void testToString() throws ParseException {
        Event event = new Event("Sale", formatter.parse("24.2.2002"));
        assertThat(event.toString()).isEqualTo("Event Sale on Sun Feb 24 00:00:00 CET 2002");
    }
}
