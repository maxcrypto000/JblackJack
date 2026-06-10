package model;

/**
 * Represents the core state of the game session,
 * tracking the username, level, number of players, and a generic counter value.
 */
public class Counter {
	
	private String Username;
	private int level;
	private int nPlayers;
	private int value; 
	
	
	/**
	 * Default constructor. Initializes an empty Counter.
	 */
	public Counter()
	{

	}
	/**
	 * Constructs a Counter with an initial value.
	 *
	 * @param initValue the initial value for the counter
	 */
	public Counter(int initValue)
	{
		reset(initValue);
	}
	
	/**
	 * Gets the current counter value.
	 *
	 * @return the counter value
	 */
	public int getValue()
	{
		return value;
	}
	
	/**
	 * Increments the counter value by 1.
	 */
	public void incValue()
	{
		value++;
	}
	
	
	/**
	 * Decrements the counter value by 1.
	 */
	public void decValue()
	{
		value--;	
	}
	/**
	 * Resets the counter to a specific value.
	 *
	 * @param resetValue the value to set the counter to
	 */
	public void reset(int resetValue)
	{
		value=resetValue;
	}
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return Username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		Username = username;
	}
	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	/**
	 * @return the nPlayers
	 */
	public int getnPlayers() {
		return nPlayers;
	}
	/**
	 * @param nPlayers the nPlayers to set
	 */
	public void setnPlayers(int nPlayers) {
		this.nPlayers = nPlayers;
	}
	@Override
	public String toString()
	{
		return "Counter.value: "+value + " name: " + Username + " players: " + nPlayers + " lvl: " + level;
	}
}
