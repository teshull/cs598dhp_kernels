package kernel_one;

import java.math.BigInteger;
import java.util.*;

public class Main {
    private static int executionTime = 10;
    private static int listSize = 30;
    private static int randRange = 30;
    private static Random rn = new Random();
    private static boolean timerFinished = false;

    private static BigInteger totalSum;

    public static void main(String[] args) {
        // write your code here
        readArgs(args);
        LinkedList<Item> linkedList = new LinkedList<Item>();
        int sum;

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timerFinished = true;
                System.out.println("Timer is finished");
                System.out.println("The total sum is " + totalSum.toString());
                System.exit(0);
            }
        }, executionTime * 1000);

        totalSum = BigInteger.valueOf(0);
        //doing this until the timer goes off
        System.out.println("Starting Kernel");
        while(true){
            initializeList(linkedList);
            sum = calculateSum(linkedList);
            totalSum = totalSum.add(BigInteger.valueOf(sum));
            linkedList.clear();
        }
    }

    private static void readArgs(String[] args){
        for (int i = 0; i < args.length; i+=2) {
            int value = 0;
            String key = args[i];
            try {
                value = Integer.parseInt(args[i+1]);
            }catch(NumberFormatException e){
                System.err.println("Argument " + args[i+1] + "must be an integer");
            }
            switch (key) {
                case "--list":
                    listSize = value;
                    break;
                case "--range":
                    randRange = value;
                    break;
                case "--time":
                    executionTime = value;
                    break;
                default:
                    System.err.println("unable to read argument");
                    System.exit(-1);
                    break;

            }
        }
    }

    private static void initializeList(List<Item> list){
        for(int i = 0; i < listSize; i++){
            int newNum = rn.nextInt(randRange);
            list.add(new Item(newNum));
        }
    }

    private static int calculateSum(List<Item> list){
        int sum = 0;
        for(Item item: list){
            sum += item.getValue();
        }
        return sum;
    }
}
