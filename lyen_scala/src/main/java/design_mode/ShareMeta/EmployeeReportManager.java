package design_mode.ShareMeta;

/**
 * Created by lyen on 16-9-14.
 */
public class EmployeeReportManager implements IReporterManager {

    private String tenantId = null;

    public EmployeeReportManager(String tenantId) {
        this.tenantId = tenantId;
    }

    public String createReporter() {
        return "reporting to employee";
    }
}
