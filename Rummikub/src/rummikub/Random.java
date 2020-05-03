/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub;

/**
 *
 * @author jair-
 */
public class Random {
    public  int obtenerRamdom(int limit) {
        java.util.Random rd = new java.util.Random();
        return rd.nextInt(limit);
    }
}
