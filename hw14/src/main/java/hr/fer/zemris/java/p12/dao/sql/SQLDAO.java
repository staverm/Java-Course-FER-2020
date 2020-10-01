package hr.fer.zemris.java.p12.dao.sql;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.model.Poll;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * This is an implementation of DAO that uses SQL. It expects that a connection
 * can be acquired by {@link SQLConnectionProvider}, which means that someone
 * should set it before executing a method from this class. This web-app
 * implemented this by defining a filter that intercepts requests to servlets
 * and sets a connection taken from connection-pool (and after request is
 * served, removes connection).
 * 
 * @author staver
 */
public class SQLDAO implements DAO {
	private List<Poll> polls; // list of initial polls
	private Connection con; // connection for creating(and inserting into) initial tables

	@Override
	public void incrementVotesCount(long optionID) {
		con = SQLConnectionProvider.getConnection();
		try (PreparedStatement pst = con
				.prepareStatement("UPDATE PollOptions SET votesCount = votesCount + 1 WHERE id = " + optionID)) {
			pst.executeUpdate();
		} catch (Exception ex) {
			throw new DAOException("Error while fetching poll option by id.", ex);
		}
	}

	@Override
	public Poll getPollById(long pollID) {
		con = SQLConnectionProvider.getConnection();
		Poll p = null;
		try (PreparedStatement pst = con.prepareStatement("SELECT title, message FROM Polls WHERE id = " + pollID)) {
			try (ResultSet rs = pst.executeQuery()) {
				if (rs != null && rs.next()) {
					// fetch poll attributes - it's important to do this first because of getClob()
					String pollTitle = rs.getString(1);
					Clob clob = rs.getClob(2); // assume clob is small enough
					String pollMessage = clob.getSubString(1, (int) clob.length());

					// create options
					List<Poll.Option> options = new ArrayList<>();

					try (PreparedStatement optionsStatement = con
							.prepareStatement("SELECT * FROM PollOptions WHERE pollID = " + pollID)) {
						try (ResultSet optionsRs = optionsStatement.executeQuery()) {
							while (optionsRs != null && optionsRs.next()) {
								Poll.Option option = new Poll.Option(optionsRs.getString(2), optionsRs.getString(3),
										optionsRs.getLong(5));
								option.setId(optionsRs.getLong(1));
								options.add(option);
							}
						}
					}

					// create poll to return
					p = new Poll(pollTitle, pollMessage, options);
					p.setId(pollID);
				}
			}

		} catch (Exception ex) {
			throw new DAOException("Error while fetching poll by id.", ex);
		}
		return p;
	}

	@Override
	public List<Poll> getDefinedPolls() {
		con = SQLConnectionProvider.getConnection();
		List<Poll> polls = new ArrayList<>();

		try (PreparedStatement pst = con.prepareStatement("SELECT * FROM Polls")) {
			try (ResultSet rs = pst.executeQuery()) {
				while (rs != null && rs.next()) {
					Poll p = new Poll(rs.getString(2), rs.getString(3), null); // options not important
					p.setId(rs.getLong(1));
					polls.add(p);
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error while accessing polls.", ex);
		}

		return polls;
	}

	@Override
	public void initializeTablesIfNotInitialized() {
		con = SQLConnectionProvider.getConnection();
		polls = initialPollsList(); // create initial polls list

		try {
			String createPollTable = "CREATE TABLE Polls (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
					+ "title VARCHAR(150) NOT NULL, message CLOB(2048) NOT NULL)";

			String createPollOptionsTable = "CREATE TABLE PollOptions (id BIGINT PRIMARY KEY GENERATED ALWAYS AS"
					+ " IDENTITY, optionTitle VARCHAR(100) NOT NULL, optionLink VARCHAR(150) NOT NULL, pollID"
					+ " BIGINT, votesCount BIGINT, FOREIGN KEY (pollID) REFERENCES Polls(id))";

			createTableIfNotExists(createPollTable);
			createTableIfNotExists(createPollOptionsTable);

			for (Poll poll : polls) {
				try (PreparedStatement pst = con
						.prepareStatement("SELECT COUNT(*) FROM Polls WHERE title = '" + poll.getTitle() + "'")) {

					try (ResultSet rs = pst.executeQuery()) {
						if (rs.next() && rs.getLong(1) == 0) {
							createPoll(poll);
						}
					}

				}
			}

		} catch (Exception ex) {
			throw new DAOException("Exception while creating initial tables.", ex);
		}
	}

	/**
	 * Adds data from the given poll to the database(inserts appropriate rows into
	 * Polls and PollOptions).
	 * 
	 * @param poll Poll to be inserted into database
	 * @throws SQLException if a database access error occurs
	 */
	private void createPoll(Poll poll) throws SQLException {

		try (PreparedStatement pst = con.prepareStatement("INSERT INTO Polls (title, message) values (?,?)",
				Statement.RETURN_GENERATED_KEYS)) {
			pst.setString(1, poll.getTitle());
			pst.setString(2, poll.getMessage());

			pst.executeUpdate(); // insert row into Polls

			try (ResultSet rset = pst.getGeneratedKeys()) {
				if (rset != null && rset.next()) {
					// insert rows into PollOptions that reference previously inserted Poll
					long pollID = rset.getLong(1);

					List<Poll.Option> options = poll.getOptions();

					try (PreparedStatement insertStatement = con
							.prepareStatement("INSERT INTO PollOptions (optionTitle, "
									+ "optionLink, pollID, votesCount) values (?,?," + pollID + ",?)")) {
						for (Poll.Option option : options) {
							insertStatement.setString(1, option.getOptionTitle());
							insertStatement.setString(2, option.getOptionLink());
							insertStatement.setLong(3, option.getVotesCount());

							insertStatement.addBatch();
							insertStatement.clearParameters();
						}
						insertStatement.executeBatch();
					}
				}
			}
		}
	}

	/**
	 * Creates table with given create table SQL statement if table doesn't already
	 * exist.
	 * 
	 * @param SQLcreateTableStatement SQL statement that creates wanted table
	 * @throws SQLException if a database access error occurs
	 */
	private void createTableIfNotExists(String SQLcreateTableStatement) throws SQLException {
		PreparedStatement pst = con.prepareStatement(SQLcreateTableStatement);
		try {
			pst.execute(); // create table
		} catch (SQLException e) {
			// if tables already exist, error code will be X0Y32
			if (!e.getSQLState().equals("X0Y32")) {
				throw e;
			}
		} finally {
			pst.close();
		}
	}

	/**
	 * Returns a list of Poll objects used to populate database tables at
	 * web-application startup.
	 * 
	 * @return list of Poll objects
	 */
	private List<Poll> initialPollsList() {
		List<Poll> polls = new ArrayList<>();

		List<Poll.Option> options = Arrays.asList(
				new Poll.Option("The Beatles", "https://www.youtube.com/watch?v=QDYfEBY9NM4", 0),
				new Poll.Option("The Platters", "https://www.youtube.com/watch?v=H2di83WAOhU", 0),
				new Poll.Option("The Beach Boys", "https://www.youtube.com/watch?v=2s4slliAtQU", 0),
				new Poll.Option("The Four Seasons", "https://www.youtube.com/watch?v=y8yvnqHmFds", 0),
				new Poll.Option("The Marcels", "https://www.youtube.com/watch?v=qoi3TH59ZEs", 0),
				new Poll.Option("The Everly Brothers", "https://www.youtube.com/watch?v=tbU3zdAgiX8", 0),
				new Poll.Option("The Mamas And The Papas", "https://www.youtube.com/watch?v=N-aK6JnyFmk", 0));
		polls.add(new Poll("Glasanje za omiljeni bend:",
				"Od sljedećih bendova, koji Vam je bend najdraži? " + "Kliknite na link kako biste glasali!", options));

		options = Arrays.asList(new Poll.Option("Franjo Tuđman", "https://hr.wikipedia.org/wiki/Franjo_Tu%C4%91man", 0),
				new Poll.Option("Stjepan Mesić", "https://hr.wikipedia.org/wiki/Stjepan_Mesi%C4%87", 0),
				new Poll.Option("Ivo Josipović", "https://hr.wikipedia.org/wiki/Ivo_Josipovi%C4%87", 0),
				new Poll.Option("Kolinda Grabar-Kitarović",
						"https://hr.wikipedia.org/wiki/Kolinda_Grabar-Kitarovi%C4%87", 0),
				new Poll.Option("Zoran Milanović", "https://hr.wikipedia.org/wiki/Zoran_Milanovi%C4%87", 0));
		polls.add(new Poll("Glasanje za omiljenog predsjednika:",
				"Od sljedećih predsjednika, koji Vam je najdraži? " + "Kliknite na link kako biste glasali!", options));

		return polls;
	}

}
