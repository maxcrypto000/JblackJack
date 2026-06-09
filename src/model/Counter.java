package model;

public class Counter {
	
	private String Username;
	private int level;
	private int nPlayers;
	private int value; 
	
	
	public Counter()
	{

	}
	/**
	 * 
	 * @param initValue
	 */
	public Counter(int initValue)
	{
		reset(initValue);
	}
	
	public int getValue()
	{
		return value;
	}
	
	public void incValue()
	{
		value++;
	}
	
	
	public void decValue()
	{
		value--;	
	}
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
