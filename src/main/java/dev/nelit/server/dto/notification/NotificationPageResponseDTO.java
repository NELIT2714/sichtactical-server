package dev.nelit.server.dto.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;

import java.util.List;

public class NotificationPageResponseDTO {

    @JsonProperty("notifications")
    private List<NotificationResponseDTO> content;

    @Min(0)
    @JsonProperty("total_elements")
    private long totalElements;

    @Min(0)
    @JsonProperty("total_pages")
    private int totalPages;

    @Min(0)
    @JsonProperty("page")
    private int page;

    @Min(0)
    @JsonProperty("size")
    private int size;

    public NotificationPageResponseDTO(List<NotificationResponseDTO> content, long totalElements, int totalPages, int page, int size) {
        this.content = content;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.page = page;
        this.size = size;
    }

    public List<NotificationResponseDTO> getContent() {
        return content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}
