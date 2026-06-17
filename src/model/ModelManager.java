package model;

import java.util.ArrayList;
import java.util.Observable;

/**
 * The core manager of the game's data and state.
 * Implements the Singleton pattern and extends Observable to update the views.
 * It coordinates the players, the dealer, the deck, and the game rules.
 */
public class ModelManager extends Observable {
	
	private static ModelManager instance;
	boolean ended = false;
	
	private ArrayList<Player>  players = new ArrayList<Player>();
	private Counter counter;
	private Dealer dealer;
	
	/**
	 * Private constructor for Singleton pattern.
	 * Initializes the counter.
	 */
	private ModelManager()
	{
		this.counter=new Counter();
	}
	
	/**
	 * Deals a single card to the dealer from the deck.
	 * Notifies observers of the change.
	 */
	public void dealToDealer() {
		
		dealer.addCard(RandomCardSelector.getInstance().selectCard());
		
		setChanged();
		notifyObservers(buildGameState());
	}
	/**
	 * Deals a single card to a specific player.
	 *
	 * @param playerIndex the index of the player receiving the card
	 * @return the value of the dealt card
	 */
	public int dealToPlayer(int playerIndex) {
		int card = RandomCardSelector.getInstance().selectCard();
		players.get(playerIndex).addCard(card);
		
		setChanged();
		notifyObservers(buildGameState());
		return card;
		
	}
	public int dealToPlayer(int playerIndex, int side) {
		int card = RandomCardSelector.getInstance().selectCard();
		players.get(playerIndex).addCardToSide(card, side);
		
		setChanged();
		notifyObservers(buildGameState());
		return card;
		
	}
	
	
	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public Dealer getDealer() {
		return dealer;
	}

	/**
	 * Returns the single instance of the ModelManager.
	 *
	 * @return the ModelManager instance
	 */
	public static ModelManager getInstance()
	{
		if (instance==null) instance=new ModelManager();
		return instance;
	}
	
	public void setUsername(String userName) {
		counter.setUsername(userName);
		setChanged();
		notifyObservers(buildGameState());
		
	}
	
	public void incCounter()
	{
		counter.incValue();
		setChanged();
		notifyObservers(buildGameState());
	}
	public void decCounter()
	{
		counter.decValue();
		setChanged();
		notifyObservers(buildGameState());
	}	
	public void resetCounter(int value)
	{
		counter.reset(value);
		setChanged();
		notifyObservers(buildGameState());
	}
	public void addPlayer(int card1, int card2,int capitale) {
		players.add(new Player(card1, card2, capitale));
	}
	public void addSplitCard(int playerIndex, int card, int cardsSide) {
		players.get(playerIndex).addCardToSide(card, cardsSide);
		setChanged();
		notifyObservers(buildGameState());
	}
	
	public void split(int playerIndex) {
		players.get(0).setSplit(true);
		setChanged();
		notifyObservers(buildGameState());
	}
	
	public void addCard(int playerIndex, int card) {
		players.get(playerIndex).addCard(card);
		setChanged();
		notifyObservers(buildGameState());
	}
	public void punta(int playerIndex, int puntata) {
		players.get(playerIndex).punta(puntata);
		setChanged();
		notifyObservers(buildGameState());
	}
	public void raddoppia(int playerIndex) {
		int originalP = players.get(playerIndex).getPuntata(0);
		players.get(playerIndex).punta(originalP);
		setChanged();
		notifyObservers(buildGameState());
	}
	/**
	 * Initializes the game setup, including the players and the dealer, based on the initial capital.
	 *
	 * @param capitale the starting bankroll for the players and the dealer
	 */
	public void initialSetup(int capitale) {
		System.out.println("initializing game");
		for(int i = 0;i < counter.getnPlayers(); i++) {
			System.out.println("adding  player " + i);
			players.add(new Player(0, 0, capitale));
		}
		dealer = new Dealer(0, 0, (capitale*3));
	}
	public void playAgain() {
		for (int i = 0; i < players.size(); i++) {
			players.get(i).resetPuntata();
			players.get(i).resetCards();
			players.get(i).setBust(false);
			players.get(i).setSplit(false);
		}
		ended = false;
		dealer.resetCards();
		dealer.setBust(false);
		RandomCardSelector.getInstance().reset();
		setChanged();
		notifyObservers(buildGameState());
	}
	public void updateView () {
		setChanged();
		notifyObservers(buildGameState());
	}
	public int getPuntataOfPlayer(int playerIndex) {
		return players.get(playerIndex).getPuntata(0);
		
	}
	public int getPuntataOfPlayer(int playerIndex, int side) {
		return players.get(playerIndex).getPuntata(side);
		
	}
	public ArrayList<Integer> getCardsOfPlayer(int playerIndex) {
		return players.get(playerIndex).getCards(0);
		
	}
	public ArrayList<Integer> getCardsOfPlayer(int playerIndex, int side) {
		return players.get(playerIndex).getCards(side);
		
	}
	
	public ArrayList<Integer> getValueOfCardsOfPlayer(int playerIndex) {
		
		return players.get(playerIndex).getValueOfCards();
	}
	
	public int getWinsOfPlayer(int playerIndex) {
		return players.get(playerIndex).getWins();
	}
	
	public boolean hitOrStand() {
		boolean result = dealer.hitOrStand();
		ended = true;
		setChanged();
		notifyObservers(buildGameState());
		return result;
	}
	public void bust(int playerIndex) {
		players.get(playerIndex).setBust(true);
		
		setChanged();
		notifyObservers(buildGameState());
	}
	
	public ArrayList<Integer> getCardsOfDealer() {
		
		return dealer.getCards(0);
	}
	
	public boolean isSplit(int playerIndex) {
		return players.get(0).isSplit();
	}
	public void isBust(int playerIndex) {
		players.get(playerIndex).isBust();
	}
	public String getUserName() {
		return counter.getUsername();
		
	}
	public int getCapitale(int playerIndex) {
		return players.get(playerIndex).getCapitale();
	}
	public void updateModel() 
	{
		incCounter();
		System.out.println(counter);
	}
	
	/**
	 * get the result of the game for each player
	 * @param playerIndex
	 * @return 0 = push, 1 = win , 2 = loss
	 */
	public int getResult(int playerIndex) {
		
		return getResult(playerIndex, 0);
		
	}
	
	public int getResult(int playerIndex, int side) {
	
		
		if(players.get(playerIndex).getSum(side) > 21){
			return 3;
		}else if(players.get(playerIndex).getSum(side) == 21 && players.get(playerIndex).getCards(side).size() == 2) {
			if(dealer.hasBlackJack()) {
				players.get(playerIndex).push(side);
			}else {
				
				players.get(playerIndex).collectBlackJack(side);
			}
			return 4;
		} else if(players.get(playerIndex).getSum(side) == dealer.getSum()) {
			if (dealer.hasBlackJack()) {
				return 2;
			} else {
				players.get(playerIndex).push(side);
				return 0;
			}
			
		}else if (players.get(playerIndex).getSum(side) > dealer.getSum() || dealer.isBust() ) {
			players.get(playerIndex).collectWin(side);;
			return 1;
		}else {
			return 2;
		}
			
		}
	
	public int getSumOfDealer() {
		return dealer.getSum();
	}
	public int getSum(int playerIndex) {
		return players.get(playerIndex).getSum();
	}
	public int getSum(int playerIndex, int side) {
		return players.get(playerIndex).getSum(side);
	}
	public boolean getBust(int playerIndex) {
		return players.get(playerIndex).isBust();
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		counter.setLevel(level);
	}
	/**
	 * 
	 * @param nPlayers number of players
	 */
	public void setnPlayers(int nPlayers) {
		counter.setnPlayers(nPlayers);
	}
	public int getnPlayers() {
		return counter.getnPlayers();
	}
	public boolean isEnded() {
		return ended;
	}

	

	public GameState buildGameState() {
		if (players.size() == 0) {
			return new GameState(
				counter != null ? counter.getUsername() : "",
				0, 0, new ArrayList<>(), 0, 0, false, 
				new ArrayList<>(), new ArrayList<>(), 
				0, 0, 0, 0, false, -1, -1, -1,
				new ArrayList<>(), 0, false, true
			);
		}
		boolean split = isSplit(0);
		boolean endedStatus = isEnded();
		return new GameState(
			counter.getUsername(),
			getCapitale(0),
			getWinsOfPlayer(0),
			getCardsOfPlayer(0),
			getPuntataOfPlayer(0),
			getSum(0),
			split,
			split ? getCardsOfPlayer(0, 1) : new ArrayList<>(),
			split ? getCardsOfPlayer(0, 2) : new ArrayList<>(),
			split ? getPuntataOfPlayer(0, 1) : 0,
			split ? getPuntataOfPlayer(0, 2) : 0,
			split ? getSum(0, 1) : 0,
			split ? getSum(0, 2) : 0,
			endedStatus,
			endedStatus ? getResult(0) : -1,
			(split && endedStatus) ? getResult(0, 1) : -1,
			(split && endedStatus) ? getResult(0, 2) : -1,
			dealer != null ? getCardsOfDealer() : new ArrayList<>(),
			dealer != null ? getSumOfDealer() : 0,
			getBust(0),
			false
		);
	}

	@Override
	public String toString()
	{
		return counter.getValue()+"" ;
	}
}
