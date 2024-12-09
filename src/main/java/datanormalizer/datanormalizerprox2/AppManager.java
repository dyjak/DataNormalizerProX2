package datanormalizer.datanormalizerprox2;

import java.io.File;

public class AppManager {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final String TITLE = "Data Normalizer Pro X2";
    public static File CURRENT_FILE;






    public static void printAppAttributes()
    {
        //System.out.println("TITLE = " + TITLE);
        //System.out.println(WIDTH + "x" + HEIGHT);
        if(CURRENT_FILE!=null)
        {
            System.out.println("CURRENT FILE = " + CURRENT_FILE.getAbsolutePath());
        }
    }
}
