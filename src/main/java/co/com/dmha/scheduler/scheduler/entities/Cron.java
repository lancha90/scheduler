package co.com.dmha.scheduler.scheduler.entities;

public class Cron {

	private String id;
	private boolean status;
	private String name;
	private String storeType;

	public Cron(String id, boolean status, String name, String storeType) {
		super();
		this.id = id;
		this.status = status;
		this.name = name;
		this.storeType = storeType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	@Override
	public String toString() {
		return "Cron [id=" + id + ", status=" + status + ", name=" + name + ", storeType=" + storeType + "]";
	}

}
