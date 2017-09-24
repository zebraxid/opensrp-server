package org.opensrp.connector.dhis2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DHIS2VersionFactory {
	
	@Autowired
	private DHIS2VersionTwoPointThree dhis2VersionTwoPointThree;
	
	@Autowired
	private DHIS2VersionTwoPointTwo dhis2VersionTwoPointTwo;
	
	private DHIS2Version dhis2Version;
	
	public DHIS2Version getVersion(DHIS2VersionType version) {
		if (version == DHIS2VersionType.VERSION2POINT2) {
			System.err.println("2");
			dhis2Version = dhis2VersionTwoPointTwo;
		} else if (version == DHIS2VersionType.VERSION2POINT3) {
			System.err.println("3");
			dhis2Version = dhis2VersionTwoPointThree;
		} else {
			
		}
		return dhis2Version;
		
	}
	
}
