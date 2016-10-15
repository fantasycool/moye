package compute_engine;

import com.jkys.moye.MoyeComputeEngineImpl;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.HashMap;
import java.util.Map;

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
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("key1", 10);
        System.out.println(moyeComputeEngine.execute("(+ key1 1)", m));
    }
}
