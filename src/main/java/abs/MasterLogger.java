package abs;
import org.apache.log4j.Logger;
public class MasterLogger {
    private static Logger log = null;

    private MasterLogger() {

    }

    public static Logger getInstance() {
        if (log == null) {
            log = Logger.getLogger(MasterLogger.class);
        }
        return log;
    }

}
