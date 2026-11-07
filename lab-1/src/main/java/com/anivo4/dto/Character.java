package com.anivo4.dto;

public class Character {

    private String id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private String originName;
    private String locationName;
    private String created;

    public Character(String[] fields) {
        this.id = fields[0];
        this.name = fields[1];
        this.status = fields[2];
        this.species = fields[3];
        this.type = fields[4];
        this.gender = fields[5];
        this.originName = fields[6];
        this.locationName = fields[7];
        this.created = fields[8];
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getSpecies() {
        return species;
    }

    public String getType() {
        return type;
    }

    public String getGender() {
        return gender;
    }

    public String getOriginName() {
        return originName;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getCreated() {
        return created;
    }

    @Override
    public String toString() {
        return "Character{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", species='" + species + '\'' +
                ", type='" + type + '\'' +
                ", gender='" + gender + '\'' +
                ", originName='" + originName + '\'' +
                ", locationName='" + locationName + '\'' +
                ", created='" + created + '\'' +
                '}';
    }
}
