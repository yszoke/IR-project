package Parse;

public class date {

    public enum Date {
        January, JANUARY, Jan,
        February, FEBRUARY, Feb,
        March, MARCH, Mar,
        April, APRIL, Apr,
        May, MAY,
        June, JUNE, Jun,
        July, JULY, Jul,
        August, AUGUST, Aug,
        September, SEPTEMBER, Sep,
        October, OCTOBER, Oct,
        November, NOVEMBER, Nov,
        December, DECEMBER, Dec,
    }

    Date date;


    public String matchDate(String name) {
        date = Date.valueOf(name);
        String result="";

        switch (date) {
            case January:
            case JANUARY:
            case Jan:
            result="01";
            break;

            case February:
            case FEBRUARY:
            case Feb:
            result="02";
            break;

            case March:
            case MARCH:
            case Mar:
            result="03";
            break;

            case April:
            case APRIL:
            case Apr:
            result="04";
            break;

            case May:
            case MAY:
            result="05";
            break;

            case June:
            case JUNE:
            case Jun:
            result="06";
            break;

            case July:
            case JULY:
            case Jul:
            result="07";
            break;

            case August:
            case AUGUST:
            case Aug:
            result="08";
            break;

            case September:
            case SEPTEMBER:
            case Sep:
            result="09";
            break;
            case October:
            case OCTOBER:
            case Oct:
            result="10";
            break;

            case November:
            case NOVEMBER:
            case Nov:
            result="11";
            break;

            case December:
            case DECEMBER:
            case Dec:
            result="12";
            break;
        }
        return result;
    }
}


