package org.macs.gotravel.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.PropertyConfigurator;
import org.macs.gotravel.web.exception.ConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itsum.sabre.client.cfg.Configuration;

/**
 * 起初始化作用的Servlet
 * @author Jason.ma
 */
public class InitServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private Logger logger = null;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	@Override
	public void init() throws ServletException {
		//初始化log4j配置
		System.out.println("log4j config start");
		File log4jconfig = new File("gotravel/conf/log4j.properties");
		System.out.println("log4j.properties path:" + log4jconfig.getAbsolutePath());
		PropertyConfigurator.configure(log4jconfig.getAbsolutePath());
		logger = LoggerFactory.getLogger(InitServlet.class);
		logger.info("log4j config ok !");
		
		//初始化系统配置
		logger.info("gotravel.conf load start");
		File sysconfig = new File("gotravel/conf/gotravel.conf");
		Properties prop = new Properties();
		try {
			InputStream is = new FileInputStream(sysconfig);
			prop.load(is);
			Configuration.config(prop);
		} catch (FileNotFoundException e) {
			throw new ConfigException("gotravel.conf not found.",e);
		} catch (IOException e) {
			throw new ConfigException("gotravel.conf read fail.",e);
		}
		logger.info("gotravel.conf load ok");
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
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
