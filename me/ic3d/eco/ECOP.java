package me.ic3d.eco;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.validation.NotEmpty;

/**
 *
 * @author IC3D (its sammy's tutorial database thingy, idk)
 */
@Entity()
@Table(name = "Economy_Stuff")
public class ECOP {

    @Id
    @GeneratedValue
    private int id; // Database Id
    @NotEmpty
    private String PlayerName; //Minecraft account name
    private Integer Holdings;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPlayerName() {
		return PlayerName;
	}
	public void setPlayerName(String playerName) {
		PlayerName = playerName;
	}
	public Integer getHoldings() {
		return Holdings;
	}
	public void setHoldings(Integer holdings) {
		Holdings = holdings;
	}
}