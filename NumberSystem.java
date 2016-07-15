/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package numbers;

/**
 *
 * Developed by Ashish Gupta
 */
public final class NumberSystem{

    private NumberSystem(){};
    
    
    private static final char[] digits={'0','1','2','3','4','5',
                                        '6','7','8','9','A','B',
                                        'C','D','E','F','G','H',
                                        'I','J','K','L','M','N',
                                        'O','P','Q','R','S','T',
                                        'U','V','W','X','Y','Z'};
    
    private static int retIndex(char c){
        for(int i=0;i<digits.length;++i){
            if(c==digits[i])
                return i;
        }
       return -1;
    }
    
    private static int fastPow(int a,int b){
        int result = 1;
        while (b>0){
        if ((b&1)==1)
            result *= a;    
        b >>=1 ;
        a *= a;
        }
        return result;
    }
    
    /**
     * Converts a String of a numbers in some System into Decimal equivalent (long)
     * <br>Example: toLong("101",2) will return 4(long)
     * @param number to convert
     * @param radix
     * @return Decimal Equivalent (long)
     */
    
    public  static long toLong(String number,int radix){
        long value=0;
        char[] buffer=number.toCharArray();
        int charPos;
        int p=0;
        int len=buffer.length;
        charPos=len-1;
        while(charPos>=0){
            if(buffer[charPos]>digits[radix-1])
                throw new NumberFormatException("Invalid radix, number string combination");
            value+=retIndex(buffer[charPos--])*fastPow(radix, p++);
        }        
        return value;
    }
    
    
    /** <b>Specify custom radix to conversion: </b><br>
     *  Returns a String containing the required conversion
     * 
     * @param number  which will undergo conversion
     * @param radix to which radix
     * @return String
     */
    
    public static String toRadix(long number,long radix){
        if(radix>35)
                throw new NumberFormatException("Radix value (<35)exceeded: "+radix);
        char buffer[]=new char[64];
        int charPos=63,temp;       
        while(number>0){
            temp=(int)(number%radix);  
            buffer[charPos--]=digits[temp];
            number/=radix;           
        }       
        return new String(buffer,charPos,64-charPos);                           
    }
    
    /** Uses two way conversion functions to convert from one input radix to another
     *  <br> throws necessary Exception upon mismatch of radix with the input String
     *  <br>Example: For conversion of "10010" (which is initially with base 2) to hexadecimal, call should be made like:
     *  <br> <b>toCustomSystem</b>("10010",2,16);
     *  <br> will return "0x12"
     * @param inputSystem String variable to undergo conversion   
     * @param prev_radix Initial Radix
     * @param next_radix Required Radix
     * @return 
     * @exception NumberFormatException If mismatch of radix takes place
     */
    public static String toCustomSystem(String inputSystem,int prev_radix,int next_radix){
        if(inputSystem.isEmpty()||inputSystem.equalsIgnoreCase("0x"))
            return "0";
        if(inputSystem.startsWith("0x"))
            inputSystem=inputSystem.substring(2, inputSystem.length());
        
        long temp=toLong(inputSystem,prev_radix);
        return ((next_radix==16)?"0x":"") +toRadix(temp,next_radix);
    }
    
    /**
     * Checks String whether it has characters satisfying a given radix
     * Returns true if valid
     * @param number String encompassing the number
     * @param radix to be checked
     * @return boolean
     */
    public static boolean satisfiesRadix(String number,int radix){        
        //Hexadecimal 
        if(number.startsWith("0x"))
            number=number.substring(2, number.length());
        //Octal
        if(number.startsWith("0"))
            number=number.substring(1,number.length());
        
        char[] array=number.toCharArray();
        for(char c:array)
            if((c>='A')?(c-'A'+11>radix?true:false):(c-'0'>radix-1)?true:false) 
                return false;
        return true;
    }
    
    /**
     * Checks a given string if it contains characters which represent only decimal number system
     * @param number to be checked
     * @return boolean
     */
    
    public static boolean isDecimal(String number){
        return satisfiesRadix(number,10);
    }
    
    
    /**
     * Checks a given string if it contains only 0 and 1
     * @param number to be checked
     * @return boolean
     */
    public static boolean isBinary(String number){
        return satisfiesRadix(number,2);
    }
    
    /**
     * Checks a given string if it contains only 0-7 (Octal System)
     * @param number to be checked
     * @return boolean
     */
    
    public static boolean isOctal(String number){
        return number.startsWith("0")&&satisfiesRadix(number,8);
    }
    /**
     * Checks a given string if it contains only Hex characters
     * @param number to be checked
     * @return boolean
     */
    public static boolean isHexadecimal(String number){
        return number.startsWith("0x")&&satisfiesRadix(number,16);
    }
    
    
    
    /**
     * Converts input Decimal Number into a String containing its Binary equivalent
     * <br>Example: Passing 11 will return "1011" respectively
     * @param number Input Number
     * @return Binary String
     */
    public static String toBinary(long number){
        return toRadix(number,2);
    }
    
    /**
     * Converts a string containing some Number System into a String containing its Binary equivalent
     * <br>Example: Passing "12" will return "1100" respectively
     * <br>NOTE:Octal gets differentiated from Decimal by assigning "0" at the beginning
     *  While Hexadecimal has "0x" prefix.
     * <br> Else result gets ambiguous and answer is always Octal
     * @param number Input Number
     * @return Binary String
     */
    
    public static String toBinary(String number){
        if(isBinary(number))
            return number;
        if(isOctal(number))
            return toCustomSystem(number,8,2);
        if(isDecimal(number))
            return toRadix(Long.parseLong(number),2);
        if(isHexadecimal(number))
            return toCustomSystem(number,16,2);
        
        return null;    
        
    }
    
    
    /**
     * Converts input Long number into a String containing its Hexadecimal equivalent
     * <br>Example: Passing 45 will return "0x2D" respectively
     * @param number Input Number
     * @return String String
     */
    public static String toHexadecimal(long number){
        return "0x"+toRadix(number,16);
    }
    
    /**
     * Converts a string containing characters representing ANY Number System into a String containing its Hexadecimal equivalent
     * <br>Example: Passing "31" will return "0x1F" respectively.
     * However passing "031" would take it as octal and generate "0x19"
     * <br>NOTE:Octal gets differentiated from Decimal by assigning "0" at the beginning.
     *  While hexadecimal has "0x" prefix preset.
     * <br> Else result gets ambiguous and answer is always Octal
     * @param number Input Number
     * @return Hexadecimal String
     */
    
    public static String toHexadecimal(String number){
        
        if(isHexadecimal(number))
            return number;
        if(isBinary(number))
            return toCustomSystem(number,2,16);
        if(isOctal(number))
            return toCustomSystem(number,8,16);
        if(isDecimal(number))
            return toHexadecimal(Long.parseLong(number));
        
        return null;    
        
    }
    
    
    /**
     * Converts input Long number into a String containing its Octal equivalent
     * @param number to convert
     * @return String Binary String
     */
    
    public static String toOctal(long number){
        return "0"+toRadix(number,8);
    }
    
    
}
