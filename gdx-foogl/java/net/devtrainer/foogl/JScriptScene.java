/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.devtrainer.foogl;

/**
 *
 * @author twachi
 */
public class JScriptScene extends SimpleScene{
    public JScriptScene(String name){    	
        plugins.javascript(name);
    }
}
