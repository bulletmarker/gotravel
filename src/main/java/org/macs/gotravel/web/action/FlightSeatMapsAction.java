package org.macs.gotravel.web.action;

import javax.servlet.http.HttpServletRequest;

import org.macs.gotravel.web.dto.FlightSeatMapsReturnDTO;
import org.macs.gotravel.web.dto.ReturnData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itsum.sabre.client.connection.SabreConnection;
import com.itsum.sabre.client.connection.SabreConnectionFactory;
import com.itsum.sabre.client.dto.imap.airseatmap.IMAPAirSeatMapRS;
import com.itsum.sabre.client.exception.SabreClientException;
import com.itsum.sabre.service.IMAP_AirSeatMapService;

/**
 * 
* @ClassName: FlightSearchAction
* @Description: 查询航班信息Action
* @author Jason.ma
* @date 2013年10月24日 下午4:32:44
*
 */
public class FlightSeatMapsAction {
	
	private static Logger logger = LoggerFactory.getLogger(FlightSeatMapsAction.class);
	
	public static void querySeatMaps(ReturnData returnData,HttpServletRequest request){
		String fromCity = request.getParameter("query.fromCity");
		String toCity = request.getParameter("query.toCity");
		String departDate = request.getParameter("query.departDate");
		String airline = request.getParameter("query.airline");
		String flightnumber = request.getParameter("query.flightnumber");
		String flightclass = request.getParameter("query.flightclass");
		
		
		SabreConnection conn = null;
		try {
			conn = SabreConnectionFactory.openConnection();
			IMAPAirSeatMapRS rs = IMAP_AirSeatMapService.querySeatMaps(conn, airline, flightnumber, departDate, fromCity, toCity, flightclass);
			FlightSeatMapsReturnDTO flightSeatMapsReturnDTO = new FlightSeatMapsReturnDTO();
			flightSeatMapsReturnDTO.setSeatMaps(rs);
			returnData.setData(flightSeatMapsReturnDTO);
			returnData.setSuccess(true);
			logger.debug("IMAP_AirSeatMapService 调用成功");
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SabreClientException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
