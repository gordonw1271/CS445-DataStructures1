import java.util.*;
import java.util.Random;

public class Assig4
{
  public static Random R = new Random();

  private ArrayList<Sorter<Integer>> sorts;
  private Integer [] A;

  private long start;
  private long stop;
  private double s;

  // best and worst alg
  private double bAvg, wAvg;
  private int bMin, wMin;
  private String bAlg, wAlg;
  // simple pivot
  private double sBAvg, sWAvg;
  private int sBMin, sWMin;
  // median of three
  private double tBAvg, tWAvg;
  private int tBMin, tWMin;
  // random pivot
  private double rBAvg, rWAvg;
  private int rBMin,rWMin;
  // merge sort
  private double mBAvg, mWAvg;
  private int mBMin,mWMin;
  // median of five
  private double fBAvg, fWAvg;
  private int fBMin,fWMin;

  
  public Assig4(String sizee, String runss)
  {
    // convert srting inputs to ints
    int size = Integer.parseInt(sizee);
    int runs = Integer.parseInt(runss);

    // initialize array 
    A = new Integer[size];

    // add sorting algs to array sorts
    sorts = new ArrayList<Sorter<Integer>>();
		sorts.add(new QuickSort<Integer>(new SimplePivot<Integer>()));
		sorts.add(new QuickSort<Integer>(new MedOfThree<Integer>()));
		sorts.add(new QuickSort<Integer>(new RandomPivot<Integer>()));
		sorts.add(new MergeSort<Integer>());
    sorts.add(new QuickSort<Integer>(new MedOfFive<Integer>()));

    // set all average times and min recurses to extreme values so we 
    // can compare them to the actual values later
    bAvg = 100;
    wAvg = 0;
    bMin = 200;
    wMin = 0;
    //
    sBAvg = 100;
    sWAvg = 0;
    sBMin = 200;
    sWMin = 0;
    //
    tBAvg = 100;
    tWAvg = 0;
    tBMin = 200;
    tWMin = 0;
    //
    rBAvg = 100;
    rWAvg = 0;
    rBMin = 200;
    rWMin = 0;
    
    mBAvg = 100;
    mWAvg = 0;
    mBMin = 200;
    mWMin = 0;

    fBAvg = 100;
    fWAvg = 0;
    fBMin = 200;
    fWMin = 0;

    // first for loop loops through each sorting algorithm
    for (int i = 0; i < sorts.size(); i++)
    {
      // double min size each run up to 160
      for(int m = 5; m <= 160; m*=2)
      {
        // third for loop for number or runs to perform
        for(int x = 0; x < runs; x++)
        {
          //set seed and add random values to array A
          R.setSeed(123456);
          for(int z = 0; z < A.length; z++)
          {
            A[z] = Integer.valueOf(R.nextInt(1000000000) + 1);
          }

          start = System.nanoTime();

          sorts.get(i).setMin(m);
          sorts.get(i).sort(A, A.length);

          stop = System.nanoTime();

          double time = stop - start;
          s = time / 1000000000;
        }
        double a = s/runs;

        // using the i as an idex, compare each algs average time and min recurse
        // to the current times and min recurse. If the values are better or worse
        // replace the current worst averages, best averages, and min recurse
        if(i == 0 && a > sWAvg)
        {
          sWAvg = a;
          sWMin = m;
        }else if(i == 0 && a < sBAvg)
        {
          sBAvg = a;
          sBMin = m;
        }else if(i == 1 && a > tWAvg)
        {
          tWAvg = a;
          tWMin = m;
        }else if(i == 1 && a < tBAvg)
        {
          tBAvg = a;
          tBMin = m;
        }else if(i == 2 && a > rWAvg)
        {
          rWAvg = a;
          rWMin = m;
        }else if(i == 2 && a < rBAvg)
        {
          rBAvg = a;
          rBMin = m;
        }else if(i == 3 && a > mWAvg)
        {
          mWAvg = a;
          mWMin = m;
        }else if(i == 3 && a < mBAvg)
        {
          mBAvg = a;
          mBMin = m;
        }else if(i == 4 && a > fWAvg)
        {
          fWAvg = a;
          fWMin = m;
        }else if(i == 4 && a < fBAvg)
        {
          fBAvg = a;
          fBMin = m;
        }
        // if average time is worse or better than worst time or best time
        // set wAlg and bAlg to the name of the worst or best algorithm
        if(a < bAvg)
        {
          bAvg = a;
          bMin = m;
          if(i == 0)
          {
            bAlg = "Simple Pivot QuickSort";
          }else if(i == 1)
          {
            bAlg = "Median of Three QuickSort";
          }else if(i == 2)
          {
            bAlg = "Random Pivot QuickSort";
          }else if(i == 3)
          {
            bAlg = "MergeSort";
          }else if(i == 4)
          {
            bAlg = "Median of Five QuickSort";
          }
        }else if(a > wAvg)
        {
          wAvg = a;
          wMin = m;
          if(i == 0)
          {
            wAlg = "Simple Pivot QuickSort";
          }else if(i == 1)
          {
            wAlg = "Median of Three QuickSort";
          }else if(i == 2)
          {
            wAlg = "Random Pivot QuickSort";
          }else if(i == 3)
          {
            wAlg = "MergeSort";
          }else if(i == 4)
          {
            wAlg = "Median of Five QuickSort";
          }
        }
      }
    }

    // pritn results
    System.out.println("Initialization information:");
    System.out.println("\tArray Size: " + size);
    System.out.println("\tNumber of Runs per Test: " + runs + "\n");

    System.out.println("After the tests, here is the best setup: ");
    System.out.println("\tAlgorithm: " + bAlg);
    System.out.println("\tMinimum Recurse: " + bMin);
    System.out.println("\tAverage: " + bAvg + " sec\n");

    System.out.println("After the tests, here is the worst setup: ");
    System.out.println("\tAlgorithm: " + wAlg);
    System.out.println("\tMinimum Recurse: " + wMin);
    System.out.println("\tAverage: " + wAvg + " sec\n");

    System.out.println("Here are the per algorithm results:");
    System.out.println("Algorithm: Simple Pivot QuickSort");
    System.out.println("\tBest Result:");
    System.out.println("\t\tMinimum Recurse: " + sBMin);
    System.out.println("\t\tAverage: " + sBAvg + " sec");
    System.out.println("\tWorst Result: ");
    System.out.println("\t\tMinimum Recurse: " + sWMin);
    System.out.println("\t\tAverage: " + sWAvg + " sec\n");

    System.out.println("Algorithm: Median of Three QuickSort");
    System.out.println("\tBest Result:");
    System.out.println("\t\tMinimum Recurse: " + tBMin);
    System.out.println("\t\tAverage: " + tBAvg + " sec");
    System.out.println("\tWorst Result: ");
    System.out.println("\t\tMinimum Recurse: " + tWMin);
    System.out.println("\t\tAverage: " + tWAvg + " sec\n");

    System.out.println("Algorithm: Random Pivot QuickSort");
    System.out.println("\tBest Result:");
    System.out.println("\t\tMinimum Recurse: " + rBMin);
    System.out.println("\t\tAverage: " + rBAvg + " sec");
    System.out.println("\tWorst Result: ");
    System.out.println("\t\tMinimum Recurse: " + rWMin);
    System.out.println("\t\tAverage: " + rWAvg + " sec\n");

    System.out.println("Algorithm: Median of Five QuickSort");
    System.out.println("\tBest Result:");
    System.out.println("\t\tMinimum Recurse: " + fBMin);
    System.out.println("\t\tAverage: " + fBAvg + " sec");
    System.out.println("\tWorst Result: ");
    System.out.println("\t\tMinimum Recurse: " + fWMin);
    System.out.println("\t\tAverage: " + fWAvg + " sec\n");

    System.out.println("Algorithm: MergeSort");
    System.out.println("\tBest Result:");
    System.out.println("\t\tMinimum Recurse: " + mBMin);
    System.out.println("\t\tAverage: " + mBAvg + " sec");
    System.out.println("\tWorst Result: ");
    System.out.println("\t\tMinimum Recurse: " + mWMin);
    System.out.println("\t\tAverage: " + mWAvg + " sec\n");
  }

  public static void main(String [] args)
	{
		new Assig4(args[0], args[1]);
	}
}
