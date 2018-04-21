package com.example.abhiyash.snap2know;

/**
 * Created by Abhiyash on 22-Apr-18.
 */

public class KeyWordExtract {
    public String ext(String q) {
        String w = null;
        int l=q.length();
        if(q.length()<4|| !q.endsWith("s"))
        {
            //System.out.println("Not Plural");
        }
        else if(q.endsWith("es"))
        {
            if(q.charAt(l-3)=='i')
            { if(q.charAt(l-4)=='a'||q.charAt(l-4)=='e'||q.charAt(l-4)=='i'||q.charAt(l-4)=='o'||q.charAt(l-4)=='u')
            {w=""+q.substring(0,l-3);
                System.out.println(w);
            }
                w=""+q.substring(0, l-3)+"y";
                System.out.println(w);
                return w;
            }
            if(q.substring(l-4, l-2).equals("ss"))
            {
                w=""+q.substring(0, l-2);
                System.out.println(w);
                return w;
            }
            if(q.charAt(l-3)=='s')
            {
                w=""+q.substring(0, l-1);
                System.out.println(w);
                return w;
            }
            if(q.charAt(l-3)=='x' ||q.charAt(l-3)=='o'||q.charAt(l-3)=='z')
            {
                w=""+q.substring(0, l-2);
                System.out.println(w);
                return w;
            }
            if(q.substring(l-4, l-2).equals("ch")||q.substring(l-4, l-2).equals("sh"))
            {
                w=""+q.substring(0, l-2);
                System.out.println(w);
                return w;
            }
            if(q.charAt(l-3)=='h')
            {
                w=""+q.substring(0, l-1);
                System.out.println(w);
                return w;
            }
            if(q.endsWith("s"))
            {
                w=""+q.substring(0, l-1);
                System.out.println(w);
                return w;
            }
        }
        else
        {
            if(q.endsWith("s"))
            {
                //   System.out.println(55);
                w=""+q.substring(0, l-1);
                System.out.println(w);
                return w;
            }
        }

        //verb past participle or verb past tense
        int vc=0;
        int i;
        for(i=0;i<q.length();i++)
        {
            if(q.charAt(i)=='a'||q.charAt(i)=='e'||q.charAt(i)=='i'||q.charAt(i)=='o'||q.charAt(i)=='u')
                vc++;
        }
        if(q.length()<5 || !q.endsWith("ed"))
        {
            // System.out.println("Do nothing");
        }
        else
        if(q.length()>=5 && (q.charAt(l-4)=='a'||q.charAt(l-4)=='e'||q.charAt(l-4)=='i'||q.charAt(l-4)=='o'||q.charAt(l-4)=='u'))
        {

            w=q.substring(0, l-1);
            System.out.println(w);
            return w;
        }
        else if(q.charAt(l-3)=='i')
        {
            System.out.println(2);
            w=q.substring(0,l-3)+"y";
            System.out.println(w);
            return w;
        }
        else if(vc==2 && (q.substring(l-4,l-2).equals("tt")||q.substring(l-4,l-2).equals("tt")||q.substring(l-4,l-2).equals("nn")||q.substring(l-4,l-2).equals("rr")||q.substring(l-4,l-2).equals("dd")||q.substring(l-4,l-2).equals("mm")||q.substring(l-4,l-2).equals("ff")||q.substring(l-4,l-2).equals("gg")||q.substring(l-4,l-2).equals("pp")||q.substring(l-4,l-2).equals("bb")))
        {
            System.out.println(3);
            w=q.substring(0,l-3);

            System.out.println(w);
            return w;

        }
        else
        {
            M:if(q.substring(l-2,l).equalsIgnoreCase("ed"))
            {
                System.out.println(4);
                w=""+q.substring(0,l-2);
                System.out.println(w);
                return w;

            }
        }

        //verb presentparticiple gerund
        int vc2=0;
        for(i=0;i<q.length();i++)
        {
            if(q.charAt(i)=='a'||q.charAt(i)=='e'||q.charAt(i)=='i'||q.charAt(i)=='o'||q.charAt(i)=='u')
                vc2++;
        }

        if(q.length()<6 || !q.endsWith("ing"))
        {
            // System.out.println("Do nothing");
        }
        else
        {
            if(q.length()>=6 && (q.charAt(l-5)=='a'||q.charAt(l-5)=='e'||q.charAt(l-5)=='i'||q.charAt(l-5)=='o'||q.charAt(l-5)=='u'))
            {
                w=""+q.substring(0, l-3)+"e";
                System.out.println(w);
                return w;
            }
            if(vc2==2 && (q.substring(l-5,l-3).equals("tt")||q.substring(l-5,l-3).equals("tt")||q.substring(l-5,l-3).equals("nn")||q.substring(l-5,l-3).equals("rr")||q.substring(l-5,l-3).equals("dd")||q.substring(l-5,l-3).equals("mm")||q.substring(l-5,l-3).equals("ff")||q.substring(l-5,l-3).equals("gg")||q.substring(l-5,l-3).equals("pp")||q.substring(l-5,l-3).equals("bb")))
            {
                w=q.substring(0,l-4);
                System.out.println(w);
                return w;
            }
            else
            {
                if(q.endsWith("ing"))
                {
                    w=""+q.substring(0, l-3);
                    System.out.println(w);
                    return w;
                }
            }
        }
        return "null";
    }
}
