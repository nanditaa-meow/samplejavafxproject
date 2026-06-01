public class CircuitBoard {
    public static Point[][] points;
    
    private static boolean firstLoop;
    
    
    public CircuitBoard(){
        points = new Point[10][10]; // creates the 4x4 board
        
        for (int i = 0; i < points.length; i++){ // creates a point in each board
            for (int j = 0; j < points[0].length; j++){
                points[i][j] = new Point();
            }
            firstLoop = true;
        }
    }
    
    public static int[] findBattery(){ // uses point class find function to find battery
        for (int i = 0; i < points.length; i++){
            for (int j = 0; j < points[0].length; j++){
                if(!(points[i][j].find("battery").equals("item not found"))){
                    System.out.println("batterylovk" + i);
                    System.out.println(j);
                    return new int[]{i,j};
                }
            }
           
        }
         System.out.println("No Battery Found. Please add a Battery.");
            return new int[]{0,0};
    }
    
    
    /*
    wires[a][b]
    a 
    0 = LEFT
    1 = RIGHT
    2 = UP
    3 = DOWN
    
    b
    0 = direction (will return a)
    1 = item (will return item stored in a)
    */
    
     public static void add(int x, int y, String direction, String item){ //add things to wires
        for (int i = 0; i < 4; i++){
            if (points[x][y].wires[i][0].equals(direction)){ //wires[i][0] iterates through directions of point 
                
                points[x][y].wires[i][1] = item;
                
                switch(points[x][y].wires[i][0]){
                    case "LEFT":
                        points[x-1][y].wires[1][1] = points[x][y].wires[i][1];
                        
                        break;
                    case "RIGHT":
                        points[x+1][y].wires[0][1] = points[x][y].wires[i][1];
                        
                        break;
                    case "UP":
                        points[x][y-1].wires[3][1] = points[x][y].wires[i][1];
                       
                        break;
                    case "DOWN":
                        points[x][y+1].wires[2][1] = points[x][y].wires[i][1];
                        
                        break;
                }
                
                return;
            }
        }
        printTest(x,y);
    }
    
    //prints items, for testing
    public static void printTest(int x,int y){
        System.out.println("(" + x+", "+y +"): DOWN= "+ points[x][y].wires[3][1]+" UP= "+points[x][y].wires[2][1] + " LEFT= "+points[x][y].wires[0][1]+" RIGHT= "+points[x][y].wires[1][1]);
        
    }
    
    //write countWires
    public static int countWires(int x, int y){
    int wireNum = 0;
                for (int i = 0; i < 4; i++){
             if (!(points[x][y].wires[i][1].equals("empty")) /*&& !(points[x][y].wires[i][1].equals("battery"))*/){
                wireNum++;
                }
                
            }
        return wireNum;
    }
    public static int loopNum = 0;
    
    public static double seriesLoop(int startingX, int startingY, int x, int y, String prevDir){
        int newX = x;
        int newY = y;
        
        double resistance = 0;
        int dirLoc = points[x][y].dirToDirLoc(prevDir); //sets resistance to the resistance stored next to prevDir
        System.out.println("previous "+prevDir + dirLoc);
        if (!(points[x][y].wires[dirLoc][1].equals("empty")) && !(points[x][y].wires[dirLoc][1].equals("battery")) && !(points[x][y].wires[dirLoc][1].equals("wire")) && !firstLoop){
            resistance = Double.parseDouble(points[x][y].wires[dirLoc][1]);
            
        }
        
        //remove countwires >2, and replace that with a different if statement that runs parallel if countwires >2
        if ((x==startingX && y==startingY && firstLoop == false)||countWires(x,y) > 2){
            return resistance;
        }//WORKS UP TO HERE PROBABLY
        
       // try{
        
        String newDir = "looloolalalooloolala"; //this will never print hopefully
        //checks if 1: direction is not prevID's direction and 2: there is not nothing stored
        for (int i = 0; i < 4; i++){
            System.out.println("i: "+i +" dirLoc: "+dirLoc+" points: "+points[x][y].wires[i][1]);
            if ((i != dirLoc )&&(!(points[x][y].wires[i][1].equals("empty")))){
                newDir = points[x][y].wires[i][0];
                System.out.println("new "+newDir);
                
                switch(newDir){
                    case "LEFT":
                        newX--;
                        newDir = "RIGHT";
                        break;
                    case "RIGHT":
                        newX++;
                        newDir = "LEFT";
                        break;
                    case "UP":
                        newY--;
                        newDir = "DOWN";
                        break;
                    case "DOWN":
                        newY++;
                        newDir = "UP";
                        break;
                }
                
            }
        }
        
        firstLoop = false;
        System.out.println(x);
        System.out.println(newX);
        System.out.println(y);
        System.out.println(newY);
        loopNum++;
        //supposed to check if id is same (LOL im not doing that)
        return resistance + seriesLoop(startingX,startingY,newX,newY,newDir);
    //    }
    //    catch(IndexOutOfBoundsException e){
    //        add(newX,newY,prevDir,"empty");
   //         return BouncingBallsDemo.loop();
   //     }
        
        
    }
    
    public static void parallelLoop(int startX, int startY, String prevDir){
        //check if endpoints for two directions found that arent prevDir is the same
       String dir1 = "";
       String dir2 = "";
       
        for (int i = 0; i < 4; i++){
                String a = points[startX][startY].wires[i][0];
                if (!a.equals(prevDir) && !a.equals("empty")){
                    dir1 = a;
                    break;
                }
            }
            
         for (int i = 0; i < 4; i++){
                String a = points[startX][startY].wires[i][0];
                if (!a.equals(prevDir) && !a.equals("empty") && !a.equals(dir1)){
                    dir2 = a;
                    break;
                }
            }
        //if they are the same, gets the resistance of both individual loops (setting startingX to the endpoint X)
        double resistance = 0;
        if (findEndPoints(startX,startY,dir1).equals(findEndPoints(startX,startY,dir1))){
            int finalX = findEndPoints(startX,startY,dir1)[0];
            int finalY = findEndPoints(startX,startY,dir1)[0];
            
            seriesLoop(finalX,finalY,startX,startY,"LEFT");
        }
        
        //sets one loop to delete, and one to change
        //makes a resistor that stores the internal resistance in the first wire of change
            //makes all resistors after that in change into wires
        //deletes everything in delete
        //runs loop from the beginning
        
         
    }
    
    
    
    
    
    
    
    public static int[] findEndPoints(int startX, int startY, String newDir){
        int x = startX;
        int y = startY;
        boolean firstLoop = true;
        String prevDir = "";
        while (countWires(x,y) ==2 || firstLoop){
            
            switch(newDir){
                    case "LEFT":
                        x--;
                        prevDir = "RIGHT";
                        break;
                    case "RIGHT":
                        x++;
                        prevDir = "LEFT";
                        break;
                    case "UP":
                        y++;
                        prevDir = "DOWN";
                        break;
                    case "DOWN":
                        y--;
                        prevDir = "UP";
                        break;
                }
            //    
            for (int i = 0; i < 4; i++){
                String a = points[x][y].wires[i][0];
                if (!a.equals(prevDir) && !a.equals("empty")){
                    newDir = a;
                }
            }
            firstLoop = false;
        }
        
        return new int[]{x,y};
    
}


}
