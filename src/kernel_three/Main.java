package kernel_three;

import java.util.*;

public class Main {
    //time stuff
    private static int executionTime = 10;
    private static int permanentListTime = 2;

    //list stuff
    private static int listSize = 30;

    //percentages for storing values
    private static double moveToOldThreshold = 95;
    private static double deleteFromListThreshold = 95;
    private static double gaussianMean = 1000;
    private static double gaussianVariance = 100;

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
        LinkedList<Items> linkedList = new LinkedList<Items>();
        LinkedList<Items> permanentList = new LinkedList<Items>();
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
        System.out.println("Starting Kernel");
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
        for (int i = 0; i < args.length; i++) {
            double value = 0;
            try {
                value = Double.parseDouble(args[i]);
            }catch(NumberFormatException e){
                System.err.println("Argument " + args[i] + "must be an integer");
            }
            switch (i) {
                case 0:
                    listSize = (int) value;
                    break;
                case 1:
                    moveToOldThreshold = value;
                    break;
                case 2:
                    deleteFromListThreshold = value;
                    break;
                case 3:
                    gaussianMean = value;
                    break;
                case 4:
                    gaussianVariance = value;
                    break;
                case 5:
                    permanentListTime = (int) value;
                    break;
                case 6:
                    executionTime = (int) value;
                    break;
                default:
                    System.err.println("too many arguments");
                    System.exit(-1);
                    break;

            }
        }
    }

    private static void initializeList(List<Items> list){
        for(int i = 0; i < listSize; i++){
            int numItems = (int) (rn.nextGaussian() * gaussianVariance + gaussianMean);
            //need a check to make sure it is above 100 Gaussian distribution extends to negative infinity
            if(numItems > 0) {
                list.add(new Items(numItems));
            }
        }
    }

    private static double calculateSum(List<Items> list){
        double sum = 0;
        for(Items item: list){
            sum += item.getSum();
        }
        return sum;
    }

    private static void cleanupList(List<Items> list, List<Items> oldList){
        int index = 0;
        while(index < list.size()){
            Items item = list.get(index);
            double moveToOldList = rn.nextDouble()*100;
            double deleteFromList = rn.nextDouble()*100;
            if(moveToOldList > moveToOldThreshold){
                //moving some to semi-permanent storage
                oldList.add(item);
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

