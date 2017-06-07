import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Connor on 6/2/2017.
 */
public class Assignment3 {
    private static double [] xVals;
    private static double [] yVals;
    private static double [][] functionOf;
    private static String TAB = "\t\t";

    public static void main(String[] args) {
        readFile(args[0]);
        functionOf = new double[xVals.length - 1][];
        for(int i = 0; i < functionOf.length; ++i) {
            functionOf[i] = new double[functionOf.length - i];
        }
        printTable();
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
        System.out.print("x");
        String commas = "";
        for(int i = 0; i < xVals.length; ++i) {
            System.out.print(TAB + "f[" + commas + "]");
            commas = commas + ",";
        }
        System.out.println();

        for(int i = 0; i < xVals.length; ++i) {
            System.out.println(xVals[i] + TAB + yVals[i]);
        }
    }

    public static void printInterpolatingPolynomial() {
        // TODO
    }

    public static void printSimplifiedPolynomial() {
        // TODO
    }
}
