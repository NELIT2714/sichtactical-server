package dev.nelit.server.entity.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("tbl_events_attendance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventAttendance {

    @Id
    @Column("id_attendance")
    private Integer idAttendance;

    @Column("id_event")
    private Integer idEvent;

    @Column("id_user")
    private Integer idUser;

    @Column("attended")
    private Boolean attended;

    @Column("marked_by")
    private Integer markedBy;

    @Column("marked_at")
    private LocalDateTime markedAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}