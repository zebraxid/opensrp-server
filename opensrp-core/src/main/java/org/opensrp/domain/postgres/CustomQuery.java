package org.opensrp.domain.postgres;

public class CustomQuery {
	private int id;
	private String uuid;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return "CustomQuery{" +
				"id=" + id +
				", uuid='" + uuid + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
