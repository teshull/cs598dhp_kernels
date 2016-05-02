package kernel_two;

import java.util.*;

public class Main {
    //time stuff
    private static int executionTime = 10;
    private static int permanentListTime = 10;

    //list stuff
    private static int listSize = 30;

    //percentages for storing values
    private static double moveToOldThreshold = 98;
    private static double deleteFromListThreshold = 50;

    //flag for deleting permanent list
    private static boolean deletePermanent = false;

    private static Random rn = new Random();
    private static boolean timerFinished = false;

    //for stats
    private static double numKept = 0;
    private static double numDeleted = 0;
    private static double numMovedToOld = 0;

    public static void main(String[] args) {
        // write your code here
        readArgs(args);
        LinkedList<Item> linkedList = new LinkedList<Item>();
        LinkedList<Item> permanentList = new LinkedList<Item>();
        double sum;

        final Timer endTime = new Timer();
        endTime.schedule(new TimerTask() {
            @Override
            public void run() {
                timerFinished = true;
                System.out.println("Timer is finished");
                System.out.println("Number kept: " + numKept);
                System.out.println("Number moved to old: " + numMovedToOld);
                System.out.println("Number deleted: " + numDeleted);
                System.exit(0);
            }
        }, executionTime * 1000);

        final Timer permanentListTimer = new Timer();
        permanentListTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                deletePermanent = true;
                System.out.println("deleting permanent list");
            }
        }, new Date(), permanentListTime * 1000);

        //doing this until the timer goes off
        System.out.println("Starting Kernel Two");
        while(true){
            if(deletePermanent){
                deletePermanent = false;
                permanentList.clear();
            }
            initializeList(linkedList);
            sum = calculateSum(linkedList);
            cleanupList(linkedList, permanentList);
        }
    }

    private static void readArgs(String[] args){
        for (int i = 0; i < args.length; i+=2) {
            String key = args[i];
            double value = 0;
            try {
                value = Double.parseDouble(args[i+1]);
            }catch(NumberFormatException e){
                System.err.println("Argument " + args[i+1] + "must be a double");
            }
            switch (key) {
                case "--list":
                    listSize = (int) value;
                    break;
                case "--oldThreshold":
                    moveToOldThreshold = value;
                    break;
                case "--deleteThreshold":
                    deleteFromListThreshold = value;
                    break;
                case "--permanentListTime":
                    permanentListTime = (int) value;
                    break;
                case "--time":
                    executionTime = (int) value;
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
            double newNum = rn.nextDouble() * 100;
            list.add(new Item(newNum));
        }
    }

    private static double calculateSum(List<Item> list){
        double sum = 0;
        for(Item item: list){
            sum += item.getValue();
        }
        return sum;
    }

    private static void cleanupList(List<Item> list, List<Item> oldList){
        int index = 0;
        while(index < list.size()){
            double value = list.get(index).getValue();
            double moveToOldList = rn.nextDouble()*100;
            double deleteFromList = rn.nextDouble()*100;
            if(moveToOldList > moveToOldThreshold){
                //moving some to semi-permanent storage
                oldList.add(new Item(value));
                list.remove(index);
                numMovedToOld++;
                numDeleted++;
            } else if(deleteFromList  > deleteFromListThreshold){
                //removing some from the list
                list.remove(index);
                numDeleted++;
            }else {
                //not removing anything, but moving onto next item
                index++;
                numKept++;
            }
        }
    }
}

