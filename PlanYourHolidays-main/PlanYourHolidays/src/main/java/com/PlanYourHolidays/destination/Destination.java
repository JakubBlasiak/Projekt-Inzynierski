package com.PlanYourHolidays.destination;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table
public class Destination {
    @Id
    @SequenceGenerator(
            name = "destinations_sequence",
            sequenceName =  "destinations_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "destinations_sequence"
    )
    private Long Id;
    private String startPoint;
    private String destinationPoint;
    private LocalDate dateOfStart;
    private LocalDate dateOfFinish;
    private double flightsPrice;
    private String flightCode;
    private double sleepPrice;
    private String hotelName;
    private double bestTotalPrice;

    @Override
    public String toString() {
        return "Destination{" +
                "Id=" + Id +
                ", startPoint='" + startPoint + '\'' +
                ", destinationPoint='" + destinationPoint + '\'' +
                ", dateOfStart=" + dateOfStart +
                ", dateOfFinish=" + dateOfFinish +
                ", flightsPrice=" + flightsPrice +
                ", flightCode='" + flightCode + '\'' +
                ", sleepPrice=" + sleepPrice +
                ", hotelName='" + hotelName + '\'' +
                ", bestTotalPrice=" + bestTotalPrice +
                '}';
    }

    public Destination() {
    }

    public Destination(Long id,
                       String startPoint,
                       String destinationPoint,
                       LocalDate dateOfStart,
                       LocalDate dateOfFinish,
                       float flightsPrice,
                       float sleepPrice,
                       float bestTotalPrice) {
        this.Id = id;
        this.startPoint = startPoint;
        this.destinationPoint = destinationPoint;
        this.dateOfStart = dateOfStart;
        this.dateOfFinish = dateOfFinish;
        this.flightsPrice = flightsPrice;
        this.sleepPrice = sleepPrice;
        this.bestTotalPrice = bestTotalPrice;
    }

    public Destination(String startPoint,
                       String destinationPoint,
                       LocalDate dateOfStart,
                       LocalDate dateOfFinish) {
        this.startPoint = startPoint;
        this.destinationPoint = destinationPoint;
        this.dateOfStart = dateOfStart;
        this.dateOfFinish = dateOfFinish;
    }

    public Destination(String startPoint,
                       String destinationPoint,
                       LocalDate dateOfStart,
                       LocalDate dateOfFinish,
                       Object flightsPrice,
                       String flightCode,
                       Object sleepPrice,
                       String hotelName,
                       Object bestTotalPrice) {
        this.startPoint = startPoint;
        this.destinationPoint = destinationPoint;
        this.dateOfStart = dateOfStart;
        this.dateOfFinish = dateOfFinish;
        this.flightsPrice = (double) flightsPrice;
        this.flightCode = flightCode;
        this.sleepPrice = (double) sleepPrice;
        this.hotelName = hotelName;
        this.bestTotalPrice = (double) bestTotalPrice;
    }
}
