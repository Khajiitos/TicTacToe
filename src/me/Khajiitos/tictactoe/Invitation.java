package me.Khajiitos.tictactoe;

import org.bukkit.entity.Player;

public class Invitation {
	
	private Player sender;
	
	private Player target;
	
	private long expiretime;
	
	public Invitation(Player sender, Player target, long expiretime) {
		this.sender = sender;
		this.target = target;
		this.expiretime = expiretime;
	}
	
	public Player getSender() {
		return sender;
	}
	
	public Player getTarget() {
		return target;
	}
	
	public long getExpiretime() {
		return expiretime;
	}
	
}
