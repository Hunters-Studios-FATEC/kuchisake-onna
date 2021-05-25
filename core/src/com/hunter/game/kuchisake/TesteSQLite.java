package com.hunter.game.kuchisake;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.badlogic.gdx.utils.Array;

public class TesteSQLite {
	
	Array<String> backpack;
	int KuchisakeLine;
	int KuchisakeColumn;
	boolean lockCompleted;
	boolean livroCompleted;
	boolean fiosCompleted;
	boolean geradorCompleted;
	int savesN;
	int hideN;
	
	public void connect(Array<String> mochila, int Kline, int KColumn, boolean lockpick, boolean livros, boolean fios, boolean gerador, int saveCount, int hideCount) {  
        Connection conn = null;  
        try {
            // db parameters  
            String url = "jdbc:sqlite:database/test.db";  
            // create a connection to the database  
            conn = DriverManager.getConnection(url);  
               
            System.out.println("Connection to SQLite has been established.");
            
            Statement statement = conn.createStatement();
            
            String kuchiTable = "CREATE TABLE IF NOT EXISTS kuchisakePos("
            				   + "kucshisakeLine INTEGER,"
            				   + "kuchisakeColumn INTEGER"
            		           + ")";
            
            String mochilaTable = "CREATE TABLE IF NOT EXISTS playerBackpack("
            				   + "ItemName VARCHAR(30)"
            		           + ")";
            
            // 1 = true e 0 = false
            String minigameTable = "CREATE TABLE IF NOT EXISTS minigameCompleted("
            					+ "lockCompleted INTEGER,"
            					+ "livroCompleted INTEGER,"
            					+ "fiosCompleted INTEGER,"
            					+ "geradorCompleted INTEGER"
            					+ ")";
            
            String statisticCount = "CREATE TABLE IF NOT EXISTS statsCounter("
            		+ "saveCount INTEGER,"
            		+ "hideCount INTEGER"
            		+ ")";
            		
            statement.execute(kuchiTable);
            statement.execute(mochilaTable);
            statement.execute(minigameTable);
            statement.execute(statisticCount);
            
            statement.execute("DELETE FROM kuchisakePos");
            
            statement.execute("DELETE FROM playerBackpack");
            
            statement.execute("DELETE FROM minigameCompleted");
            
            statement.execute("DELETE FROM statsCounter");

            
        	statement.executeUpdate("INSERT INTO kuchisakePos VALUES (" + Kline + "," + KColumn + ")");
            
        	for (String itemName : mochila) {
        		statement.executeUpdate("INSERT INTO playerBackpack VALUES ('" + itemName + "')");
        	}
        	
        	statement.executeUpdate("INSERT INTO minigameCompleted VALUES (" + lockpick + "," + livros + "," + fios + "," + gerador + ")");
        	
        	statement.executeUpdate("INSERT INTO statsCounter VALUES (" + saveCount + "," + hideCount + ")");
        	
            
              
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        } finally {  
            try {  
                if (conn != null) {  
                    conn.close();  
                }  
            } catch (SQLException ex) {  
                System.out.println(ex.getMessage());  
            }  
        }  
    }
	
	public void getData() {
		
		backpack = new Array<String>();
		
		Connection conn = null;  
        try {
            String url = "jdbc:sqlite:database/test.db";  
            conn = DriverManager.getConnection(url); 
            
            System.out.println("Connection to SQLite has been established.");
            
            Statement statement = conn.createStatement();
            
            ResultSet rs = statement.executeQuery("SELECT kucshisakeLine,kuchisakeColumn FROM kuchisakePos");
            
            while (rs.next()) {
            	KuchisakeLine = rs.getInt("kucshisakeLine");
            	KuchisakeColumn = rs.getInt("kuchisakeColumn");
            }
            
            ResultSet rsMo = statement.executeQuery("SELECT ItemName FROM playerBackpack");
            
            while (rsMo.next()) {
            	backpack.add(rsMo.getString("ItemName"));
            }
            
            ResultSet rsMini = statement.executeQuery("SELECT lockCompleted,livroCompleted,fiosCompleted,geradorCompleted from minigameCompleted");
            
            while (rsMini.next()) {
            	lockCompleted = rsMini.getBoolean("lockCompleted");
            	livroCompleted = rsMini.getBoolean("livroCompleted");
            	fiosCompleted = rsMini.getBoolean("fiosCompleted");
            	geradorCompleted = rsMini.getBoolean("geradorCompleted");
            }
            
            ResultSet rsCou = statement.executeQuery("SELECT saveCount,hideCount from statsCounter");
            
            while (rsCou.next()) {
            	savesN = rsCou.getInt("saveCount");
            	hideN = rsCou.getInt("hideCount");
            }
        	
        }
        catch (SQLException e) {
        	System.out.println(e.getMessage());
        } finally {
            try {  
                if (conn != null) {  
                    conn.close();  
                }  
            } catch (SQLException ex) {  
                System.out.println(ex.getMessage());  
                }
       }
	}

	public Array<String> getBackpack() {
		return backpack;
	}

	public int getKuchisakeLine() {
		return KuchisakeLine;
	}

	public int getKuchisakeColumn() {
		return KuchisakeColumn;
	}

	public boolean getLockCompleted() {
		return lockCompleted;
	}

	public boolean getLivroCompleted() {
		return livroCompleted;
	}

	public boolean getFiosCompleted() {
		return fiosCompleted;
	}

	public boolean getGeradorCompleted() {
		return geradorCompleted;
	}

	public int getSavesN() {
		return savesN;
	}

	public int getHideN() {
		return hideN;
	}
	
	
}
