package ma.toudertcolis.delivery_service.dto;

import lombok.Builder;
import lombok.Data;

@Builder @Data
public class Livreur {
    private Long id;
    private String name;
    private String phoneNumber;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;
    private String location;
    private Integer numberCommands;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getNumberCommands() {
        return numberCommands;
    }

    public void setNumberCommands(Integer numberCommands) {
        this.numberCommands = numberCommands;
    }

    @Override
    public String toString() {
        return "Livreur{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", location='" + location + '\'' +
                ", numberCommands=" + numberCommands +
                '}';
    }
}
