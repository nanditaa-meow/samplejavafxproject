public class MyProgram
{
    public static void main(String[] args)
    {
        CircuitBoard board = new CircuitBoard();
         
         //nothing with 0 or the last index
         CircuitBoard.add(1,1,"RIGHT","battery");
       CircuitBoard.add(1,2,"DOWN","10");
        CircuitBoard.add(2,2,"LEFT","10");
        CircuitBoard.add(2,1,"UP","wire");
         
         CircuitBoard.printTest(1,1);
         CircuitBoard.printTest(2,1);
          CircuitBoard.printTest(1,2);
          CircuitBoard.printTest(2,2);
         
          
         
         System.out.println(CircuitBoard.seriesLoop(1,1,1,1,"DOWN"));
         
    }
}
