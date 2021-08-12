public class GregorianDate extends Date {

    private static final int[] MONTH_LENGTHS = {
        31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    };

    public GregorianDate(int year, int month, int dayOfMonth) {
        super(year, month, dayOfMonth); //constructing from superclass of date
    }
    // YOUR CODE HERE
    @Override
    public Date nextDate() {
        int currDay = this.dayOfYear();
        int newYear = this.year;
        if (currDay == 365) {
            currDay = 0;
            newYear++;
        }
        currDay++; //find new month day and year, use constructor to create thenew date and the return date

        int newmonth, dateInMonth, daysInMonth;
        newmonth = 1;
        daysInMonth = 31;
        while (currDay > daysInMonth) {
            currDay-=daysInMonth;
            newmonth++;
            if (newmonth == 2) {
                daysInMonth = 28;
            } else if (newmonth == 4 || newmonth == 6 || newmonth == 9 || newmonth == 11) {
                daysInMonth = 30;
            } else {
                daysInMonth = 31;
            }
        }
        dateInMonth = currDay;
        //System.out.println("DEBUG: " + newmonth + "/" + dateInMonth);
        return new GregorianDate(newYear, newmonth, dateInMonth);
    }


    @Override
    public int dayOfYear() { //3/3 --> returns 62 when called
        int precedingMonthDays = 0;
        for (int m = 1; m < month; m += 1) {
            precedingMonthDays += getMonthLength(m);
        }
        return precedingMonthDays + dayOfMonth;
    }

    private static int getMonthLength(int m) {
        return MONTH_LENGTHS[m - 1];
    }
}

/*
    public static void main(String[] args) {
        int dayOfYear = 0;
        try {
            dayOfYear = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        int month, dateInMonth, daysInMonth;
        month = 1;
        daysInMonth = 31;
        while (dayOfYear > daysInMonth) {
            dayOfYear-=daysInMonth;
            month++;
            if (month == 2) {
                daysInMonth = 28;
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                daysInMonth = 30;
            } else {
                daysInMonth = 31;
            }
        }
        dateInMonth = dayOfYear;
        //System.out.println(month + "/" + dateInMonth);
    }
 */