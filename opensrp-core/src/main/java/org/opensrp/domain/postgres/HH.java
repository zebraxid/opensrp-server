package org.opensrp.domain.postgres;

public class HH {
 public int memberCount;
 public String first_name;
 public String last_name;
public int getMemberCount() {
	return memberCount;
}
public void setMemberCount(int memberCount) {
	this.memberCount = memberCount;
}
public String getFirst_name() {
	return first_name;
}
public void setFirst_name(String first_name) {
	this.first_name = first_name;
}
public String getLast_name() {
	return last_name;
}
public void setLast_name(String last_name) {
	this.last_name = last_name;
}
@Override
public String toString() {
	return "HH [memberCount=" + memberCount + ", first_name=" + first_name
			+ ", last_name=" + last_name + "]";
}
 
}
