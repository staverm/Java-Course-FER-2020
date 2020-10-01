package hr.fer.zemris.java.p12.model;

import java.util.List;

/**
 * Class that models a poll.
 * 
 * @author staver
 *
 */
public class Poll {
	
	/**
	 * Class that models a poll option.
	 * 
	 * @author staver
	 *
	 */
	public static class Option {
		private String optionTitle;
		private String optionLink;
		private Long votesCount;
		private Long id;
		
		/**
		 * Poll option constructor.
		 * 
		 * @param optionTitle option title
		 * @param optionLink option link
		 * @param votesCount number of votes for this option
		 */
		public Option(String optionTitle, String optionLink, long votesCount) {
			this.optionTitle = optionTitle;
			this.optionLink = optionLink;
			this.votesCount = votesCount;
		}
		
		/**
		 * Returns option title.
		 * 
		 * @return option title
		 */
		public String getOptionTitle() {
			return optionTitle;
		}
		
		/**
		 * Returns option link.
		 * 
		 * @return option link
		 */
		public String getOptionLink() {
			return optionLink;
		}
		
		/**
		 * Returns number of votes for this option.
		 * 
		 * @return number of votes for this option
		 */
		public Long getVotesCount() {
			return votesCount;
		}
		
		/**
		 * Sets id for this poll option.
		 * 
		 * @param id id to be set
		 */
		public void setId(long id) {
			this.id = id;
		}
		
		/**
		 * Returns this poll option's id.
		 * 
		 * @return this poll option's id
		 */
		public Long getId() {
			return id;
		}

	}

	private String title; // poll title
	private String message; // poll message
	List<Option> options; // list of poll options
	private long id; // poll id

	/**
	 * Poll constructor. 
	 * 
	 * @param title poll title
	 * @param message poll message
	 * @param options list of poll options
	 */
	public Poll(String title, String message, List<Option> options) {
		this.title = title;
		this.message = message;
		this.options = options;
	}

	/**
	 * Returns poll title.
	 * 
	 * @return poll title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns poll message.
	 * 
	 * @return poll message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Returns list of poll options.
	 * 
	 * @return list of poll options
	 */
	public List<Option> getOptions() {
		return options;
	}
	
	/**
	 * Sets id for this poll.
	 * 
	 * @param id id to be set
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * Returns poll id.
	 * 
	 * @return poll id
	 */
	public long getId() {
		return id;
	}

}
