import javax.swing.*;   //importing all required documents 
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

abstract class Employee {    //Abstract class of Employee
    protected double grossIncome;
    protected double taxRate;

    //constructor for initialization of gross income and tax rate
    public Employee(double grossIncome, double taxRate) {
        this.grossIncome = grossIncome;
        this.taxRate = taxRate;
    }
    //Abstarct method to claculate the pay 
    abstract double calculatePay();
}

// Full time employee inheritance of employee class
class FullTimeEmployee extends Employee {
    private double healthDed;
    private double k401Ded;

    public FullTimeEmployee(double grossIncome, double taxRate, double healthDed, double k401Ded) {
        super(grossIncome, taxRate);
        this.healthDed = healthDed;
        this.k401Ded = k401Ded;
    }
    //method to claculate the pay of fulltime employee
    double calculatePay() {
        return (grossIncome - healthDed - k401Ded) * (1 - taxRate);
    }

}
//class for parttime employees
class PartTimeEmployee extends Employee {
    private double hourlyRate;
    private double hoursWorked;

    public PartTimeEmployee(double hourlyRate, double hoursWorked, double taxRate) {
        super(0, taxRate);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }
    //overrirded method to claculate the pay
    double calculatePay() {
        return hourlyRate * hoursWorked * (1 - taxRate);
    }

}

class ContractEmployee extends Employee {
    private double contractTot;
    private int contractPeriod;

    public ContractEmployee(double contractTot, int contractPeriod, double taxRate) {
        super(0, taxRate);
        this.contractPeriod = contractPeriod;
        this.contractTot = contractTot;
    }

    @Override
    double calculatePay() {
        return contractTot / (contractPeriod / 12.0);
    }
}

//class for claculating payroll averages
class PayrollAverage {
    private double sum = 0; //sum of payroll amount
    private int count = 0;  //number of employees

    public void update(Employee employee) {
        sum = sum + employee.calculatePay();
        count++;
    }
    //method to claculate the average payroll
    public double getAvg() {
        return sum / count;
    }
}

// class to claculate payroll range
class PayrollRange {
    private double min;
    private double max;

    public PayrollRange() { //constructor to assign min and max values
        min = Double.MAX_VALUE;
        max = Double.MIN_VALUE;
    }

    public void update(Employee employee) {
        double periodPay = employee.calculatePay();
        min = Math.min(min, periodPay);
        max = Math.max(max, periodPay);
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }
}
// controller class for manageing pay roll of all employees
class PayrollController {
    private List<Employee> employee; //list of all employees

    //method to display payrollinformation
    public PayrollController() {
        employee = new ArrayList<>();
        // constructor for initializing the controller with values
        //employee.add(new FullTimeEmployee(50000, 0.2, 2000, 3000));
        employee.add(new FullTimeEmployee(55000, 0.2, 2200, 3000));
        employee.add(new PartTimeEmployee(45, 80, 0.2));
        employee.add(new ContractEmployee(24000, 24, 0.2));
    }

    public void showPayroll(JTextArea outputTextArea) {
        outputTextArea.append("Payroll for all employees:\n");
        for (Employee employee : employee) {
            if (employee instanceof FullTimeEmployee) {
                outputTextArea.append("Full Time Employee Payroll: " + employee.calculatePay() + "\n");
            } else if (employee instanceof PartTimeEmployee) {
                outputTextArea.append("Part Time Employee Payroll: " + employee.calculatePay() + "\n");
            } else if (employee instanceof ContractEmployee) {
                outputTextArea.append("Contract Employee Payroll: " + employee.calculatePay() + "\n");
            }
        }
    }
    //Method to display average payroll information
    public void showPayrollAverages(JTextArea outputTextArea) {
        double fullTimeTotal = 0;
        double partTimeTotal = 0;
        double contractTotal = 0;
        int fullTimeCount = 0;
        int partTimeCount = 0;
        int contractCount = 0;

        for (Employee employee : employee) {
            if (employee instanceof FullTimeEmployee) {
                fullTimeTotal += employee.calculatePay();
                fullTimeCount++;
            } else if (employee instanceof PartTimeEmployee) {
                partTimeTotal += employee.calculatePay();
                partTimeCount++;
            } else if (employee instanceof ContractEmployee) {
                contractTotal += employee.calculatePay();
                contractCount++;
            }
        }

        double fullTimeAverage = fullTimeCount > 0 ? fullTimeTotal / fullTimeCount : 0;
        double partTimeAverage = partTimeCount > 0 ? partTimeTotal / partTimeCount : 0;
        double contractAverage = contractCount > 0 ? contractTotal / contractCount : 0;

        outputTextArea.append("Payroll Averages:\n");
        outputTextArea.append("Full Time Employee Average Payroll: " + fullTimeAverage + "\n");
        outputTextArea.append("Part Time Employee Average Payroll: " + partTimeAverage + "\n");
        outputTextArea.append("Contract Employee Average Payroll: " + contractAverage + "\n");
    }

    public void showPayrollRanges(JTextArea outputTextArea) {
        double fullTimeMin = Double.MAX_VALUE;
        double fullTimeMax = Double.MIN_VALUE;
        double partTimeMin = Double.MAX_VALUE;
        double partTimeMax = Double.MIN_VALUE;
        double contractMin = Double.MAX_VALUE;
        double contractMax = Double.MIN_VALUE;
    
        for (Employee employee : employee) {
            double periodPay = employee.calculatePay();
            if (employee instanceof FullTimeEmployee) {
                fullTimeMin = Math.min(fullTimeMin, periodPay);
                fullTimeMax = Math.max(fullTimeMax, periodPay);
            } else if (employee instanceof PartTimeEmployee) {
                partTimeMin = Math.min(partTimeMin, periodPay);
                partTimeMax = Math.max(partTimeMax, periodPay);
            } else if (employee instanceof ContractEmployee) {
                contractMin = Math.min(contractMin, periodPay);
                contractMax = Math.max(contractMax, periodPay);
            }
        }
    
        outputTextArea.append("Payroll range for full-time employees: Min = " + fullTimeMin + ", Max = " + fullTimeMax + "\n");
        outputTextArea.append("Payroll range for part-time employees: Min = " + partTimeMin + ", Max = " + partTimeMax + "\n");
        outputTextArea.append("Payroll range for contract employees: Min = " + contractMin + ", Max = " + contractMax + "\n");
    }
    
}
// main method
public class Employeeapplication {
    public static void main(String[] args) {

        JFrame jF = new JFrame("Homework2"); //to create jframe for the application 
        JPanel jP = new JPanel();//to create a JPanel for the application 

        PayrollController controller = new PayrollController(); //Object for controller

        JTextArea outputTextArea = new JTextArea(25, 45);

        JButton payrollButton = new JButton("Payroll"); //payroll button
        payrollButton.addActionListener(e -> {
            outputTextArea.setText("");
            controller.showPayroll(outputTextArea);
        });

        JButton avgButton = new JButton("Average"); // Average button
        avgButton.addActionListener(e -> {
            outputTextArea.setText("");
            controller.showPayrollAverages(outputTextArea);
        });

        JButton rangeButton = new JButton("Range"); //Range button
        rangeButton.addActionListener(e -> {  //actionlistiner for rangebutton
            outputTextArea.setText("");
            controller.showPayrollRanges(outputTextArea);
        });

        jP.add(payrollButton);
        jP.add(avgButton);
        jP.add(rangeButton);
        jP.add(outputTextArea);
        jP.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        jF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Default close operation for JFrame
        jF.setLayout(new BorderLayout());
        jF.add(jP, BorderLayout.CENTER);
        jF.setSize(500, 500);
        jF.setLocationRelativeTo(null);
        jF.setVisible(true); //to set jframe to be visible
    }
}
