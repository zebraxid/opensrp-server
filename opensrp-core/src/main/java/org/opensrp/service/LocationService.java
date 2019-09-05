package org.opensrp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.api.domain.Location;
import org.opensrp.domain.postgres.CustomQuery;
import org.opensrp.repository.couch.AllLocations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

	private final AllLocations allLocations;

	@Autowired
	public LocationService(AllLocations allLocations) {
		this.allLocations = allLocations;
	}

	public List<Location> getAllLocations() {
		ArrayList<Location> apiLocations = new ArrayList<>();

		List<org.opensrp.domain.Location> locations = allLocations.findAllLocations();

		for (org.opensrp.domain.Location location : locations) {
			org.opensrp.domain.Location parentLocation = location.getParentLocation();

			Location apiParentLocation = new Location().withLocationId(parentLocation.getLocationId())
					.withName(parentLocation.getName()).withTags(parentLocation.getTags())
					//.withAddress(parentLocation.getAddress())
					.withAttributes(parentLocation.getAttributes()).withIdentifiers(parentLocation.getIdentifiers());

			Location apiLocation = new Location().withLocationId(location.getLocationId()).withName(location.getName())
					.withParentLocation(apiParentLocation).withTags(location.getTags())
					//  .withAddress(location.getAddress())
					.withIdentifiers(location.getIdentifiers()).withAttributes(location.getAttributes());
			
			
				/*
					      apiLocation.withCreator(location.getCreator());
					      apiLocation.withEditor(location.getEditor());
						  apiLocation.withVoider(location.getVoider());
						  apiLocation.withDateCreated(location.getDateCreated());
						  apiLocation.withDateEdited(location.getDateEdited());
						  apiLocation.withDateVoided(location.getDateVoided());
						  apiLocation.withVoided(location.getVoided());
						  apiLocation.withVoidReason(location.getRevision());*/

			apiLocations.add(apiLocation);

		}

		return apiLocations;

	}

	public void addLocation(Location location) {
		org.opensrp.domain.Location domainLocation = new org.opensrp.domain.Location()
				.withLocationId(location.getLocationId()).withName(location.getName()).withTags(location.getTags())
				//.withAddress(location.getAddress())
				.withIdentifiers(location.getIdentifiers()).withAttributes(location.getAttributes());
		allLocations.add(domainLocation);

	}

	// FIXME: 8/28/17 api.Location to domain.Location doens't set id field.
	public void updateLocation(Location location) {
		org.opensrp.domain.Location domainLocation = new org.opensrp.domain.Location()
				.withLocationId(location.getLocationId()).withName(location.getName()).withTags(location.getTags())
				//.withAddress(location.getAddress())
				.withIdentifiers(location.getIdentifiers()).withAttributes(location.getAttributes());
		allLocations.update(domainLocation);

	}

	public JSONArray convertLocationTreeToJSON(List<CustomQuery> treeDTOS) throws JSONException {
		JSONArray locationTree = new JSONArray();

		Map<String, Boolean> mp = new HashMap<>();
		JSONObject object = new JSONObject();
		JSONArray locations = new JSONArray();
		JSONObject fullLocation = new JSONObject();

		int counter = 0;
		String username = "";

		for (CustomQuery treeDTO: treeDTOS) {
			try {
				counter++;
				if (mp.get(treeDTO.getUsername()) == null || !mp.get(treeDTO.getUsername())) {
					if (counter > 1) {
						object.put("username", username);
						object.put("locations", locations);
						locationTree.put(object);
						locations = new JSONArray();
						object = new JSONObject();
					}
					mp.put(treeDTO.getUsername(), true);
				}

				username = treeDTO.getUsername();

				if (treeDTO.getLocationTagName().equalsIgnoreCase("country")) {
					if (counter > 1) {
						fullLocation = setEmptyValues(fullLocation);
						locations.put(fullLocation);
						fullLocation = new JSONObject();
					}
				}

				JSONObject location = new JSONObject();
				location.put("code", treeDTO.getCode());
				location.put("id", treeDTO.getId());
				location.put("name", treeDTO.getName());
				System.out.println("tree");
				System.out.println(treeDTO);
				fullLocation.put(treeDTO.getLocationTagName(), location);

				if (counter == treeDTOS.size()) {
					locations.put(fullLocation);
					object.put("username", username);
					object.put("locations", locations);
					locationTree.put(object);
					object = new JSONObject();
					locations = new JSONArray();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return locationTree;
	}

	private JSONObject getLocationProperty() throws JSONException {
		JSONObject property = new JSONObject();
		property.put("name", "");
		property.put("id", 0);
		property.put("code", "00");
		return property;
	}

	private JSONObject setEmptyValues(JSONObject fullLocation) throws JSONException {
		if (!fullLocation.has("Country")) {
			fullLocation.put("country", getLocationProperty());
		}
		if (!fullLocation.has("Division")) {
			fullLocation.put("division", getLocationProperty());
		}
		if (!fullLocation.has("District")) {
			fullLocation.put("district", getLocationProperty());
		}
		if (!fullLocation.has("City corporation")) {
			fullLocation.put("city_corporation", getLocationProperty());
		}
		if (!fullLocation.has("Upazilla")) {
			fullLocation.put("upazilla", getLocationProperty());
		}
		if (!fullLocation.has("Union")) {
			fullLocation.put("union", getLocationProperty());
		}
		if (!fullLocation.has("Ward")) {
			fullLocation.put("ward", getLocationProperty());
		}
		if (!fullLocation.has("Block")) {
			fullLocation.put("block", getLocationProperty());
		}
		if (!fullLocation.has("Village")) {
			fullLocation.put("village", getLocationProperty());
		}
		return fullLocation;
	}

}
