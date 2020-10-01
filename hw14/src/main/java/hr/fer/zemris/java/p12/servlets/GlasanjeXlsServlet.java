package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.Poll;

/**
 * Servlet that generates and returns as response an excel file (.xlsx) with
 * poll voting results. <br>
 * <br>
 * If the request parameter 'pollID' is set (ie. not null) the voting results
 * for the poll with that id are fetched from the persistence subsystem. Otherwise the voting results are read from a session 
 * parameter 'options' (expected to be a list of options). <br>
 * This way the user has the option to provide the options list explicitly(by
 * session parameter 'options') or just provide a request parameter 'pollID' and
 * let the servlet to fetch the options list from persistence subsystem.
 * 
 * @author staver
 *
 */
@WebServlet("/servleti/glasanje-xls")
public class GlasanjeXlsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Fetches from persistence subsystem a list of options for a poll with id read
	 * from request parameter 'pollID'. If that parameter is null retrieves this
	 * list from session parameter 'options'. This list is then used to create an
	 * excel file which is sent as response.
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try (HSSFWorkbook hwb = new HSSFWorkbook()) {
			List<Poll.Option> options = null;

			if (req.getParameter("pollID") != null) {
				Poll poll = DAOProvider.getDao().getPollById(Long.parseLong(req.getParameter("pollID")));
				options = poll.getOptions();
			} else if (req.getSession().getAttribute("options") != null
					&& req.getSession().getAttribute("options") instanceof List) {
				options = (List<Poll.Option>) req.getSession().getAttribute("options"); // assume list of options
			} else {
				return;
			}

			HSSFSheet sheet = hwb.createSheet("Sheet 1");
			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell((short) 0).setCellValue("Opcija");
			rowhead.createCell((short) 1).setCellValue("Broj glasova");

			for (Integer i = 0; i < options.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				row.createCell((short) 0).setCellValue(options.get(i).getOptionTitle());
				row.createCell((short) 1).setCellValue(options.get(i).getVotesCount());
			}

			resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			resp.setHeader("Content-Disposition", "attachment; filename=\"rezultati.xlsx\"");
			hwb.write(resp.getOutputStream());

		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

}
