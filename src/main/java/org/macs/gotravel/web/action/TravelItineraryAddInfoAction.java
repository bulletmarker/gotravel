package org.macs.gotravel.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.macs.gotravel.web.dto.ReturnData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itsum.sabre.client.connection.SabreConnection;
import com.itsum.sabre.client.connection.SabreConnectionFactory;
import com.itsum.sabre.client.dto.ota.airbook.OTAAirBookRQ;
import com.itsum.sabre.client.dto.travelitineraryaddinfo.TravelItineraryAddInfoLLSInput;
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
	
	public static void bookItinerary(ReturnData returnData,HttpServletRequest request){
		
		SabreConnection conn = null;
		try {
			TravelItineraryAddInfoRQ travelItineraryAddInfoRQ = getTravelItineraryAddInfoRQ(request);
			OTAAirBookRQ oTAAirBookRQ = getOTAAirBookRQ(request);
			conn = SabreConnectionFactory.openConnection();
			String pnr = AddPNRService.bookItinerary(conn, travelItineraryAddInfoRQ, oTAAirBookRQ);
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
		TravelItineraryAddInfoRQ travelItineraryAddInfoRQ = new TravelItineraryAddInfoRQ();
		TravelItineraryAddInfoRQ.AgencyInfo agencyInfo = new TravelItineraryAddInfoRQ.AgencyInfo();
		TravelItineraryAddInfoRQ.AgencyInfo.Address address = new TravelItineraryAddInfoRQ.AgencyInfo.Address();
		address.setAddressLine(request.getParameter("agencyInfo.address.addressLine"));
		address.setCityName(request.getParameter("agencyInfo.address.cityName"));
		address.setCountryCode(request.getParameter("agencyInfo.address.countryCode"));
		address.setPostalCode(request.getParameter("agencyInfo.address.postalCode"));
		TravelItineraryAddInfoRQ.AgencyInfo.Address.StateCountyProv stateCountyProv = new TravelItineraryAddInfoRQ.AgencyInfo.Address.StateCountyProv();
		stateCountyProv.setStateCode(request.getParameter("agencyInfo.address.stateCountyProv"));
		address.setStateCountyProv(stateCountyProv);
		address.setStreetNmbr(request.getParameter("agencyInfo.address.streetNmbr"));
		TravelItineraryAddInfoRQ.AgencyInfo.Address.VendorPrefs vendorPrefs = new TravelItineraryAddInfoRQ.AgencyInfo.Address.VendorPrefs();
		TravelItineraryAddInfoRQ.AgencyInfo.Address.VendorPrefs.Airline airline = new TravelItineraryAddInfoRQ.AgencyInfo.Address.VendorPrefs.Airline();
		boolean hosted = "true".equals(request.getParameter("agencyInfo.address.vendorPrefs.airline.hosted"))?true:false;
		airline.setHosted(hosted);
		vendorPrefs.setAirline(airline);
		address.setVendorPrefs(vendorPrefs);
		agencyInfo.setAddress(address);
		
		TravelItineraryAddInfoRQ.AgencyInfo.Ticketing ticketing = new TravelItineraryAddInfoRQ.AgencyInfo.Ticketing();
		//ticketing.setPseudoCityCode("IH9A");
		ticketing.setTicketType(request.getParameter("agencyInfo.ticketing.ticketType"));
		ticketing.setPseudoCityCode(request.getParameter("agencyInfo.ticketing.pseudoCityCode"));
		ticketing.setQueueNumber(request.getParameter("agencyInfo.ticketing.queueNumber"));
		ticketing.setShortText(request.getParameter("agencyInfo.ticketing.shortText"));
		
		agencyInfo.setTicketing(ticketing);
		travelItineraryAddInfoRQ.setAgencyInfo(agencyInfo);
		
		TravelItineraryAddInfoRQ.CustomerInfo customerInfo = new TravelItineraryAddInfoRQ.CustomerInfo();
		int emailcount = Integer.valueOf(request.getParameter("emailcount"));
		List<TravelItineraryAddInfoRQ.CustomerInfo.Email> emails = customerInfo.getEmail();
		for(int i = 0 ; i < emailcount ; i++){
			TravelItineraryAddInfoRQ.CustomerInfo.Email email = new TravelItineraryAddInfoRQ.CustomerInfo.Email();
			email.setAddress(getIndexParameter(request,"email.address",i));
			email.setNameNumber(getIndexParameter(request,"email.nameNumber",i));
			email.setType(getIndexParameter(request,"email.type",i));
			emails.add(email);
		}
		
		List<TravelItineraryAddInfoRQ.CustomerInfo.PersonName> personNames = customerInfo.getPersonName();
		int personcount = Integer.valueOf(request.getParameter("personcount"));
		for(int i = 0 ; i < personcount ; i++){
			TravelItineraryAddInfoRQ.CustomerInfo.PersonName personName = new TravelItineraryAddInfoRQ.CustomerInfo.PersonName();
			personName.setNameNumber(getIndexParameter(request,"personName.nameNumber",i));
			personName.setNameReference("TEST");
			personName.setGivenName(getIndexParameter(request,"personName.givenName",i));
			personName.setSurname(getIndexParameter(request,"personName.surname",i));
			personName.setInfant(("true".equals(getIndexParameter(request,"personName.infant",i))?true:false));
			personNames.add(personName);
		}
		
		TravelItineraryAddInfoRQ.CustomerInfo.ContactNumbers contactNumbers = new TravelItineraryAddInfoRQ.CustomerInfo.ContactNumbers();
		List<TravelItineraryAddInfoRQ.CustomerInfo.ContactNumbers.ContactNumber> contactNumbersL= contactNumbers.getContactNumber();
		int contactNumberCount = Integer.valueOf(request.getParameter("contactNumberCount"));
		for(int i = 0 ; i < contactNumberCount ; i++){
			TravelItineraryAddInfoRQ.CustomerInfo.ContactNumbers.ContactNumber contactNumber = new TravelItineraryAddInfoRQ.CustomerInfo.ContactNumbers.ContactNumber();
			contactNumber.setPhone(getIndexParameter(request,"contactNumber.phone",i));
			contactNumber.setPhoneUseType(getIndexParameter(request,"contactNumber.phoneUseType",i));
			contactNumber.setNameNumber(getIndexParameter(request,"contactNumber.nameNumber",i));
			contactNumbersL.add(contactNumber);
		}
		customerInfo.setContactNumbers(contactNumbers);
		travelItineraryAddInfoRQ.setCustomerInfo(customerInfo);
		return travelItineraryAddInfoRQ;
	}
	
	private static OTAAirBookRQ getOTAAirBookRQ(HttpServletRequest request){
		OTAAirBookRQ oTAAirBookRQ = new OTAAirBookRQ();
		int flightsegmentcount = Integer.valueOf(request.getParameter("flightsegmentcount"));
		oTAAirBookRQ.setOriginDestinationInformation(new OTAAirBookRQ.OriginDestinationInformation());
		List<OTAAirBookRQ.OriginDestinationInformation.FlightSegment> flightSegments = oTAAirBookRQ.getOriginDestinationInformation().getFlightSegment();
		for(int i = 0 ; i < flightsegmentcount ; i++){
			OTAAirBookRQ.OriginDestinationInformation.FlightSegment flightSegment = new OTAAirBookRQ.OriginDestinationInformation.FlightSegment();
			flightSegment.setDepartureDateTime(getIndexParameter(request,"airBook.flightSegment.departureDateTime",i));
			flightSegment.setArrivalDateTime(getIndexParameter(request,"airBook.flightSegment.arrivalDateTime",i));
			flightSegment.setFlightNumber(getIndexParameter(request,"airBook.flightSegment.flightNumber",i));
			flightSegment.setNumberInParty(getIndexParameter(request,"airBook.flightSegment.numberInParty",i));
			flightSegment.setStatus("NN");
			flightSegment.setResBookDesigCode(getIndexParameter(request,"airBook.flightSegment.resBookDesigCode",i));
			OTAAirBookRQ.OriginDestinationInformation.FlightSegment.DestinationLocation f1destinationLocation = new OTAAirBookRQ.OriginDestinationInformation.FlightSegment.DestinationLocation();
			f1destinationLocation.setLocationCode(getIndexParameter(request,"airBook.flightSegment.destinationLocation",i));
			flightSegment.setDestinationLocation(f1destinationLocation);
			OTAAirBookRQ.OriginDestinationInformation.FlightSegment.OriginLocation f1OriginLocation = new OTAAirBookRQ.OriginDestinationInformation.FlightSegment.OriginLocation();
			f1OriginLocation.setLocationCode(getIndexParameter(request,"airBook.flightSegment.originLocation",i));
			flightSegment.setOriginLocation(f1OriginLocation);
			OTAAirBookRQ.OriginDestinationInformation.FlightSegment.MarketingAirline marketingAirline = new OTAAirBookRQ.OriginDestinationInformation.FlightSegment.MarketingAirline();
			marketingAirline.setFlightNumber(getIndexParameter(request,"airBook.flightSegment.flightNumber",i));
			marketingAirline.setCode(getIndexParameter(request,"airBook.flightSegment.airline",i));
			flightSegment.setMarketingAirline(marketingAirline);
			flightSegments.add(flightSegment);
		}
		return oTAAirBookRQ;
	}
	
	private static String getIndexParameter(HttpServletRequest request,String pname,int i){
		return request.getParameter(pname + "[" + i + "]");
	}

}
