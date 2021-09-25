package at.htl.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


class StoreTest {

    @Test
    void testToString() {
        Store store = new Store(
                "Mediamarkt",
                1,
                new Person("Max", "M"),
                Category.ELECTRONICS);
        assertThat(store.toString()).isEqualTo("Store Mediamarkt");
    }
}
