package com.nixelsolutions;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.net.*;
import java.util.Enumeration;
import java.util.Vector;

public final class Hello extends HttpServlet
{

    public Hello()
    {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {

        InetAddress serverIp=null;
	Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
        String hostname="", clientIp="", userAgent="";
        try {
            serverIp = InetAddress.getLocalHost();
            hostname = serverIp.getHostName();
	    clientIp = request.getHeader("X-FORWARDED-FOR");
	    if (clientIp == null) {
		clientIp = request.getRemoteAddr();
	    }
	    userAgent = request.getHeader("user-agent");
 
        } catch (UnknownHostException e) {
 
            e.printStackTrace();
        }

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>Sample Application Page</title>");
        writer.println("</head>");
        writer.println("<body bgcolor=white>");
        writer.println("<table border=\"0\">");
        writer.println("<tr>");
        writer.println("<td>");
        writer.println("<h1>Sample Application</h1>");
        writer.println("</td>");
        writer.println("</tr>");
        writer.println("<tr>");
        writer.println("<td>");
//        writer.println("Server IP address: <strong>" + serverIp.getHostAddress() + "</strong>");
        writer.println("Server IP addresses: <strong>");
	while(interfaces.hasMoreElements())
	{
		NetworkInterface n = (NetworkInterface) interfaces.nextElement();
		Enumeration inetAddresses = n.getInetAddresses();
		while(inetAddresses.hasMoreElements())
		{
			InetAddress address = (InetAddress) inetAddresses.nextElement();
			if (address.getHostAddress().substring(0,6).compareTo("10.42.") == 0)
				writer.println(address.getHostAddress() + " ");
		}
	}
	writer.println("</strong>");
        writer.println("</td>");
        writer.println("</tr>");
        writer.println("<tr>");
        writer.println("<td>");
        writer.println("Server Hostname: <strong>" + hostname + "</strong>");
        writer.println("</td>");
        writer.println("</tr>");
        writer.println("<tr>");
        writer.println("<td>");
        writer.println("Your current IP address: <strong>" + clientIp + "</strong>");
        writer.println("</td>");
        writer.println("</tr>");
        writer.println("<tr>");
        writer.println("<td>");
        writer.println("Your current User-Agent: <strong>" + userAgent + "</strong>");
        writer.println("</td>");
        writer.println("</tr>");
        writer.println("</table>");
        writer.println("</body>");
        writer.println("</html>");
    }
}
