package org.macs.gotravel.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig;
import org.macs.gotravel.web.action.FlightSearchAction;
import org.macs.gotravel.web.dto.ReturnData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itsum.sabre.client.exception.SabreClientException;

/**
 * 起初始化作用的Servlet
 * @author Jason.ma
 */
public class JsonActionServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LoggerFactory.getLogger(JsonActionServlet.class);
	
	private static ObjectMapper mapper = new ObjectMapper();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JsonActionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	
	@Override
	public void init() throws ServletException {
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		super.init();
	}
	
	
	@Override
	public void destroy() {
		super.destroy();
	}
	


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//String actionName = request.getParameter("actionName");
		//String methodName = request.getParameter("actionMethod");
		ReturnData rd = new ReturnData();
		try {
			FlightSearchAction.queryFlights(rd,request);
		} catch (SabreClientException e) {
			rd.setSuccess(false);
			rd.setError(e.getErrorCode());
			rd.setErrorCode(e.getErrorMessage() + ",Severity:" + e.getSeverity() + ",StackTrace:" + e.getStackTrace());
		}
		String json = mapper.writeValueAsString(rd);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().append(json);
		response.getWriter().close();
		logger.debug(json);
	}

}
