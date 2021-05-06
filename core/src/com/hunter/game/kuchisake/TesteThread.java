package com.hunter.game.kuchisake;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.utils.Array;

public class TesteThread {
	
	Object[][] doorsRoom0;
	Object[][] doorsRoom1;
	Object[][] doorsRoom2;

	Object[][][] doors;

	ArrayList<Integer[]> path;
	ArrayList<ArrayList<Integer[]>> paths;
	
	public TesteThread() {
		doorsRoom0 = new Object[][] {{1}, {"doorUp1-1", 1}, {"doorUp2-2", "doorUp3-2", 1}, {1}};
		doorsRoom1 = new Object[][] {{1}, {"doorDown1-1", 1}, {"doorDown2-1", "doorUp2-1", 2}, {"doorDown2-1", 2}};
		doorsRoom2 = new Object[][] {{1}, {1}, {"doorDown2-2", 1}, {1}};
		
		doors = new Object[][][] {doorsRoom0, doorsRoom1, doorsRoom2};
		
		path = new ArrayList<Integer[]>();
		paths = new ArrayList<ArrayList<Integer[]>>();
	}
	
	public void testarThread(int initialLine, int initialColumn, int finalLine, int finalColumn) {
		int roomLine = initialLine;
		int roomColumn = initialColumn;

		int endLine = finalLine;
		int endColumn = finalColumn;
		
		boolean firstLoop = true;
		boolean foundRoute = false;
		
		int pathIndex = 0;
		
		while(!foundRoute) {
			if(roomLine == endLine) {
				boolean canGo = true;
				
				Object[][] roomsArray = doors[roomLine];
				
				int sector = (int) roomsArray[roomColumn][roomsArray[roomColumn].length - 1];
				int endSector = (int) roomsArray[endColumn][roomsArray[endColumn].length - 1];
				
				for(int i = roomColumn + 1; i <= endColumn; i++) {
					if((int) roomsArray[i][roomsArray[i].length - 1] > sector) {
						canGo = false;
					}
				}

				for(int i = roomColumn - 1; i >= endColumn; i--) {
					if((int) roomsArray[i][roomsArray[i].length - 1] < sector) {
						canGo = false;
					}
				}
				
				if(canGo) {
					ArrayList<Integer[]> newPath = new ArrayList<Integer[]>();
					
					for(int i = roomColumn + 1; i <= endColumn; i++) {
						newPath.add(new Integer[] {roomLine, i, sector});
					}
					
					for(int i = roomColumn - 1; i >= endColumn; i--) {
						newPath.add(new Integer[] {roomLine, i, sector});
					}
					
					if(firstLoop) {
						paths.add(newPath);
					}
					else {
						ArrayList<Integer[]> oldPath = new ArrayList<Integer[]>();
						oldPath.addAll(paths.get(pathIndex));
						
						oldPath.addAll(newPath);
						
						paths.add(oldPath);
					}
				}
				else {
					Array<String> targetSectorDoors = new Array<String>();
					
					for(int i = 0; i < roomsArray.length; i++) {
						if((int) roomsArray[i][roomsArray[i].length - 1] == endSector) {
							Object[] doorsArray = Arrays.copyOfRange(roomsArray[i], 0, roomsArray[i].length - 1);
							ArrayList<String> doorsUserData = new ArrayList<String>();
							
							for(Object door : doorsArray) {
								doorsUserData.add(door.toString());
							}
							
							for(String door : doorsUserData) {
								targetSectorDoors.add(door);
							}
						}
					}
					
					/*Object[][] previousRoomsArray = new Object[][] {};
					Object[][] nextRoomsArray = new Object[][] {};
					
					if(roomLine > 0) {
						previousRoomsArray = doors[roomLine - 1];
					}
					
					if(roomLine < doors.length - 1) {
						nextRoomsArray = doors[roomLine + 1];
					}*/
					
					ArrayList<Integer> addedSectors = new ArrayList<Integer>();
					boolean previousRoomsHasDoors = false;
					boolean nextRoomsHasDoors = false;
					
					for(String targetDoor : targetSectorDoors) {
						if(targetDoor.contains("doorDown")) {
							//int sectorIndex = targetDoor.indexOf("-");
							//int roomSector = Integer.parseInt(targetDoor.substring(sectorIndex + 1));
							//int roomID = Integer.parseInt(targetDoor.substring("doorDown".length(), sectorIndex));
							
							for(int i = 0; i < roomsArray.length; i++) {
								if((int) roomsArray[i][roomsArray[i].length - 1] == sector) {
									Object[] doorsArray = Arrays.copyOfRange(roomsArray[i], 0, roomsArray[i].length - 1);
									ArrayList<String> doorsUserData = new ArrayList<String>();
									
									for(Object door : doorsArray) {
										doorsUserData.add(door.toString());
									}
									
									
									for(String door : doorsUserData) {
										if(door.contains("doorDown") && door.contains("-" + Integer.toString(endSector)) &&
										   !addedSectors.contains(endSector)) {
											int roomID = Integer.parseInt(door.substring("doorDown".length(), door.indexOf("-")));
											
											ArrayList<Integer[]> newPath = new ArrayList<Integer[]>();
											
											for(int j = roomColumn + 1; j <= i; j++) {
												newPath.add(new Integer[] {roomLine, j, sector});
											}

											for(int j = roomColumn - 1; j >= i; j--) {
												newPath.add(new Integer[] {roomLine, j, sector});
											}
											
											newPath.add(new Integer[] {roomLine - 1, roomID, endSector});
											
											if(firstLoop) {
												paths.add(newPath);
											}
											else {
												ArrayList<Integer[]> oldPath = new ArrayList<Integer[]>();
												oldPath.addAll(paths.get(pathIndex));
												
												oldPath.addAll(newPath);
												
												paths.add(oldPath);
											}
											
											//addedSectors.add(roomSector);
											addedSectors.add(endSector);
											previousRoomsHasDoors = true;
										}
									}
								}
							}
						}
					}
					
					//addedSectors.clear();
					
					if(!previousRoomsHasDoors) {
						for(int i = 0; i < roomsArray.length; i++) {
							if((int) roomsArray[i][roomsArray[i].length - 1] == sector) {
								Object[] doorsArray = Arrays.copyOfRange(roomsArray[i], 0, roomsArray[i].length - 1);
								ArrayList<String> doorsUserData = new ArrayList<String>();
								
								for(Object door : doorsArray) {
									doorsUserData.add(door.toString());
								}
								
								for(String door : doorsUserData) {
									int sectorIndex = door.indexOf("-");
									int roomSector = Integer.parseInt(door.substring(sectorIndex + 1));
									
									if(door.contains("doorDown") && !addedSectors.contains(roomSector)) {
										int roomID = Integer.parseInt(door.substring("doorDown".length(), sectorIndex));
										
										ArrayList<Integer[]> newPath = new ArrayList<Integer[]>();
										
										for(int j = roomColumn + 1; j <= i; j++) {
											newPath.add(new Integer[] {roomLine, j, sector});
										}

										for(int j = roomColumn - 1; j >= i; j--) {
											newPath.add(new Integer[] {roomLine, j, sector});
										}
										
										newPath.add(new Integer[] {roomLine - 1, roomID, roomSector});
										
										if(firstLoop) {
											paths.add(newPath);
										}
										else {
											ArrayList<Integer[]> oldPath = new ArrayList<Integer[]>();
											oldPath.addAll(paths.get(pathIndex));
											
											boolean appendOldPath = true;
											for(Integer[] room : oldPath) {
												if(room[0].equals(roomLine - 1) && room[2].equals(roomSector)) {
													appendOldPath = false;
												}
											}
											
											if(appendOldPath) {
												oldPath.addAll(newPath);
												
												paths.add(oldPath);
											}
										}
										
										addedSectors.add(roomSector);
									}
								}
							}
						}
					}
					
					addedSectors.clear();
					
					for(String targetDoor : targetSectorDoors) {
						if(targetDoor.contains("doorUp")) {
							//int sectorIndex = targetDoor.indexOf("-");
							//int roomSector = Integer.parseInt(targetDoor.substring(sectorIndex + 1));
							
							for(int i = 0; i < roomsArray.length; i++) {
								if((int) roomsArray[i][roomsArray[i].length - 1] == sector) {
									Object[] doorsArray = Arrays.copyOfRange(roomsArray[i], 0, roomsArray[i].length - 1);
									ArrayList<String> doorsUserData = new ArrayList<String>();
									
									for(Object door : doorsArray) {
										doorsUserData.add(door.toString());
									}
									
									for(String door : doorsUserData) {
										if(door.contains("doorUp") && door.contains("-" + Integer.toString(endSector)) &&
										   !addedSectors.contains(endSector)) {
											int roomID = Integer.parseInt(door.substring("doorUp".length(), door.indexOf("-")));
											
											ArrayList<Integer[]> newPath = new ArrayList<Integer[]>();
											
											for(int j = roomColumn + 1; j <= i; j++) {
												newPath.add(new Integer[] {roomLine, j, sector});
											}

											for(int j = roomColumn - 1; j >= i; j--) {
												newPath.add(new Integer[] {roomLine, j, sector});
											}
											
											newPath.add(new Integer[] {roomLine + 1, roomID, endSector});
											
											if(firstLoop) {
												paths.add(newPath);
											}
											else {
												ArrayList<Integer[]> oldPath = new ArrayList<Integer[]>();
												oldPath.addAll(paths.get(pathIndex));
												
												oldPath.addAll(newPath);
												
												paths.add(oldPath);
											}
											
											//addedSectors.add(roomSector);
											addedSectors.add(endSector);
											nextRoomsHasDoors = true;
										}
									}
								}
							}
						}
					}
					
					//addedSectors.clear();
					
					if(!nextRoomsHasDoors) {
						for(int i = 0; i < roomsArray.length; i++) {
							if((int) roomsArray[i][roomsArray[i].length - 1] == sector) {
								Object[] doorsArray = Arrays.copyOfRange(roomsArray[i], 0, roomsArray[i].length - 1);
								ArrayList<String> doorsUserData = new ArrayList<String>();
								
								for(Object door : doorsArray) {
									doorsUserData.add(door.toString());
								}
								
								for(String door : doorsUserData) {
									int sectorIndex = door.indexOf("-");
									int roomSector = Integer.parseInt(door.substring(sectorIndex + 1));
									
									if(door.contains("doorUp") && !addedSectors.contains(roomSector)) {
										int roomID = Integer.parseInt(door.substring("doorUp".length(), sectorIndex));
										
										ArrayList<Integer[]> newPath = new ArrayList<Integer[]>();
										
										for(int j = roomColumn + 1; j <= i; j++) {
											newPath.add(new Integer[] {roomLine, j, sector});
										}

										for(int j = roomColumn - 1; j >= i; j--) {
											newPath.add(new Integer[] {roomLine, j, sector});
										}
										
										newPath.add(new Integer[] {roomLine + 1, roomID, roomSector});
										
										if(firstLoop) {
											paths.add(newPath);
										}
										else {
											ArrayList<Integer[]> oldPath = new ArrayList<Integer[]>();
											oldPath.addAll(paths.get(pathIndex));
											
											boolean appendOldPath = true;
											for(Integer[] room : oldPath) {
												if(room[0].equals(roomLine + 1) && room[2].equals(roomSector)) {
													appendOldPath = false;
												}
											}
											
											if(appendOldPath) {
												oldPath.addAll(newPath);
												
												paths.add(oldPath);
											}
										}
										
										addedSectors.add(roomSector);
									}
								}
							}
						}
					}
				}
			}
			else if(Math.abs(roomLine - endLine) == 1) {
				Object[][] roomsArray = doors[roomLine];
				Object[][] targetLineRooms = doors[endLine];
				
				int sector = (int) roomsArray[roomColumn][roomsArray[roomColumn].length - 1];
				int endSector = (int) targetLineRooms[endColumn][targetLineRooms[endColumn].length - 1];
				
				Array<String> targetSectorDoors = new Array<String>();
				
				for(int i = 0; i < targetLineRooms.length; i++) {
					if((int) targetLineRooms[i][targetLineRooms[i].length - 1] == endSector) {
						Object[] doorsArray = Arrays.copyOfRange(targetLineRooms[i], 0, targetLineRooms[i].length - 1);
						ArrayList<String> doorsUserData = new ArrayList<String>();
						
						for(Object door : doorsArray) {
							doorsUserData.add(door.toString());
						}
						
						for(String door : doorsUserData) {
							targetSectorDoors.add(door);
						}
					}
				}
				
				/*Object[][] previousRoomsArray = new Object[][] {};
				Object[][] nextRoomsArray = new Object[][] {};
				
				if(roomLine > 0) {
					previousRoomsArray = doors[roomLine - 1];
				}
				
				if(roomLine < doors.length - 1) {
					nextRoomsArray = doors[roomLine + 1];
				}*/
				
				ArrayList<Integer> addedSectors = new ArrayList<Integer>();
				boolean previousRoomsHasDoors = false;
				boolean nextRoomsHasDoors = false;
				
				for(String targetDoor : targetSectorDoors) {
					if(targetDoor.contains("doorDown")) {
						//int sectorIndex = targetDoor.indexOf("-");
						//int roomSector = Integer.parseInt(targetDoor.substring(sectorIndex + 1));
						//int roomID = Integer.parseInt(targetDoor.substring("doorDown".length(), sectorIndex));
						
						for(int i = 0; i < roomsArray.length; i++) {
							if((int) roomsArray[i][roomsArray[i].length - 1] == sector) {
								Object[] doorsArray = Arrays.copyOfRange(roomsArray[i], 0, roomsArray[i].length - 1);
								ArrayList<String> doorsUserData = new ArrayList<String>();
								
								for(Object door : doorsArray) {
									doorsUserData.add(door.toString());
								}
								
								for(String door : doorsUserData) {
									if(door.contains("doorUp") && door.contains("-" + Integer.toString(endSector)) &&
									   !addedSectors.contains(endSector)) {
										int roomID = Integer.parseInt(door.substring("doorUp".length(), door.indexOf("-")));
										
										ArrayList<Integer[]> newPath = new ArrayList<Integer[]>();
										
										for(int j = roomColumn + 1; j <= i; j++) {
											newPath.add(new Integer[] {roomLine, j, sector});
										}

										for(int j = roomColumn - 1; j >= i; j--) {
											newPath.add(new Integer[] {roomLine, j, sector});
										}
										
										newPath.add(new Integer[] {roomLine + 1, roomID, endSector});
										
										if(firstLoop) {
											paths.add(newPath);
										}
										else {
											ArrayList<Integer[]> oldPath = new ArrayList<Integer[]>();
											oldPath.addAll(paths.get(pathIndex));
											
											oldPath.addAll(newPath);
											
											paths.add(oldPath);
										}
										
										addedSectors.add(endSector);
										nextRoomsHasDoors = true;
									}
								}
							}
						}
					}
				}
				
				//addedSectors.clear();
				
				if(!nextRoomsHasDoors) {
					for(int i = 0; i < roomsArray.length; i++) {
						if((int) roomsArray[i][roomsArray[i].length - 1] == sector) {
							Object[] doorsArray = Arrays.copyOfRange(roomsArray[i], 0, roomsArray[i].length - 1);
							ArrayList<String> doorsUserData = new ArrayList<String>();
							
							for(Object door : doorsArray) {
								doorsUserData.add(door.toString());
							}
							
							for(String door : doorsUserData) {
								int sectorIndex = door.indexOf("-");
								int roomSector = Integer.parseInt(door.substring(sectorIndex + 1));
								
								if(door.contains("doorUp") && !addedSectors.contains(roomSector)) {
									int roomID = Integer.parseInt(door.substring("doorUp".length(), sectorIndex));
									
									ArrayList<Integer[]> newPath = new ArrayList<Integer[]>();
									
									for(int j = roomColumn + 1; j <= i; j++) {
										newPath.add(new Integer[] {roomLine, j, sector});
									}

									for(int j = roomColumn - 1; j >= i; j--) {
										newPath.add(new Integer[] {roomLine, j, sector});
									}
									
									newPath.add(new Integer[] {roomLine + 1, roomID, roomSector});
									
									if(firstLoop) {
										paths.add(newPath);
									}
									else {
										ArrayList<Integer[]> oldPath = new ArrayList<Integer[]>();
										oldPath.addAll(paths.get(pathIndex));
										
										boolean appendOldPath = true;
										for(Integer[] room : oldPath) {
											if(room[0].equals(roomLine + 1) && room[2].equals(roomSector)) {
												appendOldPath = false;
											}
										}
										
										if(appendOldPath) {
											oldPath.addAll(newPath);
											
											paths.add(oldPath);
										}
									}
									
									addedSectors.add(roomSector);
								}
							}
						}
					}
				}
				
				addedSectors.clear();
				
				for(String targetDoor : targetSectorDoors) {
					if(targetDoor.contains("doorUp")) {
						//int sectorIndex = targetDoor.indexOf("-");
						//int roomSector = Integer.parseInt(targetDoor.substring(sectorIndex + 1));
						
						for(int i = 0; i < roomsArray.length; i++) {
							if((int) roomsArray[i][roomsArray[i].length - 1] == sector) {
								Object[] doorsArray = Arrays.copyOfRange(roomsArray[i], 0, roomsArray[i].length - 1);
								ArrayList<String> doorsUserData = new ArrayList<String>();
								
								for(Object door : doorsArray) {
									doorsUserData.add(door.toString());
								}
								
								for(String door : doorsUserData) {
									if(door.contains("doorDown") && door.contains("-" + Integer.toString(endSector)) &&
									   !addedSectors.contains(endSector)) {
										int roomID = Integer.parseInt(door.substring("doorDown".length(), door.indexOf("-")));
										
										ArrayList<Integer[]> newPath = new ArrayList<Integer[]>();
										
										for(int j = roomColumn + 1; j <= i; j++) {
											newPath.add(new Integer[] {roomLine, j, sector});
										}

										for(int j = roomColumn - 1; j >= i; j--) {
											newPath.add(new Integer[] {roomLine, j, sector});
										}
										
										newPath.add(new Integer[] {roomLine - 1, roomID, endSector});
										
										if(firstLoop) {
											paths.add(newPath);
										}
										else {
											ArrayList<Integer[]> oldPath = new ArrayList<Integer[]>();
											oldPath.addAll(paths.get(pathIndex));
											
											oldPath.addAll(newPath);
											
											paths.add(oldPath);
										}
										
										addedSectors.add(endSector);
										previousRoomsHasDoors = true;
									}
								}
							}
						}
					}
				}
				
				//addedSectors.clear();
				
				if(!previousRoomsHasDoors) {
					for(int i = 0; i < roomsArray.length; i++) {
						if((int) roomsArray[i][roomsArray[i].length - 1] == sector) {
							Object[] doorsArray = Arrays.copyOfRange(roomsArray[i], 0, roomsArray[i].length - 1);
							ArrayList<String> doorsUserData = new ArrayList<String>();
							
							for(Object door : doorsArray) {
								doorsUserData.add(door.toString());
							}
							
							for(String door : doorsUserData) {
								int sectorIndex = door.indexOf("-");
								int roomSector = Integer.parseInt(door.substring(sectorIndex + 1));
								
								if(door.contains("doorDown") && !addedSectors.contains(roomSector)) {
									int roomID = Integer.parseInt(door.substring("doorDown".length(), sectorIndex));
									
									ArrayList<Integer[]> newPath = new ArrayList<Integer[]>();
									
									for(int j = roomColumn + 1; j <= i; j++) {
										newPath.add(new Integer[] {roomLine, j, sector});
									}

									for(int j = roomColumn - 1; j >= i; j--) {
										newPath.add(new Integer[] {roomLine, j, sector});
									}
									
									newPath.add(new Integer[] {roomLine - 1, roomID, roomSector});
									
									if(firstLoop) {
										paths.add(newPath);
									}
									else {
										ArrayList<Integer[]> oldPath = new ArrayList<Integer[]>();
										oldPath.addAll(paths.get(pathIndex));
										
										boolean appendOldPath = true;
										for(Integer[] room : oldPath) {
											if(room[0].equals(roomLine - 1) && room[2].equals(roomSector)) {
												appendOldPath = false;
											}
										}
										
										if(appendOldPath) {
											oldPath.addAll(newPath);
											
											paths.add(oldPath);
										}
									}
									
									addedSectors.add(roomSector);
								}
							}
						}
					}
				}
			}
			else {
				Object[][] roomsArray = doors[roomLine];
				Object[][] targetLineRooms = doors[endLine];
				
				int sector = (int) roomsArray[roomColumn][roomsArray[roomColumn].length - 1];
				int endSector = (int) targetLineRooms[endColumn][targetLineRooms[endColumn].length - 1];
				
				/*Object[][] previousRoomsArray = new Object[][] {};
				Object[][] nextRoomsArray = new Object[][] {};
				
				if(roomLine > 0) {
					previousRoomsArray = doors[roomLine - 1];
				}
				
				if(roomLine < doors.length - 1) {
					nextRoomsArray = doors[roomLine + 1];
				}*/
				
				ArrayList<Integer> addedSectors = new ArrayList<Integer>();
				//boolean previousRoomsHasDoors = false;
				//boolean nextRoomsHasDoors = false;
				
				for(int i = 0; i < roomsArray.length; i++) {
					if((int) roomsArray[i][roomsArray[i].length - 1] == sector) {
						Object[] doorsArray = Arrays.copyOfRange(roomsArray[i], 0, roomsArray[i].length - 1);
						ArrayList<String> doorsUserData = new ArrayList<String>();
						
						for(Object door : doorsArray) {
							doorsUserData.add(door.toString());
						}
						
						for(String door : doorsUserData) {
							int sectorIndex = door.indexOf("-");
							int roomSector = Integer.parseInt(door.substring(sectorIndex + 1));
							
							if(door.contains("doorUp") && !addedSectors.contains(roomSector)) {
								int roomID = Integer.parseInt(door.substring("doorUp".length(), sectorIndex));
								
								ArrayList<Integer[]> newPath = new ArrayList<Integer[]>();
								
								for(int j = roomColumn + 1; j <= i; j++) {
									newPath.add(new Integer[] {roomLine, j, sector});
								}

								for(int j = roomColumn - 1; j >= i; j--) {
									newPath.add(new Integer[] {roomLine, j, sector});
								}
								
								newPath.add(new Integer[] {roomLine + 1, roomID, roomSector});
								
								if(firstLoop) {
									paths.add(newPath);
								}
								else {
									ArrayList<Integer[]> oldPath = new ArrayList<Integer[]>();
									oldPath.addAll(paths.get(pathIndex));
									
									boolean appendOldPath = true;
									for(Integer[] room : oldPath) {
										if(room[0].equals(roomLine + 1) && room[2].equals(roomSector)) {
											appendOldPath = false;
										}
									}
									
									if(appendOldPath) {
										oldPath.addAll(newPath);
										
										paths.add(oldPath);
									}
								}
								
								addedSectors.add(roomSector);
							}
						}
					}
				}
				
				addedSectors.clear();
				
				for(int i = 0; i < roomsArray.length; i++) {
					if((int) roomsArray[i][roomsArray[i].length - 1] == sector) {
						Object[] doorsArray = Arrays.copyOfRange(roomsArray[i], 0, roomsArray[i].length - 1);
						ArrayList<String> doorsUserData = new ArrayList<String>();
						
						for(Object door : doorsArray) {
							doorsUserData.add(door.toString());
						}
						
						for(String door : doorsUserData) {
							int sectorIndex = door.indexOf("-");
							int roomSector = Integer.parseInt(door.substring(sectorIndex + 1));
							
							if(door.contains("doorDown") && !addedSectors.contains(roomSector)) {
								int roomID = Integer.parseInt(door.substring("doorDown".length(), sectorIndex));
								
								ArrayList<Integer[]> newPath = new ArrayList<Integer[]>();
								
								for(int j = roomColumn + 1; j <= i; j++) {
									newPath.add(new Integer[] {roomLine, j, sector});
								}

								for(int j = roomColumn - 1; j >= i; j--) {
									newPath.add(new Integer[] {roomLine, j, sector});
								}
								
								newPath.add(new Integer[] {roomLine - 1, roomID, roomSector});
								
								if(firstLoop) {
									paths.add(newPath);
								}
								else {
									ArrayList<Integer[]> oldPath = new ArrayList<Integer[]>();
									oldPath.addAll(paths.get(pathIndex));
									
									boolean appendOldPath = true;
									for(Integer[] room : oldPath) {
										if(room[0].equals(roomLine - 1) && room[2].equals(roomSector)) {
											appendOldPath = false;
										}
									}
									
									if(appendOldPath) {
										oldPath.addAll(newPath);
										
										paths.add(oldPath);
									}
								}
								
								addedSectors.add(roomSector);
							}
						}
					}
				}
			}
			
			if(firstLoop) {
				firstLoop = false;
			}
			else {
				pathIndex++;	
			}
			
			roomLine = paths.get(pathIndex).get(paths.get(pathIndex).size() - 1)[0];
			roomColumn = paths.get(pathIndex).get(paths.get(pathIndex).size() - 1)[1];
			
			for(ArrayList<Integer[]> foundPath : paths) {
				if(foundPath.get(foundPath.size() - 1)[0].equals(endLine) && 
				   foundPath.get(foundPath.size() - 1)[1].equals(endColumn)) {
					path.addAll(foundPath);
					foundRoute = true;
					break;
				}
			}
		}
		
		
		System.out.println("Mostrando todos os caminhos da lista Paths: ");
		for(ArrayList<Integer[]> teste : paths) {
			String caminho = "";
			
			for(Integer[] posicao : teste) {
				String pos  = "(";
				for(Integer num : posicao) {
					pos += num.toString();
					pos += " - ";
				}
				pos += ")";
				
				caminho += pos;
				caminho += " -> ";
			}
			
			System.out.println(caminho);
		}
		System.out.println("///////////////////////////////////////////////////////");
		
		/*String caminho = "";
		for(Integer[] posicao : path) {
			String pos  = "(";
			for(Integer num : posicao) {
				pos += num.toString();
				pos += " - ";
			}
			pos += ")";
			
			caminho += pos;
			caminho += " -> ";
		}
		
		System.out.println(caminho);*/
	}

}
