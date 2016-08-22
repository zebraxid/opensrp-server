package org.opensrp.camp.dto;

import java.util.Set;

import org.codehaus.jackson.annotate.JsonProperty;
import org.opensrp.camp.dao.CampDate;

public class CampDTO {
	
	@JsonProperty("session_name")
	private String session_name;
	
	@JsonProperty("session_location")
	private String session_location;
	
	@JsonProperty("total_hh")
	private String total_hh;
	
	@JsonProperty("total_population")
	private String total_population;
	
	@JsonProperty("total_adolescent")
	private String total_adolescent;
	
	@JsonProperty("total_women")
	private String total_women;
	
	@JsonProperty("total_child0")
	private String total_child0;
	
	@JsonProperty("total_child1")
	private String total_child1;
	
	@JsonProperty("total_child2")
	private String total_child2;
	
	@JsonProperty("username")
	private String username;
	
	@JsonProperty("contact")
	private String contact;
	
	@JsonProperty("created")
	private String created;
	
	@JsonProperty("user")
	private String user;
	
	@JsonProperty("camp_dates")
	private Set<CampDate> camp_dates;
	
	public CampDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public CampDTO(String session_name, String session_location, String total_hh, String total_population,
	    String total_adolescent, String total_women, String total_child0, String total_child1, String total_child2,
	    String username, String contact, String created, String user, Set<CampDate> camp_dates) {
		super();
		this.session_name = session_name;
		this.session_location = session_location;
		this.total_hh = total_hh;
		this.total_population = total_population;
		this.total_adolescent = total_adolescent;
		this.total_women = total_women;
		this.total_child0 = total_child0;
		this.total_child1 = total_child1;
		this.total_child2 = total_child2;
		this.username = username;
		this.contact = contact;
		this.created = created;
		this.user = user;
		this.camp_dates = camp_dates;
	}
	
	public String getSession_name() {
		return session_name;
	}
	
	public void setSession_name(String session_name) {
		this.session_name = session_name;
	}
	
	public String getSession_location() {
		return session_location;
	}
	
	public void setSession_location(String session_location) {
		this.session_location = session_location;
	}
	
	public String getTotal_hh() {
		return total_hh;
	}
	
	public void setTotal_hh(String total_hh) {
		this.total_hh = total_hh;
	}
	
	public String getTotal_population() {
		return total_population;
	}
	
	public void setTotal_population(String total_population) {
		this.total_population = total_population;
	}
	
	public String getTotal_adolescent() {
		return total_adolescent;
	}
	
	public void setTotal_adolescent(String total_adolescent) {
		this.total_adolescent = total_adolescent;
	}
	
	public String getTotal_women() {
		return total_women;
	}
	
	public void setTotal_women(String total_women) {
		this.total_women = total_women;
	}
	
	public String getTotal_child0() {
		return total_child0;
	}
	
	public void setTotal_child0(String total_child0) {
		this.total_child0 = total_child0;
	}
	
	public String getTotal_child1() {
		return total_child1;
	}
	
	public void setTotal_child1(String total_child1) {
		this.total_child1 = total_child1;
	}
	
	public String getTotal_child2() {
		return total_child2;
	}
	
	public void setTotal_child2(String total_child2) {
		this.total_child2 = total_child2;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getContact() {
		return contact;
	}
	
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public String getCreated() {
		return created;
	}
	
	public void setCreated(String created) {
		this.created = created;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public Set<CampDate> getCamp_dates() {
		return camp_dates;
	}
	
	public void setCamp_dates(Set<CampDate> camp_dates) {
		this.camp_dates = camp_dates;
	}

	@Override
    public String toString() {
	    return "CampDTO [session_name=" + session_name + ", session_location=" + session_location + ", total_hh=" + total_hh
	            + ", total_population=" + total_population + ", total_adolescent=" + total_adolescent + ", total_women="
	            + total_women + ", total_child0=" + total_child0 + ", total_child1=" + total_child1 + ", total_child2="
	            + total_child2 + ", username=" + username + ", contact=" + contact + ", created=" + created + ", user="
	            + user + ", camp_dates=" + camp_dates + "]";
    }

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((camp_dates == null) ? 0 : camp_dates.hashCode());
	    result = prime * result + ((contact == null) ? 0 : contact.hashCode());
	    result = prime * result + ((created == null) ? 0 : created.hashCode());
	    result = prime * result + ((session_location == null) ? 0 : session_location.hashCode());
	    result = prime * result + ((session_name == null) ? 0 : session_name.hashCode());
	    result = prime * result + ((total_adolescent == null) ? 0 : total_adolescent.hashCode());
	    result = prime * result + ((total_child0 == null) ? 0 : total_child0.hashCode());
	    result = prime * result + ((total_child1 == null) ? 0 : total_child1.hashCode());
	    result = prime * result + ((total_child2 == null) ? 0 : total_child2.hashCode());
	    result = prime * result + ((total_hh == null) ? 0 : total_hh.hashCode());
	    result = prime * result + ((total_population == null) ? 0 : total_population.hashCode());
	    result = prime * result + ((total_women == null) ? 0 : total_women.hashCode());
	    result = prime * result + ((user == null) ? 0 : user.hashCode());
	    result = prime * result + ((username == null) ? 0 : username.hashCode());
	    return result;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null)
		    return false;
	    if (getClass() != obj.getClass())
		    return false;
	    CampDTO other = (CampDTO) obj;
	    if (camp_dates == null) {
		    if (other.camp_dates != null)
			    return false;
	    } else if (!camp_dates.equals(other.camp_dates))
		    return false;
	    if (contact == null) {
		    if (other.contact != null)
			    return false;
	    } else if (!contact.equals(other.contact))
		    return false;
	    if (created == null) {
		    if (other.created != null)
			    return false;
	    } else if (!created.equals(other.created))
		    return false;
	    if (session_location == null) {
		    if (other.session_location != null)
			    return false;
	    } else if (!session_location.equals(other.session_location))
		    return false;
	    if (session_name == null) {
		    if (other.session_name != null)
			    return false;
	    } else if (!session_name.equals(other.session_name))
		    return false;
	    if (total_adolescent == null) {
		    if (other.total_adolescent != null)
			    return false;
	    } else if (!total_adolescent.equals(other.total_adolescent))
		    return false;
	    if (total_child0 == null) {
		    if (other.total_child0 != null)
			    return false;
	    } else if (!total_child0.equals(other.total_child0))
		    return false;
	    if (total_child1 == null) {
		    if (other.total_child1 != null)
			    return false;
	    } else if (!total_child1.equals(other.total_child1))
		    return false;
	    if (total_child2 == null) {
		    if (other.total_child2 != null)
			    return false;
	    } else if (!total_child2.equals(other.total_child2))
		    return false;
	    if (total_hh == null) {
		    if (other.total_hh != null)
			    return false;
	    } else if (!total_hh.equals(other.total_hh))
		    return false;
	    if (total_population == null) {
		    if (other.total_population != null)
			    return false;
	    } else if (!total_population.equals(other.total_population))
		    return false;
	    if (total_women == null) {
		    if (other.total_women != null)
			    return false;
	    } else if (!total_women.equals(other.total_women))
		    return false;
	    if (user == null) {
		    if (other.user != null)
			    return false;
	    } else if (!user.equals(other.user))
		    return false;
	    if (username == null) {
		    if (other.username != null)
			    return false;
	    } else if (!username.equals(other.username))
		    return false;
	    return true;
    }
	
}
