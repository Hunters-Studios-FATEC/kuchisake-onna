package com.hunter.game.kuchisake.objects;

import com.badlogic.gdx.utils.Array;
import com.hunter.game.kuchisake.TerrorGame;

import java.util.ArrayList;
import java.util.Arrays;

public class KuchisakeThread {

    Object[][] doorsRoom0;
    Object[][] doorsRoom1;
    Object[][] doorsRoom2;
    Object[][] doorsRoom3;

    Object[][][] doors;
    ArrayList<Integer[]> path;
    ArrayList<ArrayList<Integer[]>> paths;

    Float[][] doorsPosX0;
    Float[][] doorsPosX1;
    Float[][] doorsPosX2;
    Float[][] doorsPosX3;

    Float[][][] doorsPosX;

    public KuchisakeThread() {
        doorsRoom0 = new Object[][] {{"doorUp0-0", 0}, {"doorUp2-1", 0}, {"doorUp4-2", "doorUp5-3", 0}};
        doorsRoom1 = new Object[][] {
                {"doorUp0-0", "doorDown0-0", 0}, {"doorUp1-1", 1}, {"doorUp2-2", "doorDown1-0", "doorUp3-3", 1},
                {"doorUp4-4", "doorUp5-5", 1}, {"doorDown2-0", 2}, {"doorDown2-0", "doorUp6-6", 3}};
        doorsRoom2 = new Object[][] {{"doorDown0-0", "doorUp0-0", 0}, {"doorDown1-1", 1}, {"doorDown2-1", 2},
                {"doorDown2-1", 3}, {"doorDown3-1", 4}, {"doorDown3-1", 5}, {"doorDown5-3", 6}};
        doorsRoom3 = new Object[][] {{"doorDown0-0", 0}};

        doors = new Object[][][] {doorsRoom0, doorsRoom1, doorsRoom2, doorsRoom3};


        doorsPosX0 = new Float[][]{{483f / TerrorGame.SCALE}, {1750f / TerrorGame.SCALE}, {600f / TerrorGame.SCALE, 3143f / TerrorGame.SCALE}};
        doorsPosX1 = new Float[][]{{600f / TerrorGame.SCALE, 2891f / TerrorGame.SCALE}, {1750f / TerrorGame.SCALE}, {(420 + 280f / 2f) / TerrorGame.SCALE, 1750f / TerrorGame.SCALE, (2800 + 280f / 2f) / TerrorGame.SCALE}, {971f / TerrorGame.SCALE, 2487f / TerrorGame.SCALE}, {1950 / TerrorGame.SCALE}, {600f / TerrorGame.SCALE, 2900f / TerrorGame.SCALE}};
        doorsPosX2 = new Float[][]{{(971+230f) / TerrorGame.SCALE, (2487+230f) / TerrorGame.SCALE}, {2810f / TerrorGame.SCALE}, {483f / TerrorGame.SCALE}, {2891f / TerrorGame.SCALE}, {483f / TerrorGame.SCALE}, {2891f / TerrorGame.SCALE}, {500f / TerrorGame.SCALE}};
        doorsPosX3 = new Float[][]{{(2487+230f) / TerrorGame.SCALE}};

        doorsPosX = new Float[][][] {doorsPosX0, doorsPosX1, doorsPosX2, doorsPosX3};
        
        path = new ArrayList<Integer[]>();
        paths = new ArrayList<ArrayList<Integer[]>>();
    }

    public void runThread(int initialLine, int initialColumn, int finalLine, int finalColumn) {
    	path.clear();
    	paths.clear();
    	
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

                    ArrayList<Integer> addedSectors = new ArrayList<Integer>();
                    boolean previousRoomsHasDoors = false;
                    boolean nextRoomsHasDoors = false;

                    for(String targetDoor : targetSectorDoors) {
                        if(targetDoor.contains("doorDown")) {

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

                    addedSectors.clear();

                    for(String targetDoor : targetSectorDoors) {
                        if(targetDoor.contains("doorUp")) {

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

                ArrayList<Integer> addedSectors = new ArrayList<Integer>();
                boolean previousRoomsHasDoors = false;
                boolean nextRoomsHasDoors = false;

                for(String targetDoor : targetSectorDoors) {
                    if(targetDoor.contains("doorDown")) {

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

                ArrayList<Integer> addedSectors = new ArrayList<Integer>();

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
    }

    public ArrayList<Integer[]> getPath(){
        return path;
    }

    public Object[][][] getDoors() {
        return doors;
    }

    public Float[][][] getDoorsPosX() {
        return doorsPosX;
    }
}
