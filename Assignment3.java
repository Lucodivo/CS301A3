import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Connor on 6/2/2017.
 */
public class Assignment3 {
    private static double [] xVals;
    private static double [] yVals;
    private static double [][] functionOf;
    private static String TAB = "\t\t\t";
    private static NumberFormat formatter = new DecimalFormat("#0.0000");

    public static void main(String[] args) {
        readFile(args[0]);
        functionOf = new double[xVals.length - 1][];
        for(int i = 0; i < functionOf.length; ++i) {
            functionOf[i] = new double[functionOf.length - i];
        }
        dividedDifference();
        printTable();
        printInterpolatingPolynomial();
        printSimplifiedPolynomial();
    }

    private static void dividedDifference() {
        for(int i = 1; i < yVals.length; ++i) {
            functionOf[0][i - 1] = yVals[i - 1] - yVals[i];
            functionOf[0][i - 1] /= (xVals[i - 1] - xVals[i]);
        }
        for(int i = 1; i < functionOf.length; ++i) {
            for(int j = 1; j < functionOf[i - 1].length; ++j) {
                functionOf[i][j - 1] = functionOf[i - 1][j - 1] - functionOf[i - 1][j];
                // divide by xvals
                functionOf[i][j - 1] /= (xVals[j - 1] - xVals[j + i]);
            }
        }
    }

    private static void readFile(String arg) {
        // File reading code pulled from following source
        // https://www.mkyong.com/java/how-to-read-file-from-java-bufferedreader-example/

        BufferedReader br = null;
        FileReader fr = null;

        try {

            fr = new FileReader(arg);
            br = new BufferedReader(fr);

            String sCurrentLine;

            br = new BufferedReader(new FileReader(arg));

            // acquire x-values
            sCurrentLine = br.readLine();
            String xValArray[] = sCurrentLine.split(" ");
            xVals = new double[xValArray.length];
            for(int i = 0; i < xValArray.length; ++i) {
                xVals[i] = Double.parseDouble(xValArray[i]);
            }

            // acquire y-values
            sCurrentLine = br.readLine();
            String yValArray[] = sCurrentLine.split(" ");
            yVals = new double[yValArray.length];
            for(int i = 0; i < yValArray.length; ++i) {
                yVals[i] = Double.parseDouble(yValArray[i]);
            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }
    }

    public static void printTable() {
        System.out.print("x\t");
        int commas = 0;
        for(int i = 0; i < xVals.length; ++i) {
            System.out.print(TAB + "f[" + commas++ + "]");
        }
        System.out.println();

        for(int i = 0; i < xVals.length; ++i) {
            System.out.print(formatter.format(xVals[i]) + TAB + formatter.format(yVals[i]));
            for(int j = 0; j < i; ++j) {
                System.out.print(TAB + formatter.format(functionOf[j][i - j - 1]));
            }
            System.out.println();
        }
    }

    public static void printInterpolatingPolynomial() {
        System.out.println();
        System.out.println("Interpolating polynomial is: \n");
        String xString = "";
        System.out.print(formatter.format(yVals[0]));
        for(int i = 0; i < functionOf.length; ++i) {
            double coefficient = functionOf[i][0];
            if(coefficient < 0) {
                System.out.print(" - " + formatter.format(Math.abs(coefficient)));
            } else {
                System.out.print(" + " + formatter.format(coefficient));
            }
            if(xVals[i] < 0) {
                xString += "(x+" + formatter.format(Math.abs(xVals[i])) + ")";
            } else  if(xVals[i] > 0) {
                xString += "(x-" + formatter.format(Math.abs(xVals[i])) + ")";
            } else {
                xString += "x";
            }
            System.out.print(xString);
        }
        System.out.println();
    }

    public static void printSimplifiedPolynomial() {
        System.out.println();
        System.out.println("Simplified polynomial is: \n");
        double [] finalVal = new double[functionOf.length + 1];
        double [] interVal = new double[functionOf.length + 1];
        finalVal[0] = yVals[0];
        interVal[0] = 1;
        for(int i = 0; i < functionOf.length; ++i) {
            double [] higherOrderVal = new double[interVal.length];
            // acquire all values that will be a result of previous values multiplied by x
            for(int j = interVal.length - 1; j > 0; --j) {
                higherOrderVal[j] = interVal[j - 1];
            }
            // multiply by the coefficient in (x - c) and add the higher orders from previous loop
            for(int j = 0; j < interVal.length; ++j) {
                interVal[j] *= (-1 * xVals[i]);
                interVal[j] += higherOrderVal[j];
            }
            // add intermediate values to final result
            for(int j = 0; j < interVal.length; ++j) {
                finalVal[j] += functionOf[i][0] * interVal[j];
            }
        }
        for(int i = finalVal.length - 1; i > 0; --i) {
            if(finalVal[i - 1] > 0) {
                System.out.print(formatter.format(Math.abs(finalVal[i])) + "x^" + i + " + ");
            } else if(finalVal[i - 1] < 0) {
                System.out.print(formatter.format(Math.abs(finalVal[i])) + "x^" + i + " - ");
            }
        }
        System.out.println(formatter.format(Math.abs(finalVal[0])));
    }
}
