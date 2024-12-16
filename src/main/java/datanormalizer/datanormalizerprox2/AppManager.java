package datanormalizer.datanormalizerprox2;

import java.io.File;

public class AppManager {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
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
        }
    }

    public static void updateOutputPath()
    {
        OUTPUT_PATH = CURRENT_FILE.getName().replace(".csv", "-processed.csv");
    }
}
