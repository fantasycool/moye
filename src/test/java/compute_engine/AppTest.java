package compute_engine;

import com.jkys.moye.MoyeComputeEngine;
import com.jkys.moye.MoyeComputeEngineImpl;
import com.jkys.moye.MoyeParser;
import com.jkys.moye.Word;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.HashMap;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        MoyeComputeEngineImpl moyeComputeEngine = new MoyeComputeEngineImpl();
        System.out.println(moyeComputeEngine.execute("(&& (in 123 [456]) 1)", new HashMap<String, Object>()));
    }
}
