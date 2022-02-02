package ru.otus.crm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;


@Table("client")
public class Client {

    @Id
    private final Long id;
    @Nonnull
    private final String name;

    @Nonnull
    private final String managerId;

    @Nonnull
    private final Integer orderColumn;

    public Client(String name, String managerId, int orderColumn) {
        this(null, name, managerId, orderColumn);
    }

    @PersistenceConstructor
    public Client(Long id, String name, String managerId, int orderColumn) {
        this.id = id;
        this.name = name;
        this.managerId = managerId;
        this.orderColumn = orderColumn;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getManagerId() {
        return managerId;
    }

    public int getOrderColumn() {
        return orderColumn;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", managerId='" + managerId + '\'' +
                ", orderColumn=" + orderColumn +
                '}';
    }
}
