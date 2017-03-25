package subsmerger;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import subsmerger.SubsMergerModel;

public class SubsMergerModelTest {
    public String SRC_SRT_1 = "resources\\kubo1.srt";
    public String SRC_SRT_2 = "resources\\kubo2.srt";
    public String SRC_ASS = "resources\\out\\kubo.ass";
    
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test of fillMergedFile method, of class SubsMergerModel.
     */
    @Test
    public void testFillMergedFile() throws Exception {
        System.out.println("fillMergedFile");
        File fTop = new File(SRC_SRT_1);
        File fBot = new File(SRC_SRT_2);
        System.out.println(fTop.getAbsolutePath());
        System.out.println(fBot.getAbsolutePath());
        String font = "Arial";
        int sizeFont = 19;
        File fDest = new File(SRC_ASS);
        SubsMergerModel.fillMergedFile(fTop, fBot, font, sizeFont, fDest);
    }
    
    
}