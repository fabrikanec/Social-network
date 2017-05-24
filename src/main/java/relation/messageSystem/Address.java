package main.java.relation.messageSystem;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * An object similars to Service instance.
 */
public class Address {
    private static AtomicInteger abonentCreator;
    private final int abonentId;

    public Address() {
        abonentCreator = new AtomicInteger();
        this.abonentId = abonentCreator.incrementAndGet();
    }

    @Override
    public int hashCode() { return abonentId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        return abonentId == address.abonentId;

    }
}