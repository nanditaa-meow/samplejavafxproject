public class CircuitBoard {
    public static Point[][] points;
    
    private static boolean firstLoop;
    
    
    public CircuitBoard(){
        points = new Point[4][4]; // creates the 4x4 board
        
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
                        points[x][y-1].wires[1][1] = points[x][y].wires[i][1];
                        
                        break;
                    case "RIGHT":
                        points[x][y+1].wires[0][1] = points[x][y].wires[i][1];
                        
                        break;
                    case "UP":
                        points[x-1][y].wires[3][1] = points[x][y].wires[i][1];
                       
                        break;
                    case "DOWN":
                        points[x+1][y].wires[2][1] = points[x][y].wires[i][1];
                        
                        break;
                }
                
                return;
            }
        }
        
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
    
    
    public static double seriesLoop(int startingX, int startingY, int x, int y, String prevDir){
        int newX = x;
        int newY = y;
        
        double resistance = 0;
        int dirLoc = points[x][y].dirToDirLoc(prevDir); //sets resistance to the resistance stored next to prevDir
        System.out.println("previous "+prevDir);
        if (!(points[x][y].wires[dirLoc][1].equals("empty")) && !(points[x][y].wires[dirLoc][1].equals("battery")) && !(points[x][y].wires[dirLoc][1].equals("wire"))){
            resistance = Double.parseDouble(points[x][y].wires[dirLoc][1]);
            
        }
        
        
        if ((newX==startingX && newY==startingY && firstLoop == false)||countWires(x,y) > 2){
            return resistance;
        }//WORKS UP TO HERE PROBABLY
        
        String newDir = "looloolalalooloolala"; //this will never print hopefully
        //checks if 1: direction is not prevID's direction and 2: there is not nothing stored
        for (int i = 0; i < 4; i++){
            if ((i != dirLoc )&&(!(points[x][y].wires[i][1].equals("empty")))){
                newDir = points[x][y].wires[i][0];
                System.out.println("new "+newDir);
                
                switch(newDir){
                    case "LEFT":
                        newX--;
                        break;
                    case "RIGHT":
                        newX++;
                        break;
                    case "UP":
                        newY++;
                        break;
                    case "DOWN":
                        newY--;
                        break;
                }
                
            }
        }
        
        firstLoop = false;
        System.out.println(x);
        System.out.println(newX);
        System.out.println(y);
        System.out.println(newY);
        //supposed to check if id is same (LOL im not doing that)
        return resistance + seriesLoop(startingX,startingY,newX,newY,newDir);
        
        
    }
    
}
