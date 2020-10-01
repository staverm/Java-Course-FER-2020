package hr.fer.zemris.java.servlets;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that represents a band. Each band has an id, name, a link to a popular
 * song and number of votes.
 * 
 * @author staver
 *
 */
public class Band {

	private Integer id; // band id
	private String name; // band name
	private String song; // link to popular song
	private Integer votes; // number of votes for this band

	/**
	 * Band constructor. Constructs a new band and initializes it with the given
	 * argument.
	 * 
	 * @param id   band id
	 * @param name band name
	 * @param song link to popular song
	 */
	public Band(Integer id, String name, String song, Integer votes) {
		this.id = id;
		this.name = name;
		this.song = song;
		this.votes = votes;
	}

	/**
	 * Returns band id;
	 * 
	 * @return band id;
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Returns band name;
	 * 
	 * @return band name;
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns link to popular song.
	 * 
	 * @return link to popular song.
	 */
	public String getSong() {
		return song;
	}

	/**
	 * Returns number of votes for this band.
	 * 
	 * @return number of votes for this band.
	 */
	public Integer getVotes() {
		return votes;
	}

	/**
	 * Sets number of votes for this band to the given value.
	 * 
	 * @param votes number of votes to be set.
	 */
	public void setVotes(Integer votes) {
		this.votes = votes;
	}

	/**
	 * Constructs and returns list of bands (sorted in ascending order by id) parsed
	 * from given file. It is expected that each line from file represents one band.
	 * Each band will have 0 votes.
	 * 
	 * @param fileName file to be parsed into list of bands. It is expected that
	 *                 each line from file represents one band.
	 * @return list of bands
	 * @throws Exception if unable to read or parse file
	 */
	public static List<Band> parseBands(String bandsFileName) throws Exception {
		List<String> lines = Files.readAllLines(Paths.get(bandsFileName));
		List<Band> bands = new ArrayList<>();

		for (String line : lines) {
			String[] bandAttrs = line.split("\\t");
			bands.add(new Band(Integer.parseInt(bandAttrs[0]), bandAttrs[1], bandAttrs[2], 0));
		}

		Collections.sort(bands, (o1, o2) -> o1.getId().compareTo(o2.getId()));
		return bands;
	}

	/**
	 * Returns list of bands where each band has appropriate number of votes which
	 * are read from file given as 2nd argument. 1st argument is file which lists
	 * bands (without votes). The list is sorted in descending order by number of
	 * votes.
	 * 
	 * @param bandsFileName   file to be parsed into list of bands. It is expected
	 *                        that each line from file represents one band.
	 * @param resultsFileName file that maps band ids to number of votes
	 * @return list of bands where each band has appropriate number of votes
	 * @throws Exception if unable to read or parse files
	 */
	public static List<Band> getResults(String bandsFileName, String resultsFileName) throws Exception {
		List<String> lines = Files.readAllLines(Paths.get(bandsFileName));
		List<Band> bands = new ArrayList<>();
		
		// map id to band
		Map<Integer, Band> bandMap = new HashMap<>(); 
		
		for (String line : lines) {
			String[] bandAttrs = line.split("\\t");
			Band b = new Band(Integer.parseInt(bandAttrs[0]), bandAttrs[1], bandAttrs[2], 0);
			bands.add(b);
			bandMap.put(b.getId(), b);
			//notice that map values are references to the same Band objects that our list has!
		}

		{
			File rez = new File(resultsFileName);
			rez.createNewFile(); // create empty file if it doesn't exist
		}

		List<String> fileContent = new ArrayList<>(
				Files.readAllLines(Paths.get(resultsFileName), StandardCharsets.UTF_8));

		for (int i = 0; i < fileContent.size(); i++) { // set appropriate votes value for each band
			String[] bandRating = fileContent.get(i).split("\\t");
			bandMap.get(Integer.parseInt(bandRating[0])).setVotes(Integer.parseInt(bandRating[1]));
		}

		Collections.sort(bands, (o1, o2) -> o2.getVotes().compareTo(o1.getVotes()));
		return bands;
	}

}
