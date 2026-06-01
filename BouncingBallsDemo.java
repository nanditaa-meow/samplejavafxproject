import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.shape.Line;
import javafx.scene.control.TextField;


public class BouncingBallsDemo extends Application {
 CircuitBoard board = new CircuitBoard();
   public boolean clicked = false;
    public    int oldX =0;
    public    int oldY = 0;
    
public boolean resistorAdd = false;
public boolean batteryAdd = false;
    

    public static void main(String[] args) {
        
       
        
        launch(args);
    }

    @Override
    public void start(Stage stage) {
         /*CircuitBoard.add(1,1,"RIGHT","battery");
        CircuitBoard.add(2,2,"DOWN","1");
        CircuitBoard.add(3,2,"DOWN","1");
         CircuitBoard.add(2,1,"DOWN","wire");
          CircuitBoard.add(2,2,"RIGHT","wire");
          CircuitBoard.add(2,3,"RIGHT","wire");
          CircuitBoard.add(2,3,"DOWN","wire");
          CircuitBoard.add(2,4,"LEFT","wire");
          CircuitBoard.add(1,4,"UP","wire");
          CircuitBoard.add(1,3,"UP","wire");
           CircuitBoard.add(1,2,"UP","wire");*/
           
        Pane root = new Pane();

        
        Button b1 = new Button("Get Resistance");
        b1.setLayoutX(220);
        b1.setLayoutY(350);
        b1.setPrefSize(130, 20);
        
        b1.setOnMouseClicked(event -> {
            System.out.println(loop2());
        });

        Button b2 = new Button("Add Resistor");
        b2.setLayoutX(80);
        b2.setLayoutY(335);
        b2.setPrefSize(120, 20);
        
        b2.setOnMouseClicked(event -> {
                    resistorAdd = true;
                    batteryAdd = false;
                    });
                    
        
        TextField t1 = new TextField("   ");
        t1.setLayoutX(20);
        t1.setLayoutY(335);
        t1.setPrefSize(45, 20);
        
        TextField t2 = new TextField("   ");
        t2.setLayoutX(20);
        t2.setLayoutY(365);
        t2.setPrefSize(45, 20);
        
        Button b3 = new Button("Add Battery");
        b3.setLayoutX(80);
        b3.setLayoutY(365);
        b3.setPrefSize(120, 20);
        
         b3.setOnMouseClicked(event -> {
                    batteryAdd = true;
                    resistorAdd = false;
                    });
        
      
        root.getChildren().addAll(b1,b2,b3,t1,t2);
        
        Circle[][] points = new Circle[10][10];
        
        Circle[][] hitbox = new Circle[10][10];
        
        
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j ++){
                hitbox[i][j] = new Circle((i*30)+50,(j*30)+50,12);
                hitbox[i][j].setFill(Color.WHITE);
                points[i][j] = new Circle((i*30)+50,(j*30)+50,2);
                root.getChildren().add(hitbox[i][j]);
                root.getChildren().add(points[i][j]);
                int x = i;
                int y = j;
                hitbox[i][j].setOnMouseClicked(event -> {
                    
                   
                    
                    if (!clicked){
                        oldX = (x*30)+50;
                        oldY = (y*30)+50;
                        clicked = true;
                    }
                    else if (batteryAdd){
                        int newX = (x*30)+50;
                        int newY = (y*30)+50;
                        Line wire = new Line(oldX, oldY,newX, newY);
                    String dir = direction(x,y,(oldX-50)/30,(oldY-50)/30);
                    CircuitBoard.add(x,y,dir ,"battery");
                     System.out.println("battery added 1");
                     CircuitBoard.printTest(x,y);
                        wire.setStroke(Color.RED);
                         root.getChildren().add(wire);
                        System.out.println("battery added");
                        clicked = false;
                        batteryAdd = false;
                    }
                     else if (resistorAdd && !t1.getCharacters().isEmpty()){
                         
                        int newX = (x*30)+50;
                        int newY = (y*30)+50;
                        Line wire = new Line(oldX, oldY,newX, newY);
                         wire.setStroke(Color.BLUE);
                         String dir = direction(x,y,(oldX-50)/30,(oldY-50)/30);
                    CircuitBoard.add(x,y,dir ,t1.getCharacters().toString());
                        System.out.println("resistor added");
                        System.out.println("resistance: " + t1.getCharacters().toString());
                        root.getChildren().add(wire);
                        clicked = false;
                        resistorAdd = false;
                    }
                    else{
                        int newX = (x*30)+50;
                        int newY = (y*30)+50;
                        Line wire = new Line(oldX, oldY,newX, newY);
                        String dir = direction(x,y,(oldX-50)/30,(oldY-50)/30);
                    CircuitBoard.add(x,y,dir ,"wire");
                        root.getChildren().add(wire);
                        clicked = false;
                    }
                    //ai usage: debugged error: local vaiables referenced from a lambda expression must be final
                    // change made: set variables to be class variables rather than local ones
        });
            }
            
        }
       

        
     
        
        Scene scene = new Scene(root, 400, 400,Color.WHITE);
        stage.setScene(scene);
        stage.show();
    }

public static CharSequence blankField(){
    TextField t = new TextField("   ");
    return t.getCharacters();
}

public static String direction(int x1,int y1,int x2,int y2){
    if ((x2-x1) > 0){
        return  "RIGHT";
    }
     if ((x2-x1) < 0){
        return  "LEFT";
    }
    if ((y2-y1) > 0){
        return  "DOWN";
    }
    if ((y2-y1) < 0){
        return  "UP";
    }
    return "error";
}
  
     public static double loop(){
        int x = CircuitBoard.findBattery()[0];
         int y = CircuitBoard.findBattery()[1];
         String batteryDir = CircuitBoard.points[x][y].find("battery");
         String dir = "meow";
         for (int i = 0; i < 4; i++){
             if (!CircuitBoard.points[x][y].wires[i][0].equals(batteryDir) && !CircuitBoard.points[x][y].wires[i][1].equals("empty")){
                 dir = CircuitBoard.points[x][y].wires[i][0];
             }
         }
         
         
         return CircuitBoard.seriesLoop(x,y,x,y,dir);
    }
    
    
         public static double loop2(){
        int x = CircuitBoard.findBattery()[0];
         int y = CircuitBoard.findBattery()[1];
         String batteryDir = CircuitBoard.points[x][y].find("battery");
         
         
         return CircuitBoard.seriesLoop2(batteryDir,x,y,x,y);
    }
   
  
}
