package org.macs.gotravel.web.action;

import javax.servlet.http.HttpServletRequest;

import org.macs.gotravel.web.dto.FlightSearchReturnDTO;
import org.macs.gotravel.web.dto.ReturnData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itsum.sabre.client.connection.SabreConnection;
import com.itsum.sabre.client.connection.SabreConnectionFactory;
import com.itsum.sabre.client.dto.ota.airavail.OTA_AirAvailLLSOutput;
import com.itsum.sabre.client.exception.SabreClientException;
import com.itsum.sabre.service.OTA_AirAvailService;

/**
 * 
* @ClassName: FlightSearchAction
* @Description: 查询航班信息Action
* @author Jason.ma
* @date 2013年10月24日 下午4:32:44
*
 */
public class FlightSearchAction {
	
	private static Logger logger = LoggerFactory.getLogger(FlightSearchAction.class);
	
	public static void queryFlights(ReturnData returnData,HttpServletRequest request){
		String departDate = request.getParameter("departDate");
		String returnDate = request.getParameter("returnDate");
		String fromCity = request.getParameter("fromCity");
		String toCity = request.getParameter("toCity");
		SabreConnection conn = null;
		try {
			conn = SabreConnectionFactory.openConnection();
			OTA_AirAvailLLSOutput output = OTA_AirAvailService.queryAvail(conn,departDate, returnDate,fromCity, toCity);
			FlightSearchReturnDTO flightSearchReturnDTO = new FlightSearchReturnDTO();
			flightSearchReturnDTO.setAirAvail(output.getRs());
			returnData.setData(flightSearchReturnDTO);
			logger.debug("OTA_AirAvailService 调用成功");
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
