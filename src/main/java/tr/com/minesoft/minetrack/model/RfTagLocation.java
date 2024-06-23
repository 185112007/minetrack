package tr.com.minesoft.minetrack.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RfTagLocation {
    private long id;
    private double x;
    private double y;
    private String tagId;
    private String fullName;
    private LocalDateTime dateTime;
}
