package design_mode.ShareMeta;

/**
 * Created by lyen on 16-9-14.
 */
public class FinancialReportManager implements IReporterManager {

    private String tenantId = null;

    public FinancialReportManager(String tenantId) {
        this.tenantId = tenantId;
    }

    public String createReporter() {
        return "reporting to financialer";
    }
}
