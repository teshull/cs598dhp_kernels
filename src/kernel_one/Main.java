package kernel_one;

import java.math.BigInteger;
import java.util.*;

public class Main {
    private static int executionTime = 60;
    private static int listSize = 30;
    private static int randRange = 30;
    private static Random rn = new Random();
    private static boolean timerFinished = false;

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
                System.exit(0);
            }
        }, executionTime * 1000);

        //doing this until the timer goes off
        System.out.println("Starting Kernel");
        BigInteger totalSum = BigInteger.valueOf(0);
        while(true){
            initializeList(linkedList);
            sum = calculateSum(linkedList);
            totalSum = totalSum.add(BigInteger.valueOf(sum));
            linkedList.clear();
        }
    }

    private static void readArgs(String[] args){
        for (int i = 0; i < args.length; i++) {
            int value = 0;
            try {
                value = Integer.parseInt(args[i]);
            }catch(NumberFormatException e){
                System.err.println("Argument " + args[i] + "must be an integer");
            }
            switch (i) {
                case 0:
                    listSize = value;
                    break;
                case 1:
                    randRange = value;
                    break;
                case 2:
                    executionTime = value;
                    break;
                default:
                    System.err.println("too many arguments");
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
