package programs;

import java.util.ArrayList;
import java.util.List;

public class SawPrimePlanks {

    public static List<Integer> getPlankLengths(int longPlankLength){
        int length = (sawPlank(longPlankLength));
        int numOfPlanks = longPlankLength/length;
        List<Integer> arr = new ArrayList<>();

        for(int i = 0; i < numOfPlanks; i++){
            arr.add(length);
        }
        //System.out.println(arr.size());
        return arr;
    }

    public static int sawPlank(int plankLength){
        //base case; if plankLength is prime
        if(isPrime(plankLength)){
            return plankLength;
        }
        //recursive case
        else{
            //try dividing by prime numbers
            int prime = 2;
            while(plankLength % prime != 0){
                prime++;
                //until we reach the next prime number, keep adding to prime
                while(!isPrime(prime)) {
                    prime++;
                }
            }
            return sawPlank(plankLength/prime);
        }
    }

    public static boolean isPrime(int n){
        boolean returnVal = true;

        for(int i = 2; i <= n/2; i++){
            //nonprime number
            if(n%i == 0){
                returnVal = false;
                break;
            }
        }
        //returns true for prime num, false for composite
        return returnVal;
    }
}
