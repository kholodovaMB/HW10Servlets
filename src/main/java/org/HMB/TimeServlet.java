package org.HMB;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {

    private String zoneIdUTC = "UTC";
    private String dateTime;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        String timeZone = parseTimezone(req);

        dateTime = ZonedDateTime.now(ZoneId.of(timeZone)).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm:ss"))
                + " " + timeZone;

        out.print("<html><body>");
        out.print("<h3>час у переданому часовому поясі:</h3>");
        out.print("<p>" + dateTime + "</p>");
        out.print("</body></html>");
        out.close();
    }

    private String parseTimezone(HttpServletRequest request) {
        if (request.getParameterMap().containsKey("timezone")) {
            return request.getParameter("timezone").replace(" ", "+");
        }
        return zoneIdUTC;
    }
    @Override
    public void destroy() {
        dateTime  = "";
    }
}
