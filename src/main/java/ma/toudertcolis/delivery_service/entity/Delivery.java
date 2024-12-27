package ma.toudertcolis.delivery_service.entity;

import lombok.*;
import ma.toudertcolis.delivery_service.dto.Livreur;
import ma.toudertcolis.delivery_service.dto.Person;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "deliveries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
    @Id
    private String _id;

    private String trackingNumber;

    private LocalDateTime pickupTime;
    private LocalDateTime deliveryTime;

    private String status;

    private Long deliveryPersonId;
    private Livreur deliveryPerson;
    private Long customerId;
    private String uid;
    private Double prix;

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    public Person getCustomer() {
        return customer;
    }

    public void setCustomer(Person customer) {
        this.customer = customer;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    private Person customer;
    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public LocalDateTime getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(LocalDateTime pickupTime) {
        this.pickupTime = pickupTime;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status.toString();
    }

    public Long getDeliveryPersonId() {
        return deliveryPersonId;
    }

    public void setDeliveryPersonId(Long deliveryPersonId) {
        this.deliveryPersonId = deliveryPersonId;
    }

    public Livreur getDeliveryPerson() {
        return deliveryPerson;
    }

    public void setDeliveryPerson(Livreur deliveryPerson) {
        this.deliveryPerson = deliveryPerson;
    }

    public enum Status {
        IN_TRANSIT,
        DELIVERED
    }
}
