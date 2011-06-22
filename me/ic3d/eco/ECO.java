	package me.ic3d.eco;
//Java Imports
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.PersistenceException;

import me.ic3d.eco.Command.ECOCMD;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class ECO extends JavaPlugin {
	
	private boolean UsePermissions;
	public static PermissionHandler Permissions;
	private void setupPermissions() {
	    Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");
	    if (this.Permissions == null) {
	        if (test != null) {
	            UsePermissions = true;
	            this.Permissions = ((Permissions) test).getHandler();
	            System.out.println("[3co] Version 1.0 Permissions system detected!");
	        } else {
	            log.info("[3co] Version 1.0 Permissions system not detected, defaulting to OP");
	            UsePermissions = false;
	        }
	    }
	}
	
    public boolean hasPerm(Player p, String string) {
        if (UsePermissions) {
            return this.Permissions.has(p, "3co." + string);
        }
        return p.isOp();
    }
	
	 private void setupDatabase() {
	        try {
	            getDatabase().find(ECOP.class).findRowCount();
	        } catch (PersistenceException ex) {
	            installDDL();
	        }
	    }
	    @Override
	    public List<Class<?>> getDatabaseClasses() {
	        List<Class<?>> list = new ArrayList<Class<?>>();
	        list.add(ECOP.class);
	        return list;
	    }
	public Configuration config;
	public String singularCurrency;
	public String pluralCurrency;
	
	private static final Logger log = Logger.getLogger("Minecraft");
	
	public File database = new File("plugins/3co/3co.db");
	
	public void onEnable() {
	    setupDatabase();
	    setupPermissions();
		this.config = this.getConfiguration();
		singularCurrency = this.config.getString("Currency.Singuluar", "Dollar");
		pluralCurrency = this.config.getString("Currency.Plural", "Dollars");
		config.save();
		this.getCommand("eco").setExecutor(new ECOCMD(this));
		log.info("[3co] Version " + this.getDescription().getVersion() + " by IC3D enabled");
		for(World world : this.getServer().getWorlds()) {
			for(Player player : world.getPlayers()) {
				ECOP pClass = getDatabase().find(ECOP.class).where().ieq("PlayerName", player.getName()).findUnique();
				if (pClass== null) {
					pClass = new ECOP ();
					pClass.setPlayerName(player.getName());
					pClass.setHoldings(0);
				}
			}
		}
	}
	public void onDisable() {
		log.info("[3co] Version " + this.getDescription().getVersion() + " by IC3D disabled");
	}
	
	public Integer getMoney(Player player) {
		ECOP pClass = getDatabase().find(ECOP.class).where().ieq("PlayerName", player.getName()).findUnique();
		if (pClass== null) {
			pClass = new ECOP ();
			pClass.setPlayerName(player.getName());
			pClass.setHoldings((Integer) 0);
		}
		return pClass.getHoldings().intValue();
	}
	
	public void setMoney(Player player, Integer amount) {
		ECOP pClass = getDatabase().find(ECOP.class).where().ieq("PlayerName", player.getName()).findUnique();
		if (pClass== null) {
			pClass = new ECOP ();
			pClass.setPlayerName(player.getName());
			pClass.setHoldings((Integer) 0);
		}
		pClass.setHoldings(amount);
		this.getDatabase().save(pClass);
	}
	public void giveMoney(Player player, Integer amount) {
		ECOP pClass = getDatabase().find(ECOP.class).where().ieq("PlayerName", player.getName()).findUnique();
		if (pClass== null) {
			pClass = new ECOP ();
			pClass.setPlayerName(player.getName());
			pClass.setHoldings((Integer) 0);
		}
		Integer oldmoney = pClass.getHoldings();
		if(amount.toString().contains("-")) {
			return;
		}
		Integer newmoney = (Integer) (oldmoney + amount);
		pClass.setHoldings(newmoney);
		this.getDatabase().save(pClass);
	}
	public void takeMoney(Player player, Integer amount) {
		ECOP pClass = getDatabase().find(ECOP.class).where().ieq("PlayerName", player.getName()).findUnique();
		if (pClass== null) {
			pClass = new ECOP ();
			pClass.setPlayerName(player.getName());
			pClass.setHoldings((Integer )0);
		}
		Integer oldmoney = pClass.getHoldings();
		if(amount.toString().contains("-")) {
			return;
		}
		Integer newmoney = (Integer) (oldmoney - amount);
		pClass.setHoldings(newmoney);
		this.getDatabase().save(pClass);
	}
	public String getPluralCurrency() {
		return this.pluralCurrency;
	}
	public String getSingularCurrency() {
		return this.singularCurrency;
	}
	public Boolean hasEnough(Player player, Integer amount) {
		ECOP pClass = getDatabase().find(ECOP.class).where().ieq("PlayerName", player.getName()).findUnique();
		if (pClass== null) {
			pClass = new ECOP ();
			pClass.setPlayerName(player.getName());
			pClass.setHoldings((Integer) 0);
		}
		Integer current = pClass.getHoldings();
		if(amount > current) {
			return false;
		} else {
			return true;
		}
	}
	public Boolean hasAccount(Player player) {
		ECOP pClass = getDatabase().find(ECOP.class).where().ieq("PlayerName", player.getName()).findUnique();
		if (pClass == null) {
			return true;
		} else {
			return false;
		}
	}
	public String getVersion() {
		return this.getDescription().getVersion();
	}
	public void createAccount(Player player, Integer amount) {
		ECOP pClass = getDatabase().find(ECOP.class).where().ieq("PlayerName", player.getName()).findUnique();
		if (pClass== null) {
			pClass = new ECOP ();
			pClass.setPlayerName(player.getName());
			pClass.setHoldings(amount);
			this.getDatabase().save(pClass);
		} else {
			return;
		}
	}
	public void transferMoney(Player from, Player to, Integer amount) {
		this.takeMoney(from, amount);
		this.giveMoney(to, amount);
	}	
}
