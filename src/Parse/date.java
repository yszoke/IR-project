package Parse;

public class date {

    public enum Date {
        January, JANUARY, Jan,january,jan,JAN,
        February, FEBRUARY, Feb,february,FEB,feb,
        March, MARCH, Mar,march,MAR,mar,
        April, APRIL, Apr,april,APR,apr,
        May, MAY,may,
        June, JUNE, Jun,june,JUN,jun,
        July, JULY, Jul,july,JUL,jul,
        August, AUGUST, Aug,august,AUG,aug,
        September, SEPTEMBER, Sep, september,SEP,sep,
        October, OCTOBER, Oct, october,OCT,oct,
        November, NOVEMBER, Nov, november,NOV,nov,
        December, DECEMBER, Dec, december,DEC,dec
    }

    Date date;

    public String changeToDate(String monthWord,String digit){
        monthWord = matchDate(monthWord);
        if (Integer.parseInt(digit)>999){
            return digit+"-"+monthWord;
        }else{
            if(Integer.parseInt(digit)<10) {
                return monthWord+"-0"+digit;
            }
            return monthWord+"-"+digit;
        }
    }


    public String matchDate(String name) {
        date = Date.valueOf(name);
        String result="";

        switch (date) {
            case January:
            case JANUARY:
            case Jan:
            case january:
            case jan:
            case JAN:
            result="01";
            break;

            case February:
            case FEBRUARY:
            case Feb:
            case february:
            case FEB:
            case feb:
            result="02";
            break;

            case March:
            case MARCH:
            case Mar:
            case march:
            case MAR:
            case mar:
            result="03";
            break;

            case April:
            case APRIL:
            case Apr:
            case april:
            case APR:
            case apr:
            result="04";
            break;

            case May:
            case MAY:
            case may:

            result="05";
            break;

            case June:
            case JUNE:
            case Jun:
            case june:
            case JUN:
            case jun:
            result="06";
            break;

            case July:
            case JULY:
            case Jul:
            case july:
            case JUL:
            case jul:
            result="07";
            break;

            case August:
            case AUGUST:
            case Aug:
            case august:
            case AUG:
            case aug:
            result="08";
            break;

            case September:
            case SEPTEMBER:
            case Sep:
            case september:
            case SEP:
            case sep:
            result="09";
            break;

            case October:
            case OCTOBER:
            case Oct:
            case october:
            case OCT:
            case oct:
            result="10";
            break;

            case November:
            case NOVEMBER:
            case Nov:
            case november:
            case NOV:
            case nov:
            result="11";
            break;

            case December:
            case DECEMBER:
            case Dec:
            case december:
            case DEC:
            case dec:
            result="12";
            break;
        }
        return result;
    }
}


