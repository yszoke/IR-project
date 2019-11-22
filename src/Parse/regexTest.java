package Parse;

public class regexTest {



    public static void main(String[] args){

        String s = "98 percentage";
        String result = s.replaceAll("\\%|\\s\\bpercent\\b|\\s\\bpercentage\\b", "%");

        //replace Thousand to K
        String text = "$100 thousand";
        text = text.replaceAll("\\s\\bThousand\\b|\\s\\bthousand\\b","K");
        System.out.println(text);

        //replace $100 to 100 dollars ----> NOT WORKING!
        text = "$100";
        text = text.replaceAll("\\$+[^\\d]", " dollars");
        System.out.println(text);
        //\$+\d++\w|

        //replace Million to M
        text = "$100 million";
        text = text.replaceAll("\\s\\bMillion\\b|\\s\\bmillion\\b","M");
        System.out.println(text);

        text = "$100 billion";
        text = text.replaceAll("\\s\\bBillion\\b|\\s\\bbillion\\b","B");
        System.out.println(text);

        text = "$100 m dfbgfbf";
        text = text.replaceAll("\\d+\\s\\bm\\b","M");
        System.out.println(text);



        String bigNum = "45 Thousand";
        //String bResult = bigNum.replaceAll("\\s\\bThousand\\b","K");
        //System.out.println(bResult);



        /*
        String num = "450000";
        String numResult = num.replaceAll("[1-9][0-9][0-9][0-9][0-9]|[1-9][0-9][0-9][0-9][0-9]", num.substring(0,num.length()-3)+" K");
        System.out.println(numResult);
        */



    }
}
