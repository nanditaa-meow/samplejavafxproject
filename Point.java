public class Point {
    public double current;
    public double voltage;
    
    public String[][] wires;
    
    public Point(){
        wires = new String[][]{{"LEFT","empty"},
                {"RIGHT","empty"},
                {"UP","empty"},
                {"DOWN","empty"}};
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
    
    public String find(String item){ //finds things based on name of item (not resisitor), returns direction
        for (int i = 0; i < 4; i++){
            if (wires[i][1].equals(item)){
                return wires[i][0];
            }
        }
        
        return "item not found";
        
    }
    

        
    
      public int dirToDirLoc(String dir){ //direction to direction location
        for (int i = 0; i < 4; i++){
            if (wires[i][0].equals(dir)){
                return i;
            }
        }
        
        return 100;
        
    }
    
    
}
