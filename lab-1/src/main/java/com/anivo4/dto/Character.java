package com.anivo4.dto;

public class Character implements Comparable<Character> {

    private String id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private String originName;
    private String locationName;
    private String created;

    public Character() {
    }

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

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public int compareTo(Character other) {
        return this.name.compareTo(other.name);
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