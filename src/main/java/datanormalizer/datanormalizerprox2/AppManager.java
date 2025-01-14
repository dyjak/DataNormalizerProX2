package datanormalizer.datanormalizerprox2;

import java.io.File;

public class AppManager {
    public static final int WIDTH = 1820;
    public static final int HEIGHT = 980;
    public static final String TITLE = "Data Normalizer Pro X2";
    public static File CURRENT_FILE;
    public static String OUTPUT_PATH="";

    public static String COLUMN_EXPLODER = ",";
    public static Boolean COLUMN_HEADERS = true;






    public static void printAppAttributes()
    {
        //System.out.println("TITLE = " + TITLE);
        //System.out.println(WIDTH + "x" + HEIGHT);
        if(CURRENT_FILE!=null)
        {
            System.out.println("CURRENT FILE = " + CURRENT_FILE.getAbsolutePath());
            System.out.println("OUTPUT FILE = " + OUTPUT_PATH);
            System.out.println("PROJECT PATH: ...");
        }
    }

    public static void updateOutputPath() {
        if (CURRENT_FILE != null) {
            OUTPUT_PATH = CURRENT_FILE.getAbsolutePath().replace(".csv", "-processed.csv");
        } else {
            System.err.println("CURRENT_FILE is null. Cannot set OUTPUT_PATH.");
        }
    }
}
