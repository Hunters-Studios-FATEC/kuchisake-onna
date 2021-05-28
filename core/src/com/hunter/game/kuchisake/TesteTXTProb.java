package com.hunter.game.kuchisake;

import java.io.FileWriter;
import java.io.IOException;

public class TesteTXTProb {
	
	public void writeTXT(int saveCount, int hideCount ) {
		
		try {
			FileWriter dadosTxt = new FileWriter("database/StatsData.txt");
			dadosTxt.write(saveCount + "\n" + hideCount);
			dadosTxt.close();
			System.out.println("Dados estatísticos registrados.");
		} catch (IOException e) {
			System.out.println("Um erro ocorreu.");
			e.printStackTrace();	
		}
	}
}
