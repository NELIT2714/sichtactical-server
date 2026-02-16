package dev.nelit.server.entity.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "tbl_events_locations")
public class EventLocation {

    @Id
    @JsonProperty("id_location")
    @Column("id_location")
    private Integer idLocation;

    @JsonProperty("name")
    @Column("name")
    private String name;

    @JsonProperty("address")
    @Column("address")
    private String address;

    @JsonProperty("google_maps")
    @Column("google_maps")
    private String googleMaps;

    public EventLocation(String name, String address, String googleMaps) {
        this.name = name;
        this.address = address;
        this.googleMaps = googleMaps;
    }

    public Integer getIdLocation() {
        return idLocation;
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
