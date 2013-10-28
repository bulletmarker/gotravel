package org.macs.gotravel.web.dto;

import com.itsum.sabre.client.dto.imap.airseatmap.IMAPAirSeatMapRS;

/**
* @ClassName: FlightSeatMapsReturnDTO
* @Description: 可选航班查询结果
* @author Jason.ma
* @date 2013年10月24日 下午4:56:13
*
 */
public class FlightSeatMapsReturnDTO implements IGoTravelDTO{
	
	private static final long serialVersionUID = 1L;
	
	private IMAPAirSeatMapRS seatMaps = null;

	public IMAPAirSeatMapRS getSeatMaps() {
		return seatMaps;
	}

	public void setSeatMaps(IMAPAirSeatMapRS seatMaps) {
		this.seatMaps = seatMaps;
	}


}
