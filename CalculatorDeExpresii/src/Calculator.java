import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    private static String[] getPattern(String expression){
        String[] rez=new String[expression.length()+1];
        if(expression.isEmpty())return null;
        String reg = "([+-/*])|((\\d{1,100}[.])?\\d{1,100})|(\\d{1,100})";
        Pattern pattern = Pattern.compile(reg);
        Matcher m = pattern.matcher(expression);
        int i=0;
        while(m.find()) rez[i++]=m.group();
        return rez;
    }

    private static String[] resolvePattern(String[] e){
        String[] rez = null;
        for(int i=0;i<e.length;i++)
            if(e[i]==null){
                rez=new String[i];
                break;
            }
        if(rez==null)return e;
        if(e[0].equals("-")){
            e[1]=e[0]+e[1];
            e[0]=null;
        }
        for(int i=0;i<e.length;i++){
            if(e[i]!=null) {
                if (e[i].equals("-") && (e[i - 1].equals("/") || e[i - 1].equals("*") || e[i - 1].equals("+") || e[i - 1].equals("-"))) {
                    try {
                        double c = Double.valueOf(e[i + 1]);
                        e[i + 1] = e[i] + e[i + 1];
                        e[i] = null;
                    } catch (Exception ee) {
                        //nothing happens
                    }
                }
            }
        }
        int j=0;
        for(int i=0;i<rez.length;i++){
            if(e[i]==null);
            else rez[j++]=e[i];
        }
        return rez;
    }

    private static String[] reformatString(String[] rez){
        String[] reformat= new String[rez.length];
        int j=0;
        for(int i=0;i<rez.length;i++)if(rez[i]!=null&&!rez[i].equals(""))reformat[j++]=rez[i];
        return reformat;
    }

    private static Double evaluate(String expression) {
        String[] rez = reformatString(resolvePattern(getPattern(expression)));
        if(rez==null)return null;
        if(rez[1]==null)return Double.valueOf(rez[0]);
        while(true) {
            String newRez = "";
            for (int i = 0; i < rez.length; i++) {
                if (rez[i] != null && !rez[i].equals(""))
                    if (rez[i].equals("*")) {
                        newRez = String.valueOf(Double.valueOf(rez[i - 1]) * Double.valueOf(rez[i + 1]));
                        rez[i + 1] = newRez;
                        rez[i] = null;
                        rez[i - 1] = null;
                    } else if (rez[i].equals("/")) {
                        newRez = String.valueOf(Double.valueOf(rez[i - 1]) / Double.valueOf(rez[i + 1]));
                        rez[i + 1] = newRez;
                        rez[i] = null;
                        rez[i - 1] = null;
                    }
            }
            rez = reformatString(rez);
            for (int i = 0; i < rez.length; i++) {
                if (rez[i] != null && !rez[i].equals(""))
                    if (rez[i].equals("+")) {
                        newRez = String.valueOf(Double.valueOf(rez[i - 1]) + Double.valueOf(rez[i + 1]));
                        rez[i + 1] = newRez;
                        rez[i] = null;
                        rez[i - 1] = null;
                    } else if (rez[i].equals("-")) {
                        newRez = String.valueOf(Double.valueOf(rez[i - 1]) - Double.valueOf(rez[i + 1]));
                        rez[i + 1] = newRez;
                        rez[i] = null;
                        rez[i - 1] = null;
                    }
            }
            rez = reformatString(rez);
            try {
                Double inSfrasit=Double.valueOf(rez[0]);
                if (rez[1] == null || rez[1].equals(""))return inSfrasit;
            }catch (Exception e){
                // nothing happens
            }
        }
    }
    public static Double calculate(String expression){
        StringBuilder exp=new StringBuilder(expression.replaceAll(" ",""));
        int nrPar=0;
        int nrTotalPar=0;
        for(int i=0;i<exp.length();i++){
            if(exp.charAt(i)=='('){
                nrPar++;
                nrTotalPar++;
            }
            else if(exp.charAt(i)==')')nrPar--;
            if(nrPar<0)return null;
        }
        if(nrPar!=0)return null;
        int firstPar=0;
        if(nrTotalPar!=0) {
            for (int i = 0; i < exp.length(); i++) {
                if (exp.charAt(i) == '(') {
                    nrPar++;
                    firstPar = i;
                } else if (exp.charAt(i) == ')') {
                    nrPar--;
                    if (firstPar + 1 == i) return null;
                    String val = String.valueOf(evaluate(exp.substring(firstPar + 1, i)));
                    StringBuilder aux = new StringBuilder(exp.substring(0, firstPar) + val + exp.substring(i + 1, exp.length()));
                    exp = aux;
                    i = -1;
                }
            }
        }
        return evaluate(exp.toString());
    }
}