package me.Khajiitos.tictactoe;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Game {
	
	
	// -1 - invalid slot
	// 0 - empty
	// 1 - slot occupied by the inviter
	// 2 - slot occupied by the invited
	
	private int turn = 1; // 1 - inviter's turn  2 - invited's turn
	
	private Player inviter;
	
	private Player invited;
	
	int a1 = 0;
	
	int a2 = 0;
	
	int a3 = 0;
	
	int b1 = 0;
	
	int b2 = 0;
	
	int b3 = 0;
	
	int c1 = 0;
	
	int c2 = 0;
	
	int c3 = 0;
	
	int secondspassed = 0;
	
	public Game(Player inviter, Player invited) {
		this.inviter = inviter;
		this.invited = invited;
	}
	
	public Player getInviter() {
		return inviter;
	}
	
	public Player getInvited() {
		return invited;
	}
	
	public Player getOpponent(Player player) {
		
		if (getInviter().equals(player)) {
			return getInvited();
		}
		else if (getInvited().equals(player)) {
			return getInviter();
		}
		else {
			return null;
		}
		
	}
	
	public int getTurn() {
		return turn;
	}
	
	public void nextTurn() {
		if (turn == 1) {
			turn = 2;
		} else {
			turn = 1;
		}
		
		if (turn == 1) {
			inviter.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "It's your turn!");
			invited.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "It's your opponent's turn!");
		} else {
			invited.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "It's your turn!");
			inviter.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "It's your opponent's turn!");
		}
	}
	
	public void end(int result) {
		
		TicTacToe.printGame(getInviter(), this);
		TicTacToe.printGame(getInvited(), this);
		
		if (result == 1) {
			getInviter().sendMessage(ChatColor.DARK_GREEN + "You won!");
			getInvited().sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + getInviter().getName() + ChatColor.YELLOW + " has won the game.");
		} else if (result == 2) {
			getInvited().sendMessage(ChatColor.DARK_GREEN + "You won!");
			getInviter().sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + getInvited().getName() + ChatColor.YELLOW + " has won the game.");
		} else {
			getInviter().sendMessage(ChatColor.DARK_AQUA + "It's a draw!");
			getInvited().sendMessage(ChatColor.DARK_AQUA + "It's a draw!");
		}
		
		TicTacToe.games.remove(this);
	}
	
	public void onScorePlaced() {
		
		secondspassed = 0;
		
		if (GameMechanic.checkForWinner(this) != 0) {
			int result = GameMechanic.checkForWinner(this);
			end(result);
			return;
		}
		
		TicTacToe.printGame(getInviter(), this);
		TicTacToe.printGame(getInvited(), this);
		nextTurn();
	}
	
	public void setScore(String str, int score) {
		String s = str.toLowerCase();
		
		switch (s) {
		case "a1":
			a1 = score;
			break;
		case "a2":
			a2 = score;
			break;
		case "a3":
			a3 = score;
			break;
		case "b1":
			b1 = score;
			break;
		case "b2":
			b2 = score;
			break;
		case "b3":
			b3 = score;
			break;
		case "c1":
			c1 = score;
			break;
		case "c2":
			c2 = score;
			break;
		case "c3":
			c3 = score;
			break;
		default:
			throw new Error(str + " is not a valid slot.");
		}
	}
	
	public void failedToPlace() {
		if (getTurn() == 1) {
			getInviter().sendMessage(ChatColor.RED + "You failed to select your answer.");
			getInvited().sendMessage(ChatColor.DARK_RED + getInviter().getName() + ChatColor.RED + " failed to select an answer.");
		} else {
			getInvited().sendMessage(ChatColor.RED + "You failed to select your answer.");
			getInviter().sendMessage(ChatColor.DARK_RED + getInvited().getName() + ChatColor.RED + " failed to select an answer.");
		}
		onScorePlaced();
	}
	
	public int getScore(String str) {
		
		switch (str.toLowerCase()) {
		case "a1":
			return a1;
		case "a2":
			return a2;
		case "a3":
			return a3;
		case "b1":
			return b1;
		case "b2":
			return b2;
		case "b3":
			return b3;
		case "c1":
			return c1;
		case "c2":
			return c2;
		case "c3":
			return c3;
		default:
			return -1;
		}
		
	}

}
