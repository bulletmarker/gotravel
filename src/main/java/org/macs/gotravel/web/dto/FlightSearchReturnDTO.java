package org.macs.gotravel.web.dto;

import com.itsum.sabre.client.dto.ota.airavail.OTAAirAvailRS;

/**
* @ClassName: FlightSearchReturnDTO
* @Description: 可选航班查询结果
* @author Jason.ma
* @date 2013年10月24日 下午4:56:13
*
 */
public class FlightSearchReturnDTO implements IGoTravelDTO{
	
	private static final long serialVersionUID = 1L;
	
	private OTAAirAvailRS airAvail= null;

	public OTAAirAvailRS getAirAvail() {
		return airAvail;
	}

	public void setAirAvail(OTAAirAvailRS airAvail) {
		this.airAvail = airAvail;
	}

}
