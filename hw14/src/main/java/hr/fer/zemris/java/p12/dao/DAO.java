package hr.fer.zemris.java.p12.dao;

import java.util.List;
import hr.fer.zemris.java.p12.model.Poll;

/**
 * Interface used to communicate with the persistence subsystem.
 * 
 * @author staver
 *
 */
public interface DAO {

	/**
	 * Increments votes count for poll option specified with given option id.
	 * 
	 * @param optionID id of the poll option whose votes count is to be incremented.
	 */
	public void incrementVotesCount(long optionID);

	/**
	 * Returns Poll object specified by given id.
	 * 
	 * @param pollID id of the poll to be returned
	 * @return Poll object specified by given id
	 */
	public Poll getPollById(long pollID);

	/**
	 * Returns list of defined polls(with options attribute set to null - this is
	 * done for efficiency and simplicity).
	 * 
	 * @return list of defined polls(with options attribute set to null)
	 */
	public List<Poll> getDefinedPolls();

	/**
	 * Creates initial tables (Polls and PollOptions) if they don't already exist.
	 * Inserts appropriate initial rows into them (two polls with appropriate poll
	 * options with votesCount set to 0).
	 * 
	 * After this method is called there will be two polls defined: poll for favorite band
	 * and poll favorite president.
	 */
	public void initializeTablesIfNotInitialized();

}