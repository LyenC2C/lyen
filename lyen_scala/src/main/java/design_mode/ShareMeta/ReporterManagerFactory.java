package design_mode.ShareMeta;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyen on 16-9-14.
 */
public class ReporterManagerFactory {

    Map<String, IReporterManager> financialReportManager = new HashMap<String, IReporterManager>();
    Map<String, IReporterManager> employeeReportManager = new HashMap<String, IReporterManager>();

    public IReporterManager getFinancialReportManager(String tenentId) {
        IReporterManager r = financialReportManager.get(tenentId);//通过租户id获取享元
        if (r == null) {
            r = new FinancialReportManager(tenentId);
            financialReportManager.put(tenentId,r);//维护已创建的享元对象
        }
        return r;
    }

    public IReporterManager getEmployeeReportManager(String tenentId) {

        IReporterManager r = employeeReportManager.get(tenentId);//通过租户id获取享元
        if(r == null){
            r = new EmployeeReportManager(tenentId);
            employeeReportManager.put(tenentId,r);//维护已创建的享元对象
        }
        return r ;
    }

    public static void main(String[] args) {
        ReporterManagerFactory re = new ReporterManagerFactory();
        IReporterManager rme = re.getEmployeeReportManager("A");
        IReporterManager rmf = re.getFinancialReportManager("B");
        System.out.println(rme.createReporter());
        System.out.println(rmf.createReporter());

    }

}
