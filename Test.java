/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package numbers;

/**
 *
 * @author Ashish Gupta
 */
public class TempClass {
    
    
    public static void main(String [] Args){
      System.out.println(NumberSystem.isBinary("1011"));
      System.out.println(NumberSystem.isHexadecimal("1011AFEG"));
      System.out.println(NumberSystem.isHexadecimal("11FF"));
      System.out.println(NumberSystem.toCustomSystem("0x1FF4",16,10));
      System.out.println(NumberSystem.toHexadecimal("031"));
    }
}
