package dev.nelit.server.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EventLocationDTO {

    @JsonProperty("id_location")
    private Integer idLocation;

    @NotBlank
    @Size(max = 70)
    @JsonProperty("name")
    private String name;

    @NotBlank
    @Size(max = 200)
    @JsonProperty("address")
    private String address;

    @Size(max = 60)
    @JsonProperty("google_maps")
    private String googleMaps;

    public Integer getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(Integer idLocation) {
        this.idLocation = idLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGoogleMaps() {
        return googleMaps;
    }

    public void setGoogleMaps(String googleMaps) {
        this.googleMaps = googleMaps;
    }
}