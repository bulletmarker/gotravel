package org.macs.gotravel.web.action;

import javax.servlet.http.HttpServletRequest;

import org.macs.gotravel.web.dto.ReturnData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itsum.sabre.client.connection.SabreConnection;
import com.itsum.sabre.client.connection.SabreConnectionFactory;
import com.itsum.sabre.client.dto.ota.airbook.OTAAirBookRQ;
import com.itsum.sabre.client.dto.travelitineraryaddinfo.TravelItineraryAddInfoRQ;
import com.itsum.sabre.client.exception.SabreClientException;
import com.itsum.sabre.service.AddPNRService;


/**
 * 
* @ClassName: TravelItineraryAddInfoAction
* @Description: 添加行程信息Action
* @author Jason.ma
* @date 2013年10月30日 下午3:54:54
*
 */
public class TravelItineraryAddInfoAction {
	
	private static Logger logger = LoggerFactory.getLogger(TravelItineraryAddInfoAction.class);
	
	public static void addInfo(ReturnData returnData,HttpServletRequest request){
		
		SabreConnection conn = null;
		try {
			conn = SabreConnectionFactory.openConnection();
			TravelItineraryAddInfoRQ travelItineraryAddInfoRQ = getTravelItineraryAddInfoRQ(request);
			OTAAirBookRQ oTAAirBookRQ = getOTAAirBookRQ(request);
			String pnr = AddPNRService.addInfo(conn, travelItineraryAddInfoRQ, oTAAirBookRQ);
			returnData.setStringData(pnr);
			returnData.setSuccess(true);
			logger.debug("TravelItineraryAddInfoService 调用成功");
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
	
	private static TravelItineraryAddInfoRQ getTravelItineraryAddInfoRQ(HttpServletRequest request){
		return null;
	}
	
	private static  OTAAirBookRQ getOTAAirBookRQ(HttpServletRequest request){
		return null;
	}

}
