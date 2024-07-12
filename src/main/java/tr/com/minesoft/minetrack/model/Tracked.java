package tr.com.minesoft.minetrack.model;
import java.util.Objects;

public class Tracked {
	private String fname;
	private String lname;
	private final String tagId;
	private boolean state;
	private double x;
	private double y;
	private String konum;
	private Signal prevSignal;
	private int prevPointIndex;

	public Tracked(String fname, String lname, String tagId) {
		this.fname = fname;
		this.lname = lname;
		this.tagId = tagId;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getTagId() {
		return tagId;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fname == null) ? 0 : fname.hashCode());
		result = prime * result + ((lname == null) ? 0 : lname.hashCode());
		result = prime * result + ((tagId == null) ? 0 : tagId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Tracked))
			return false;
		Tracked other = (Tracked) obj;
		if (fname == null) {
			if (other.fname != null)
				return false;
		} else if (!fname.equals(other.fname))
			return false;
		if (lname == null) {
			if (other.lname != null)
				return false;
		} else if (!lname.equals(other.lname))
			return false;
		return Objects.equals(tagId, other.tagId);
	}
	public String getKonum() {
		return konum;
	}
	public void setKonum(String konum) {
		this.konum = konum;
	}
	public Signal getPrevSignal() {
		return prevSignal;
	}
	public void setPrevSignal(Signal prevSignal) {
		this.prevSignal = prevSignal;
	}
	public int getPrevPointIndex() {
		return prevPointIndex;
	}
	public void setPrevPointIndex(int prevPointIndex) {
		this.prevPointIndex = prevPointIndex;
	}
	@Override
	public String toString() {
		return fname + " " + lname;
	}
}
