package edu.uoc.pac1;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class PAC1Ex3 {

    static DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("EN"));
    static DecimalFormat decimalFormat = new DecimalFormat("0.00", symbols);

    static final int FIRST_YEAR = 2012;

    static double[][] evolutionCPI = {
            //Jan'12 - Dec'12
            {-1.1,0.1,0.7,1.4,-0.1, -0.2, -0.2, 0.6, 1, 0.8, -0.1, 0.1},
            //Jan'13 - Dec'13
            {-1.3, 0.2, 0.4, 0.4, 0.2, 0.1, -0.5, 0.3, -0.2, 0.4, 0.2, 0.1},
            //Jan'14 - Dec'14
            {-1.3, 0, 0.2, 0.9, 0, 0, -0.9, 0.2, 0.2, 0.5, -0.1, -0.6},
            //Jan'15 - Dec'15
            {-1.6, 0.2, 0.6, 0.9, 0.5, 0.3, -0.9, -0.3, -0.3, 0.6, 0.4, -0.3},
            //Jan'16 - Dec'16
            {-1.9, -0.4, 0.6, 0.7, 0.5, 0.5, -0.7, 0.1, 0, 1.1, 0.4, 0.6},
            //Jan'17 - Dec'17
            {-0.5, -0.4, 0, 1, -0.1, 0, -0.7, 0.2, 0.2, 0.9, 0.5, 0},
            //Jan'18 - Dec'18
            {-1.1, 0.1,0.1,0.8,0.9,0.3,-0.7,0.1,0.2,0.9,-0.1,-0.4},
            //Jan'19 - Dec'19
            {-1.3,0.2,0.4,1,0.2,-0.1,-0.6,-0.1,0,1,0.2,-0.1},
            //Jan'20 - Dec'20
            {-1,-0.1,-0.4,0.3,0,0.5,-0.9,0,0.2,0.5,0.2,0.2},
            //Jan'21 - Dec'21
            {0,-0.6,1,1.2,0.5,0.5,-0.8,0.5,0.8,1.8,0.3,1.2},
            //Jan'22 - Jun'22
            {-0.4,0.8,3,-0.2,0.8,1.9}
    };

    public static double calculateSalaryWithAbsoluteCPI(double salary, double absoluteCPI) {

        if(salary <= 0 || absoluteCPI <= 0) {
            return salary;
        } else {
            return ((salary * absoluteCPI)/100) + salary;
        }
    }


    public static double calculateAccumulateCPI(int firstYear, int monthFirstYear, int secondYear, int monthSecondYear){
        double calculation = 0;

        // undesired scenarios
        if(firstYear < 0 || secondYear < 0 || firstYear > secondYear ||
        firstYear > evolutionCPI.length || secondYear > evolutionCPI.length ||
        (firstYear == secondYear && monthFirstYear > monthSecondYear)) {
            return -1;

        // same year scenario
        } else if (firstYear == secondYear) {
            for(int i = monthFirstYear; i <= monthSecondYear; i++) {
                calculation = calculation + evolutionCPI[firstYear][i];
            }

        // multi-year scenario
        } else {
            int i = firstYear;

            // traverse first year from target month
            while(i == firstYear) {
                for(int j = monthFirstYear; j < evolutionCPI[0].length; j++) {
                    calculation = calculation + evolutionCPI[i][j];
                }
                i++;
            }

            // fully traverse the years between firstYear and secondYear
            while(i < secondYear) {
                for(int j = 0; j < evolutionCPI[0].length; j++) {
                    calculation = calculation + evolutionCPI[i][j];
                }
                i++;
            }

            // traverse secondYear until target month
            while(i == secondYear) {
                for(int j = 0; j <= monthSecondYear; j++) {
                    calculation = calculation + evolutionCPI[i][j];
                }
                i++;
            }
        }

        return calculation;
    }

    public static double calculateSalaryWithIntervalCPI(double salary, int firstYear, int monthFirstYear, int secondYear, int monthSecondYear){
       double CPI = calculateAccumulateCPI(firstYear,monthFirstYear,secondYear,monthSecondYear);

       return calculateSalaryWithAbsoluteCPI(salary,CPI);
    }

    public static void printSalaryInARange(double salary, int firstYear, int secondYear){
       double calculatedSalary = salary;
       String decimalSalary;

       for(int i = firstYear; i <= secondYear - firstYear; i++) {

           if(evolutionCPI[i].length < 12) {
               System.out.println("[ERROR] There was an error with printSalaryInARange");
           } else {
               calculatedSalary = calculateSalaryWithIntervalCPI(calculatedSalary,i, 0, i, 11);
               decimalSalary = decimalFormat.format(calculatedSalary);
               System.out.printf("Year %d, your salary should be %s\n", 2012 + i, decimalSalary);
           }
       }
    }


}
