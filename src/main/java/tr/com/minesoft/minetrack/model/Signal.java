package tr.com.minesoft.minetrack.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

@Getter
@Setter
@Builder
public class Signal {
	private int rssi;
	private DateTime dt;
	private String rid;
	private String tid;
}
