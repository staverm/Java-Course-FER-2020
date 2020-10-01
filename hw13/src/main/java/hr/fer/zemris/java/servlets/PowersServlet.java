package hr.fer.zemris.java.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet that dynamically generates an excel file (.xlsx) depending on the
 * given parameters a,b and n. Excel file has n sheets and on each sheet i are
 * displayed values from a to b and their powers (x^i).
 * 
 * @author staver
 *
 */
public class PowersServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Dynamically generates an excel file (.xlsx) depending on the given parameters
	 * a,b and n. If given parameters are illegal values, displays an error jsp.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer a = null;
		Integer b = null;
		Integer n = null;
		try {
			a = Integer.parseInt(req.getParameter("a"));
			b = Integer.parseInt(req.getParameter("b"));
			n = Integer.parseInt(req.getParameter("n"));
		} catch (NumberFormatException ex) {
			req.setAttribute("notNumbers", "true");
			req.getRequestDispatcher("/WEB-INF/pages/powersError.jsp").forward(req, resp);
		}

		boolean error = false;
		if (a < -100 || a > 100 || a > b) {
			req.setAttribute("a", a);
			error = true;
		}

		if (b < -100 || b > 100) {
			req.setAttribute("b", b);
			error = true;
		}

		if (n < 1 || n > 5) {
			req.setAttribute("n", n);
			error = true;
		}

		if (error) { //display error jsp
			req.getRequestDispatcher("/WEB-INF/pages/powersError.jsp").forward(req, resp);
		}

		try (HSSFWorkbook hwb = new HSSFWorkbook()) { //create excel document
			for (Integer i = 1; i <= n; i++) {
				HSSFSheet sheet = hwb.createSheet("Sheet " + i);
				HSSFRow rowhead = sheet.createRow((short) 0);
				rowhead.createCell((short) 0).setCellValue("value");
				rowhead.createCell((short) 1).setCellValue("x^" + i);

				short rowNum = 1;
				for (Integer j = a; j <= b; j++) {
					HSSFRow row = sheet.createRow(rowNum);
					row.createCell((short) 0).setCellValue(j);
					row.createCell((short) 1).setCellValue(Math.pow(j.doubleValue(), i.doubleValue()));
					rowNum++;
				}
			}

			resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xlsx\"");
			hwb.write(resp.getOutputStream());
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
}
